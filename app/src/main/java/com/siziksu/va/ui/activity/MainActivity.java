package com.siziksu.va.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.siziksu.va.R;
import com.siziksu.va.common.Constants;
import com.siziksu.va.common.MetricsUtils;
import com.siziksu.va.ui.activity.managers.AnimationManager;
import com.siziksu.va.ui.fragment.ProductsFragment;
import com.siziksu.va.ui.fragment.ProfileFragment;
import com.siziksu.va.ui.manager.ContentManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.mainContent)
    View mainContent;
    @BindView(R.id.mainMenu)
    View mainMenu;

    private static final String IS_ANIMATED = "is_animated";
    private static final String SECTION = "section";

    private AnimationManager animationManager;
    private ContentManager contentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentManager = new ContentManager(getSupportFragmentManager());
        ButterKnife.bind(this);
        MetricsUtils.init(this);
        animationManager = new AnimationManager(mainContent, mainMenu, MetricsUtils.get().getMetrics());
        animationManager.setPositionPercentageX(0.5f)
                        .setPositionPercentageY(0)
                        .withScaleCorrection(true)
                        .setScaleFactor(0.8f)
                        .setRotationX(0)
                        .setRotationY(-10)
                        .setRotationZ(0)
                        .setDepth(0)
                        .setMenuDelay(250);
        if (savedInstanceState == null) {
            contentManager.show(R.id.mainContent, new ProductsFragment(), Constants.PRODUCTS_FRAGMENT, false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(IS_ANIMATED, animationManager.isAnimated());
        savedInstanceState.putString(SECTION, contentManager.getSection());
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        boolean animated = savedInstanceState.getBoolean(IS_ANIMATED);
        animationManager.setAnimated(animated);
        String section = savedInstanceState.getString(SECTION);
        if (section != null) {
            switch (section) {
                case Constants.PRODUCTS_FRAGMENT:
                    contentManager.show(R.id.mainContent, new ProductsFragment(), Constants.PRODUCTS_FRAGMENT, false);
                    break;
                case Constants.PROFILE_FRAGMENT:
                    contentManager.show(R.id.mainContent, new ProfileFragment(), Constants.PROFILE_FRAGMENT, false);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (animationManager.isAnimated()) {
            animate();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.actionProducts)
    public void onProductsClick() {
        contentManager.show(R.id.mainContent, new ProductsFragment(), Constants.PRODUCTS_FRAGMENT, false);
        animate();
    }

    @OnClick(R.id.actionProfile)
    public void onProfileClick() {
        contentManager.show(R.id.mainContent, new ProfileFragment(), Constants.PROFILE_FRAGMENT, false);
        animate();
    }

    @Override
    public void animate() {
        animationManager.animate(() -> Log.i(Constants.TAG, "Animation finished"));
    }

    @Override
    public void animateIfAlreadyAnimated() {
        if (animationManager.isAnimated()) {
            animate();
        }
    }
}
