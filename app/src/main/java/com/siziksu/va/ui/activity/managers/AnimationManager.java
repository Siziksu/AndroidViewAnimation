package com.siziksu.va.ui.activity.managers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private boolean animated = false;
    private float scaleFactor = 1f;
    private float position = 0.5f;
    private float rotationY;
    private float width;
    private float alpha = 1f;
    private boolean rotate;

    public AnimationManager() {}

    public void animateView(View view) {
        animateView(view, null);
    }

    public void animateView(View view, Done done) {
        if (width > 0) {
            if (position != 0) {
                if (!animated) {
                    animateFull(view, done);
                    animated = true;
                } else {
                    animateFull(view, done);
                    animated = false;
                }
            } else {
                animated = false;
            }
        }
    }

    private void animateFull(View view, Done done) {
        AnimatorSet animation = new AnimatorSet();
        animation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (done != null) {
                    done.apply();
                }
            }
        });
        List<Animator> list = new ArrayList<>();
        float scaleFactor = (!animated ? this.scaleFactor : 1);
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, (!animated ? position : 0) * scaleFactor);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scaleFactor);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scaleFactor);
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        list.add(translation);
        list.add(scale);
        if (rotate) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(view, View.ROTATION_Y, !animated ? rotationY : 0);
            rotation.start();
            list.add(rotation);
        }
        if (alpha <= 1 && alpha > 0) {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, !animated ? alpha : 1);
            list.add(alphaAnimator);
        }
        animation.playTogether(list);
        animation.start();
    }

    public AnimationManager setWidth(float width) {
        this.width = width;
        return this;
    }

    public AnimationManager setPositionPercentage(float percent) {
        position = width * percent;
        return this;
    }

    public AnimationManager setScaleFactor(float value) {
        this.scaleFactor = value;
        return this;
    }

    public AnimationManager setYRotation(float value) {
        this.rotationY = value;
        this.rotate = true;
        return this;
    }

    public AnimationManager setAlpha(float value) {
        this.alpha = value;
        return this;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(View view, boolean animated) {
        if (this.animated != animated) {
            animateView(view);
        }
    }

    public interface Done {

        void apply();
    }
}
