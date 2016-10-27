package com.siziksu.va.ui.activity.managers;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

import com.siziksu.va.R;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private boolean animated = false;
    private float scaleFactor = 1f;
    private float position = 0.5f;
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
                    animateFull(view, position, scaleFactor, R.animator.rotate, done);
                    animated = true;
                } else {
                    animateFull(view, 0, 1, R.animator.rotate_back, done);
                    animated = false;
                }
            } else {
                animated = false;
            }
        }
    }

    private void animateFull(View view, float x, float scaleFactor, int animatorXml, Done done) {
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
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, x * scaleFactor);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scaleFactor);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scaleFactor);
        ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY);
        list.add(translation);
        list.add(scale);
        if (rotate) {
            ObjectAnimator rotate = (ObjectAnimator) AnimatorInflater.loadAnimator(view.getContext(), animatorXml);
            rotate.setTarget(view);
            list.add(rotate);
        }
        if (alpha < 1f && alpha > 0f) {
            ObjectAnimator alphaAnimator;
            if (!animated) {
                alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1, alpha);
            } else {
                alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, alpha, 1);
            }
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

    public AnimationManager setScaleFactor(float factor) {
        this.scaleFactor = factor;
        return this;
    }

    public AnimationManager setRotate3d(boolean value) {
        this.rotate = value;
        return this;
    }

    public AnimationManager setAlpha(float alpha) {
        this.alpha = alpha;
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
