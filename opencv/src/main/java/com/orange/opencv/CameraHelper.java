package com.orange.opencv;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraHelper implements Camera.PreviewCallback {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private int mCameraId;
    private Camera mCamera;
    private byte[] mBuffer;
    private Camera.PreviewCallback mPreviewCallback;
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();

    public CameraHelper(int cameraId) {
        mCameraId = cameraId;
    }

    public void switchCamera() {
        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        stopPreview();
        startPreview();
    }

    public int getCameraId() {
        return mCameraId;
    }

    public void startPreview() {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mCamera = Camera.open(mCameraId);
                    Camera.Parameters parameters = mCamera.getParameters();
                    //设置预览格式
                    parameters.setPreviewFormat(ImageFormat.NV21);
                    //设置相机宽、高
                    parameters.setPreviewSize(WIDTH, HEIGHT);
                    if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                    }

                    //图像传感器角度、方向
                    mCamera.setParameters(parameters);
                    mBuffer = new byte[WIDTH * HEIGHT * 3 / 2];
                    //数据缓存区
                    mCamera.addCallbackBuffer(mBuffer);
                    mCamera.setPreviewCallbackWithBuffer(CameraHelper.this);

                    SurfaceTexture surfaceTexture = new SurfaceTexture(11);
                    mCamera.setPreviewTexture(surfaceTexture);
                    mCamera.startPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stopPreview() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public void setPreviewCallback(Camera.PreviewCallback previewCallback) {
        mPreviewCallback = previewCallback;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mPreviewCallback.onPreviewFrame(data, camera);
        camera.addCallbackBuffer(mBuffer);
    }
}
