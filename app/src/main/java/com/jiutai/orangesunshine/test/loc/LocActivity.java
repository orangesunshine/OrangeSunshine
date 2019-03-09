package com.jiutai.orangesunshine.test.loc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiutai.commonlib.ioc.InjectManager;
import com.jiutai.commonlib.ioc.annotation.ContentLayout;
import com.jiutai.commonlib.ioc.annotation.InjectView;
import com.jiutai.commonlib.ioc.annotation.OnClick;
import com.jiutai.orangesunshine.R;

@ContentLayout(R.layout.activity_loc)
public class LocActivity extends AppCompatActivity {
    @InjectView(R.id.tv_1)
    TextView tv1;
    @InjectView(R.id.tv_1)
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
        Log.e("HY", "tv1: " + (null == tv1));
    }

    @OnClick(R.id.tv_1)
    private void onShow(View view) {
        Toast.makeText(this, "OnClick", Toast.LENGTH_LONG).show();
    }
}
