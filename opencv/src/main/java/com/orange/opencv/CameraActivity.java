package com.orange.opencv;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jiutai.commonlib.utils.AssetUtils;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback {
    static {
        System.loadLibrary("native-lib");
    }
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private CameraHelper mCameraHelper;
    private String mPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);
        mCameraHelper = new CameraHelper(mCameraId);
        mCameraHelper.setPreviewCallback(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 10);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
        }

        mPath = AssetUtils.copyAssetAndWrite(this, "lbpcascade_frontalface.xml");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化跟踪器
        init(mPath);
        mCameraHelper.startPreview();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //释放跟踪器
        release();
        mCameraHelper.stopPreview();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //传输数据
        faceDetected(data, CameraHelper.WIDTH, CameraHelper.HEIGHT, mCameraId);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //设置surface 用于显示
        setSurface(holder.getSurface());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mCameraHelper.switchCamera();
            mCameraId = mCameraHelper.getCameraId();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 初始化追踪器
     *
     * @param path
     */
    public native void init(String path);

    /**
     * 设置画布
     *
     * @param surface
     */
    public native void setSurface(Surface surface);

    /**
     * 释放资源
     */
    public native void release();

    /**
     * 处理摄像头数据
     * @param data
     * @param w
     * @param h
     * @param cameraId
     */
    public native void faceDetected(byte[] data, int w, int h, int cameraId);
}
