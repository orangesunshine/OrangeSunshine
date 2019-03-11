package com.jiutai.commonlib.animation;

public class LinePathPoint extends PathPoint {

    public LinePathPoint(float x, float y) {
        super(x, y);
    }

    @Override
    public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        float x,y;
        x = startValue.mX + t * (endValue.mX - startValue.mX);
        y = startValue.mY + t * (endValue.mY - startValue.mY);
        return new LinePathPoint(x, y);
    }
}
