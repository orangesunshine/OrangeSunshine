package com.jiutai.commonlib.animation;

public class CubicPathPoint extends PathPoint<CubicPathPoint> {
    protected float mControlX1, mControlY1;
    protected float mControlX2, mControlY2;

    public CubicPathPoint(float x, float y) {
        super(x, y);
    }

    public CubicPathPoint(float x, float y, float controlX1, float controlY1, float controlX2, float controlY2) {
        super(x, y);
        mControlX1 = controlX1;
        mControlY1 = controlY1;
        mControlX2 = controlX2;
        mControlY2 = controlY2;
    }

    @Override
    public PathPoint evaluate(float t, CubicPathPoint startValue, CubicPathPoint endValue) {
        float x, y;
        float oneMinusT = 1 - t;
        x = oneMinusT * oneMinusT * oneMinusT * startValue.mX + 3 * oneMinusT * oneMinusT * t * endValue.mControlX1 +
                3 * oneMinusT * t * t * endValue.mControlX2 + t * t * t * endValue.mX;

        y = oneMinusT * oneMinusT * oneMinusT * startValue.mY + 3 * oneMinusT * oneMinusT * t * endValue.mControlY1 +
                3 * oneMinusT * t * t * endValue.mControlY2 + t * t * t * endValue.mY;
        return new CubicPathPoint(x, y);
    }
}
