package com.jiutai.orangesunshine.test.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiutai.commonlib.animation.PathAnimation;
import com.jiutai.commonlib.animation.PathPoint;
import com.jiutai.commonlib.animation.path.CubicPathBuild;
import com.jiutai.commonlib.animation.path.LinePathBuild;
import com.jiutai.commonlib.animation.path.MovePathBuild;
import com.jiutai.orangesunshine.R;

public class AnimationActivity extends Activity {
    private TextView tvAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        tvAni = findViewById(R.id.tv_animation);
    }

    public void onAnimation(View view) {
        PathAnimation ani = new PathAnimation();
        ani.addPoint(new PathPoint(0, 0, new MovePathBuild()));
        ani.addPoint(new PathPoint(200, 200, new CubicPathBuild(300.0f, 0, 400f, 100f)));
        ani.addPoint(new PathPoint(600, 400, new LinePathBuild()));
        ani.startAnimation(tvAni, 2000);
    }
}
