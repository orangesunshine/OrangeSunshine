package com.jiutai.commonlib.animation.path;

import com.jiutai.commonlib.animation.PathPoint;

public abstract class PathBuild {

    public abstract PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue);
}
