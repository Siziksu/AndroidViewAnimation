package com.siziksu.va.common.model;

import android.view.View;

public class AnimationInfo {

    private View view;
    private float positionX;
    private float positionY;
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private float scaleFactor = 1f;
    private boolean scaleCorrection;

    public void addView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setValues(float positionX, float positionY, float rotationX, float rotationY, float rotationZ, float scaleFactor, boolean scaleCorrection) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.scaleFactor = scaleFactor;
        this.scaleCorrection = scaleCorrection;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getRotationX() {
        return rotationX;
    }

    public float getRotationY() {
        return rotationY;
    }

    public float getRotationZ() {
        return rotationZ;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public boolean isScaleCorrection() {
        return scaleCorrection;
    }
}
