package com.jiutai.commonlib.animation;

public class MovePathPoint extends PathPoint {

    public MovePathPoint(float x, float y) {
        super(x, y);
    }

    @Override
    public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        return new MovePathPoint(mX, mY);
    }
}
