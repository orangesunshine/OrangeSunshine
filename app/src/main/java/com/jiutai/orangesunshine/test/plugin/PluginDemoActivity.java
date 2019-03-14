package com.jiutai.orangesunshine.test.plugin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jiutai.commonlib.plugin.PluginManager;
import com.jiutai.commonlib.plugin.ProxyActivity;
import com.jiutai.commonlib.utils.AssetUtils;
import com.jiutai.orangesunshine.R;

public class PluginDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_demo);
        PluginManager.getInstance().init(this);
        findViewById(R.id.tv_load_apk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = AssetUtils.copyAssetAndWrite(PluginDemoActivity.this, "pluginApk.apk");
                PluginManager.getInstance().loadApk(path);
            }
        });

        findViewById(R.id.tv_launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PluginDemoActivity.this, ProxyActivity.class);
                intent.putExtra("className", "com.orange.pluginapk.PluginApkActivity");
                startActivity(intent);
            }
        });
    }
}
