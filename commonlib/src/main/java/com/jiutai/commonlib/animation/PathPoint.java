package com.jiutai.commonlib.animation;

import com.jiutai.commonlib.animation.path.PathBuild;

public class PathPoint<T extends PathBuild> {
    public float mX;
    public float mY;
    private T mPathBuild;

    public PathPoint(float x, float y) {
        mX = x;
        mY = y;
    }

    public PathPoint(float x, float y, T build) {
        mX = x;
        mY = y;
        mPathBuild = build;
    }

    public T getPathBuild() {
        return mPathBuild;
    }

    protected PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        return mPathBuild.evaluate(t, startValue, endValue);
    }
}
