package com.siziksu.va.ui.activity.managers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private boolean animated = false;
    private float scaleFactor = 1f;
    private float position = 0.5f;
    private float rotationY;
    private float width;
    private boolean rotate;
    private long menuDelay;
    private View content;
    private View menu;

    public AnimationManager(View content, View menu, DisplayMetrics metrics) {
        this.content = content;
        this.menu = menu;
        this.width = metrics.widthPixels;
        this.menu.setTranslationX(-width);
    }

    public void animateViews() {
        animateViews(null);
    }

    public void animateViews(Done done) {
        if (width > 0) {
            if (position != 0) {
                if (!animated) {
                    animate(true, done);
                } else {
                    animate(false, done);
                }
            } else {
                animated = false;
            }
        }
    }

    private void animate(boolean animated, Done done) {
        animateContent(content);
        animateMenu(menu, () -> {
            if (done != null) {
                done.apply();
            }
            this.animated = animated;
        });
    }

    private void animateMenu(View view, Done done) {
        AnimatorSet animation = new AnimatorSet();
        setAnimationCallback(animation, done);
        List<Animator> list = new ArrayList<>();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, !animated ? 0 : -width);
        list.add(translation);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, !animated ? 1 : 0);
        list.add(alphaAnimator);
        animation.playTogether(list);
        animation.setStartDelay(!animated ? menuDelay : 0);
        animation.start();
    }

    private void animateContent(View view) {
        AnimatorSet animation = new AnimatorSet();
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
        animation.playTogether(list);
        animation.start();
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

    public AnimationManager setMenuDelay(long menuDelay) {
        this.menuDelay = menuDelay;
        return this;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        if (this.animated != animated) {
            animateViews();
        }
    }

    private void setAnimationCallback(AnimatorSet animation, Done done) {
        if (done != null) {
            animation.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    done.apply();
                }
            });
        }
    }

    public interface Done {

        void apply();
    }
}
