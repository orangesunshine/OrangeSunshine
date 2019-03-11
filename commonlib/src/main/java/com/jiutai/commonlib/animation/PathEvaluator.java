package com.jiutai.commonlib.animation;

import android.animation.TypeEvaluator;

class PathEvaluator implements TypeEvaluator<PathPoint> {
    @Override
    public PathPoint evaluate(float fraction, PathPoint startValue, PathPoint endValue) {
        PathPoint evaluate = endValue.evaluate(fraction, startValue, endValue);
        return evaluate;
    }
}
