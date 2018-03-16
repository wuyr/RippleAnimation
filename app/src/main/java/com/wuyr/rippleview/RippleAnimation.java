package com.wuyr.rippleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuyr on 3/15/18 5:23 PM.
 */

public class RippleAnimation extends View {

    private Bitmap mBackground;
    private Paint mPaint;
    private int mMaxRadius, mStartRadius, mCurrentRadius;
    private boolean isStarted;
    private long mDuration;
    private float mStartX, mStartY;
    private ViewGroup mRootView;
    private OnAnimationEndListener mOnAnimationEndListener;
    private Animator.AnimatorListener mAnimatorListener;
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;

    public static RippleAnimation create(View onClickView) {
        Context context = onClickView.getContext();
        int newWidth = onClickView.getWidth() / 2;
        int newHeight = onClickView.getHeight() / 2;
        float startX = onClickView.getX() + newWidth;
        float startY = onClickView.getY() + newHeight;
        int radius = Math.max(newWidth, newHeight);
        return new RippleAnimation(context, startX, startY, radius);
    }

    private RippleAnimation(Context context, float startX, float startY, int radius) {
        super(context);
        mRootView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        mStartX = startX;
        mStartY = startY;
        mStartRadius = radius;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        updateMaxRadius();
        initListener();
    }

    private void initListener() {
        mAnimatorListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                detachFromRootView();
                if (mOnAnimationEndListener != null) {
                    mOnAnimationEndListener.onAnimationEnd();
                }
            }
        };
        mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getCurrentPlayTime() / mDuration;
                //更新圆的半径
                mCurrentRadius = (int) (mMaxRadius * progress) + mStartRadius;
                postInvalidate();
            }
        };
    }

    public void start() {
        if (!isStarted) {
            isStarted = true;
            updateBackground();
            attachToRootView();
            getAnimator().start();
        }
    }

    private void updateMaxRadius() {
        RectF leftTop = new RectF(0, 0, mStartX + mStartRadius, mStartY + mStartRadius);
        RectF rightTop = new RectF(leftTop.right, 0, mRootView.getRight(), leftTop.bottom);
        RectF leftBottom = new RectF(0, leftTop.bottom, leftTop.right, mRootView.getBottom());
        RectF rightBottom = new RectF(leftBottom.right, leftTop.bottom, mRootView.getRight(), leftBottom.bottom);

        double leftTopHypotenuse = Math.sqrt(Math.pow(leftTop.width(), 2) + Math.pow(leftTop.height(), 2));
        double rightTopHypotenuse = Math.sqrt(Math.pow(rightTop.width(), 2) + Math.pow(rightTop.height(), 2));
        double leftBottomHypotenuse = Math.sqrt(Math.pow(leftBottom.width(), 2) + Math.pow(leftBottom.height(), 2));
        double rightBottomHypotenuse = Math.sqrt(Math.pow(rightBottom.width(), 2) + Math.pow(rightBottom.height(), 2));

        mMaxRadius = (int) Math.max(
                Math.max(leftTopHypotenuse, rightTopHypotenuse),
                Math.max(leftBottomHypotenuse, rightBottomHypotenuse));
    }

    private void attachToRootView() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRootView.addView(this);
    }

    private void detachFromRootView() {
        mRootView.removeView(this);
    }

    public RippleAnimation setDuration(long duration) {
        mDuration = duration;
        return this;
    }

    private void updateBackground() {
        if (mBackground != null && !mBackground.isRecycled()) {
            mBackground.recycle();
        }
        mRootView.setDrawingCacheEnabled(true);
        mBackground = mRootView.getDrawingCache();
        mBackground = Bitmap.createBitmap(mBackground);
        mRootView.setDrawingCacheEnabled(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        canvas.drawBitmap(mBackground, 0, 0, null);
        canvas.drawCircle(mStartX, mStartY, mCurrentRadius, mPaint);
        canvas.restoreToCount(layer);
    }

    private ValueAnimator getAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 0).setDuration(mDuration);
        valueAnimator.addUpdateListener(mAnimatorUpdateListener);
        valueAnimator.addListener(mAnimatorListener);
        return valueAnimator;
    }

    public RippleAnimation setOnAnimationEndListener(OnAnimationEndListener listener) {
        mOnAnimationEndListener = listener;
        return this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public interface OnAnimationEndListener {
        void onAnimationEnd();
    }
}
