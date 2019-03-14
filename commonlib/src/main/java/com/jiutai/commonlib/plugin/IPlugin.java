package com.jiutai.commonlib.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public interface IPlugin {
    int FROM_INTERNAL = 0;//内部跳转
    int FROM_EXTERNAL = 1;//内部跳转

    void attach(Activity activity);

    void onCreate(Bundle saveInstanceState);

    void setContentView(int layoutResId);

    void onStart();

    void onRestart();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
