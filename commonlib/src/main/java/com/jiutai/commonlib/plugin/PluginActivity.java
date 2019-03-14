package com.jiutai.commonlib.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PluginActivity extends Activity implements IPlugin {
    private int mFrom = FROM_INTERNAL;
    private Activity mActivity;

    @Override
    public void attach(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (null != saveInstanceState) {
            mFrom = saveInstanceState.getInt("FROM");
        }
        if (mFrom == FROM_INTERNAL) {
            super.onCreate(saveInstanceState);
            mActivity = this;
        }
    }

    @Override
    public void setContentView(int layoutResId) {
        if (mFrom == FROM_INTERNAL) {
            super.setContentView(layoutResId);
        } else {
            mActivity.setContentView(layoutResId);
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERNAL) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == FROM_INTERNAL) {
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mFrom == FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == FROM_INTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == FROM_INTERNAL) {
            super.onDestroy();
        }
    }
}
