package com.siziksu.va.ui.activity.managers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import com.siziksu.va.common.model.AnimationInfo;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private static final int MAX_DEGREE = 360;

    private boolean animated = false;
    private float scaleFactor = 1f;
    private boolean scaleCorrection;
    private float depth;
    private float positionX;
    private float positionY;
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private float width;
    private float height;
    private long menuDelay;
    private View content;
    private View menu;
    private float density;

    public AnimationManager(View content, View menu, DisplayMetrics metrics) {
        this.content = content;
        this.menu = menu;
        this.width = metrics.widthPixels;
        this.height = metrics.heightPixels;
        this.density = metrics.density;
        this.menu.setTranslationX(-width);
    }

    public void animate() {
        animate(null);
    }

    public void animate(Done done) {
        if (width > 0) {
            if (!animated) {
                animate(true, done);
            } else {
                animate(false, done);
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
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, !animated ? 1 : 0);
        list.add(translation);
        list.add(alphaAnimator);
        animation.playTogether(list);
        animation.setStartDelay(!animated ? menuDelay : 0);
        animation.start();
    }

    private void animateContent(View view) {
        view.setPivotY(getDistanceToCenterY(view));
        view.setPivotX(getDistanceToCenterX(view));
        view.setCameraDistance((depth == 0 ? height : depth) * density);
        AnimationInfo animationInfo = new AnimationInfo();
        animationInfo.addView(view);
        if (!animated) {
            animationInfo.setValues(positionX, positionY, rotationX, rotationY, rotationZ, scaleFactor, scaleCorrection);
            animateMe(animationInfo);
        } else {
            animationInfo.setValues(0, 0, 0, 0, 0, 1, false);
            animateMe(animationInfo);
        }
    }

    private void animateMe(AnimationInfo animationInfo) {
        AnimatorSet animation = new AnimatorSet();
        List<Animator> list = new ArrayList<>();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(
                animationInfo.getView(),
                View.TRANSLATION_X,
                animationInfo.getPositionX() * (animationInfo.isScaleCorrection() ? animationInfo.getScaleFactor() : 1)
        );
        ObjectAnimator translationY = ObjectAnimator.ofFloat(
                animationInfo.getView(),
                View.TRANSLATION_Y,
                animationInfo.getPositionY() * (animationInfo.isScaleCorrection() ? animationInfo.getScaleFactor() : 1)
        );
        ObjectAnimator rotationX = ObjectAnimator.ofFloat(animationInfo.getView(), View.ROTATION_X, animationInfo.getRotationX());
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(animationInfo.getView(), View.ROTATION_Y, animationInfo.getRotationY());
        ObjectAnimator rotationZ = ObjectAnimator.ofFloat(animationInfo.getView(), View.ROTATION, animationInfo.getRotationZ());
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(animationInfo.getView(), View.SCALE_X, animationInfo.getScaleFactor());
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(animationInfo.getView(), View.SCALE_Y, animationInfo.getScaleFactor());
        list.add(translationX);
        list.add(translationY);
        list.add(scaleX);
        list.add(scaleY);
        list.add(rotationX);
        list.add(rotationY);
        list.add(rotationZ);
        animation.playTogether(list);
        animation.start();
    }

    private static float getDistanceToCenterX(View view) {
        float viewCenter = view.getLeft() + view.getWidth() / 2f;
        float rootCenter = ((View) view.getParent()).getWidth() / 2;
        return view.getWidth() / 2f + rootCenter - viewCenter;
    }

    private static float getDistanceToCenterY(View view) {
        float viewCenter = view.getTop() + view.getHeight() / 2f;
        float rootCenter = ((View) view.getParent()).getHeight() / 2;
        return view.getHeight() / 2f + rootCenter - viewCenter;
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

    public AnimationManager setPositionPercentageX(float value) {
        positionX = width * (value >= 0 && value <= 1 ? value : 0);
        return this;
    }

    public AnimationManager setPositionPercentageY(float value) {
        positionY = height * (value >= 0 && value <= 1 ? value : 0);
        return this;
    }

    public AnimationManager setScaleFactor(float value) {
        scaleFactor = (value > 0 && value < 1 ? value : 1);
        return this;
    }

    public AnimationManager setRotationX(float value) {
        rotationX = (value >= -MAX_DEGREE && value <= MAX_DEGREE ? value : 0);
        return this;
    }

    public AnimationManager setRotationY(float value) {
        rotationY = (value >= -MAX_DEGREE && value <= MAX_DEGREE ? value : 0);
        return this;
    }

    public AnimationManager setRotationZ(float value) {
        rotationZ = (value >= -MAX_DEGREE && value <= MAX_DEGREE ? value : 0);
        return this;
    }

    public AnimationManager setDepth(float value) {
        depth = (value > 0 && value < 11 ? value * width : width);
        return this;
    }

    public AnimationManager setMenuDelay(long value) {
        menuDelay = value;
        return this;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean value) {
        if (animated != value) {
            animate();
        }
    }

    public AnimationManager withScaleCorrection(boolean value) {
        scaleCorrection = value;
        return this;
    }

    public interface Done {

        void apply();
    }
}
