package com.jiutai.commonlib.animation;

public interface IPathPoint<T extends PathPoint> {
    PathPoint evaluate(float t, T startValue, T endValue);
}
