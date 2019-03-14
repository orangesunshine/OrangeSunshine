//
// Created by Administrator on 2019/3/13.
//
#include <jni.h>
#include<string>
#include<android/log.h>
#include<opencv2/opencv.hpp>
#include <android/native_window_jni.h>

#define LOG_TAG "C_TAG"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__) // 定义LOGD类型

using namespace cv;
using namespace std;

CascadeClassifier *classifier = 0;
ANativeWindow *window = 0;
DetectionBasedTracker *tracker = 0;

class CascadeDetectorAdapter : public DetectionBasedTracker::IDetector {
public:
    CascadeDetectorAdapter(cv::Ptr<cv::CascadeClassifier> detector) :
            IDetector(),
            Detector(detector) {
        LOGD("CascadeDetectorAdapter::Detect::Detect");
        CV_Assert(detector);
    }

    void detect(const cv::Mat &Image, std::vector<cv::Rect> &objects) {
        LOGD("CascadeDetectorAdapter::Detect: begin");
        LOGD("CascadeDetectorAdapter::Detect: scaleFactor=%.2f, minNeighbours=%d, minObjSize=(%dx%d), maxObjSize=(%dx%d)",
             scaleFactor, minNeighbours, minObjSize.width, minObjSize.height, maxObjSize.width,
             maxObjSize.height);
        Detector->detectMultiScale(Image, objects, scaleFactor, minNeighbours, 0, minObjSize,
                                   maxObjSize);
        LOGD("CascadeDetectorAdapter::Detect: end");
    }

    virtual ~CascadeDetectorAdapter() {
        LOGD("CascadeDetectorAdapter::Detect::~Detect");
    }

private:
    CascadeDetectorAdapter();

    cv::Ptr<cv::CascadeClassifier> Detector;
};

extern "C"
JNIEXPORT void JNICALL
Java_com_orange_opencv_CameraActivity_init(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    LOGD("CameraActivity=%s", "init");
    //第一种方案
//    classifier /= new CascadeClassifier(path);

    //第二种方案
    //创建一个跟踪适配器
    Ptr<CascadeDetectorAdapter> mainDetector = makePtr<CascadeDetectorAdapter>(
            makePtr<CascadeClassifier>(path));

    //创建一个跟踪适配器
    Ptr<CascadeDetectorAdapter> trackingDetector = makePtr<CascadeDetectorAdapter>(
            makePtr<CascadeClassifier>(path));

    DetectionBasedTracker::Parameters parameters;

    //创建一个跟踪器
    tracker = new DetectionBasedTracker(mainDetector, trackingDetector, parameters);
    //开始跟踪
    tracker->run();

    env->ReleaseStringUTFChars(path_, path);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_orange_opencv_CameraActivity_setSurface(JNIEnv *env, jobject instance, jobject surface) {
    LOGD("CameraActivity=%s", "setSurface");
    if (window) {
        ANativeWindow_release(window);
        window = 0;
    }
    window = ANativeWindow_fromSurface(env, surface);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_orange_opencv_CameraActivity_release(JNIEnv *env, jobject instance) {
    LOGD("CameraActivity=%s", "release");
    if (tracker) {
        tracker->stop();
        delete tracker;
        tracker = 0;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_orange_opencv_CameraActivity_faceDetected(JNIEnv *env, jobject instance, jbyteArray data_,
                                                   jint w, jint h, jint cameraId) {
    jbyte *data = env->GetByteArrayElements(data_, NULL);
    LOGD("CameraActivity=%s", "faceDetected");
    //将data转换成opencv识别格式 YUV420格式数据
    Mat src(h + h / 2, w, CV_8UC1, data);

    cvtColor(src, src, COLOR_YUV2RGBA_NV21);

    if (cameraId == 1) {
        //前置摄像头，逆时针旋转
        rotate(src, src, ROTATE_90_COUNTERCLOCKWISE);

        //水平镜像翻转
        flip(src, src, 1);
    } else {
        //后置摄像头，顺时针旋转
        rotate(src, src, ROTATE_90_CLOCKWISE);
    }

    Mat grayMat;
    //灰度化处理，排除彩色信息干扰，黑白图片
    cvtColor(src, grayMat, COLOR_RGBA2GRAY);

    //均衡化处理，增强图像对比度
    equalizeHist(grayMat, grayMat);

    vector<Rect> faces;
    //识别
    //第一种方案
//    classifier->detectMultiScale(grayMat, faces);
    //第二种方案
    tracker->process(grayMat);
    tracker->getObjects(faces);
    for (int i = 0; i < faces.size(); i++) {
        Rect face = faces[i];
        rectangle(src, face, Scalar(255, 255, 0));
    }

    if (window) {
        ANativeWindow_setBuffersGeometry(window, src.cols, src.rows, WINDOW_FORMAT_RGBA_8888);
        ANativeWindow_Buffer buffer;
        do {
            if (ANativeWindow_lock(window, &buffer, 0)) {
                ANativeWindow_release(window);
                window = 0;
                break;
            } else {
                //填充rgb 数据给到dst_data
                uint8_t *dst_data = static_cast<uint8_t *>(buffer.bits);
                //一行有多少个数据
                int dst_line_size = buffer.stride * 4;
                for (int i = 0; i < buffer.height; i++) {
                    memcpy(dst_data + i * dst_line_size, src.data + i * src.cols * 4,
                           dst_line_size);
                }

                ANativeWindow_unlockAndPost(window);
            }
        } while (0);
    }

    src.release();
    grayMat.release();

    env->ReleaseByteArrayElements(data_, data, 0);
}