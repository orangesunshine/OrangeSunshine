package com.jiutai.commonlib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetUtils {
    public static void copyAsset(Context context, String asset) {
        if (null == context || TextUtils.isEmpty(asset)) {
            throw new IllegalArgumentException("初始参数错误");
        }
        try {
            String[] list = context.getAssets().list(asset);
            if (null != list && list.length > 0) {
                File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + asset);
                if (!file.exists()) {
                    file.mkdirs();
                }
                for (String item : list) {
                    asset = asset + File.separator + item;
                    copyAsset(context, asset);
                }
            } else {
                copyAssetFile(context, asset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String copyAssetFile(Context context, String fileName) {
        if (null == context || TextUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("初始参数错误");
        }
        String path = context.getFilesDir().getAbsolutePath() + File.separator + fileName;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fos = new FileOutputStream(file);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
                is.close();
                fos.close();
                Toast.makeText(context, "copy finish", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String copyAssetAndWrite(Context context, String fileName) {
        File cacheDir = context.getExternalCacheDir();
        Log.e("wanna", "cacheDir: " + cacheDir.getAbsolutePath());
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        File file = new File(cacheDir, fileName);
        if (!file.exists()) {
            try {
                boolean res = file.createNewFile();
                if (res) {
                    InputStream is = context.getAssets().open(fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int count;
                    while (-1 != (count = is.read(buffer))) {
                        fos.write(buffer, 0, count);
                    }
                    fos.flush();
                    fos.close();
                    is.close();
                    Toast.makeText(context, "下载成功", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }
}
