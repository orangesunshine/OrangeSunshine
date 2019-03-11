package com.jiutai.orangesunshine.test.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jiutai.commonlib.eventbus.EventBus;
import com.jiutai.commonlib.eventbus.EventBusBean;
import com.jiutai.orangesunshine.R;

public class Activity_B extends AppCompatActivity {
    private TextView tvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__b);
        tvSend = findViewById(R.id.tv_send);
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventBusBean("Android", "a phone performance system"));
            }
        });
    }
}
