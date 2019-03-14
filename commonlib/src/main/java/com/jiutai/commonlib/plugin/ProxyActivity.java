package com.jiutai.commonlib.plugin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jiutai.commonlib.R;

public class ProxyActivity extends Activity {
    private String mClassName;
    private PluginApk mPluginApk;
    private IPlugin mIPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (null == mPluginApk) {
            throw new RuntimeException("请先加载插件Apk");
        }
        try {
            //clazz就是activity对象，但没有生命周期，没有上下文
            Class<?> clazz = mPluginApk.mDexClassLoader.loadClass(mClassName);
            Object object = clazz.newInstance();
            Log.e("wanna", object.getClass().getSimpleName() + ", isAssignableFrom: " + object.getClass().isAssignableFrom(IPlugin.class));
            if (object instanceof IPlugin) {
                mIPlugin = (IPlugin) object;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_EXTERNAL);
                mIPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return null != mPluginApk ? mPluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return null != mPluginApk ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return null != mPluginApk ? mPluginApk.mDexClassLoader : super.getClassLoader();
    }
}
