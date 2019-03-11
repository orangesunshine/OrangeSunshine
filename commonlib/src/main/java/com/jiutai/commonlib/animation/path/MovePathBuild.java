package com.jiutai.commonlib.animation.path;

import com.jiutai.commonlib.animation.PathPoint;

public class MovePathBuild extends PathBuild {
    @Override
    public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue) {
        float x = 0, y = 0;
        x = endValue.mX;
        y = endValue.mY;
        return new PathPoint(x, y);
    }
}
