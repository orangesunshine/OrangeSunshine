package com.jiutai.commonlib.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

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
        ObjectAnimator animation = ObjectAnimator.ofObject(this, "animation", new PathEvaluator(), points);
        animation.setDuration(duration);
        animation.start();
    }

    private void setAnimation(PathPoint p) {
        target.setTranslationX(p.mX);
        target.setTranslationY(p.mY);
    }
}
