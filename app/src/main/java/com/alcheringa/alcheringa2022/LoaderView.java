package com.alcheringa.alcheringa2022;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class LoaderView extends View {
    // distance between neighbour dot centres
    private int mDotStep = 50;

    // actual dot radius
    private int mDotRadius = 14;

    // to get identified in which position dot has to bounce
    private int mDotPosition;

    // specify how many dots you need in a progressbar
    private final int mDotCount = 3;
    private final int mTimeout = 400;
    private int mDotColor = Color.parseColor("#EE6337");

    public LoaderView(Context context) {
        super(context);
        initDotSize();
    }

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDotSize();
    }

    public LoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDotSize();
    }

    private void initDotSize() {
        final float scale = getResources().getDisplayMetrics().density;
        mDotStep = (int)(mDotStep * scale);
        mDotRadius = (int)(mDotRadius * scale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShown()) {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor(("#A700010D")));
            canvas.drawRect(0,0,canvas.getWidth(), canvas.getHeight(), paint);
            createDots(canvas, paint);
            paint.setAntiAlias(true);
            paint.setDither(true);

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    private void createDots(Canvas canvas, Paint paint) {
        for (int i = 0; i < mDotCount; i++ ) {
            if(i==0){
                paint.setColor(Color.parseColor("#EE6337"));
                if(i==mDotPosition){
                    paint.setStyle(Paint.Style.FILL);
                }else{
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(4F);
                }
            }else if(i==1){
                paint.setColor(Color.parseColor("#7D95E4"));
                if (i == mDotPosition) {
                    paint.setStyle(Paint.Style.FILL);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(4F);
                }
            }else{
                paint.setColor(Color.parseColor("#11D3D3"));
                if (i == mDotPosition) {
                    paint.setStyle(Paint.Style.FILL);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(4F);
                }
            }
            canvas.drawCircle(canvas.getWidth()/2 + ((i-1) * mDotStep), canvas.getHeight()/2 + mDotRadius, mDotRadius, paint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // MUST CALL THIS
        setMeasuredDimension(mDotStep * mDotCount, mDotRadius * 2);
    }

    private void startAnimation() {
        BounceAnimation bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(mTimeout);
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (++mDotPosition >= mDotCount) {
                    mDotPosition = 0;
                }
            }
        });
        startAnimation(bounceAnimation);
    }


    private class BounceAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            // call invalidate to redraw your view again
            invalidate();
        }
    }
}