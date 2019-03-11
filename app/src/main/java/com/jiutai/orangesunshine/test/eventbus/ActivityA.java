package com.jiutai.orangesunshine.test.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jiutai.commonlib.eventbus.EventBus;
import com.jiutai.commonlib.eventbus.EventBusBean;
import com.jiutai.commonlib.eventbus.Subscribe;
import com.jiutai.orangesunshine.R;

public class ActivityA extends AppCompatActivity {
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        EventBus.getDefault().register(this);
        tvMsg = findViewById(R.id.tv_msg);
        tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityA.this, Activity_B.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe()
    public void onReceiver(EventBusBean bean) {
        Log.e("HY", bean.toString());
    }
}
