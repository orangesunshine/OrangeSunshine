package com.jiutai.orangesunshine.test.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jiutai.commonlib.animation.LinePathPoint;
import com.jiutai.commonlib.animation.MovePathPoint;
import com.jiutai.commonlib.animation.PathAnimation;
import com.jiutai.orangesunshine.R;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    public void onAnimation(View view) {
        PathAnimation ani = new PathAnimation();
        ani.addPoint(new LinePathPoint(100, 100));
        ani.addPoint(new MovePathPoint(0, 0));
        ani.startAnimation(view, 1000);
    }
}
