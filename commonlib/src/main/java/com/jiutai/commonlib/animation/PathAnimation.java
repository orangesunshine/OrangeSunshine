package com.jiutai.commonlib.animation;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.REVERSE;

public class PathAnimation {
    private List<PathPoint> points = new ArrayList<>();
    private View target;

    public void addPoint(PathPoint point) {
        if (null == point) throw new NullPointerException("point not be null!");
        points.add(point);
    }

    public void startAnimation(View view, int duration) {
        if (null == view) throw new NullPointerException("view not be null!");
        target = view;
        ObjectAnimator animation = ObjectAnimator.ofObject(PathAnimation.this, "haha", new PathEvaluator(), points.toArray());
        animation.setDuration(duration);
        animation.setRepeatMode(REVERSE);
        animation.setRepeatCount(INFINITE);
        animation.start();
    }

    public  void setHaha(PathPoint p) {
        Log.e("HY", "setHaha_evaluate: " + p.toString());
        target.setTranslationX(p.mX);
        target.setTranslationY(p.mY);
    }
}
