package com.jiutai.commonlib.animation;

public abstract class PathPoint<T extends PathPoint> implements IPathPoint<T> {
    protected float mX;
    protected float mY;

    public PathPoint(float x, float y) {
        mX = x;
        mY = y;
    }
}
