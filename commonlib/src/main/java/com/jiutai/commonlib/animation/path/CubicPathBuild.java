package com.jiutai.commonlib.animation.path;

import com.jiutai.commonlib.animation.PathPoint;

public class CubicPathBuild extends PathBuild {
    public float mControlX1, mControlY1, mControlX2, mControlY2;

    public CubicPathBuild(float controlX1, float controlY1, float controlX2, float controlY2) {
        mControlX1 = controlX1;
        mControlY1 = controlY1;
        mControlX2 = controlX2;
        mControlY2 = controlY2;
    }

    @Override
    public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        PathBuild build = endValue.getPathBuild();
        if (null == build || !(build instanceof CubicPathBuild)) {
            throw new IllegalStateException("build must be CubicPathBuild!");
        }
        float x = 0, y = 0;
        float oneMiuns = 1 - t;
        x = oneMiuns * oneMiuns * oneMiuns * startValue.mX + 3 * oneMiuns * oneMiuns * t * ((CubicPathBuild) build).mControlX1 + 3 * oneMiuns * t * t * ((CubicPathBuild) build).mControlX2 + t * t * t * endValue.mX;
        y = oneMiuns * oneMiuns * oneMiuns * startValue.mY + 3 * oneMiuns * oneMiuns * t * ((CubicPathBuild) build).mControlY1 + 3 * oneMiuns * t * t * ((CubicPathBuild) build).mControlY2 + t * t * t * endValue.mY;
        return new PathPoint(x, y);
    }
}
