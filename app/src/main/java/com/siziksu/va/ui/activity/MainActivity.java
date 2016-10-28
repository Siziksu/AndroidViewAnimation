package com.siziksu.va.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;

import com.siziksu.va.R;
import com.siziksu.va.common.Constants;
import com.siziksu.va.common.MetricsUtils;
import com.siziksu.va.ui.activity.adapter.ProductsAdapter;
import com.siziksu.va.ui.activity.managers.ActionBarManager;
import com.siziksu.va.ui.activity.managers.AnimationManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mainContent)
    RelativeLayout mainContent;

    private static final String IS_ANIMATED = "is_animated";
    private static final int SPAN_COUNT = 2;

    private ProductsAdapter adapter;
    private AnimationManager animationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarManager actionBarManager = new ActionBarManager();
        actionBarManager.setActionBar(getSupportActionBar());
        MetricsUtils.init(this);
        animationManager = new AnimationManager();
        animationManager.setWidth(MetricsUtils.get().getWidth())
                        .setPositionPercentage(0.5f)
                        .setScaleFactor(0.8f)
                        .setYRotation(-10f);
        adapter = new ProductsAdapter(getApplicationContext(), (view, position) -> {
            Snackbar.make(mainContent, "Product " + (position + 1) + " clicked", Snackbar.LENGTH_SHORT).show();
            if (animationManager.isAnimated()) {
                animate();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(IS_ANIMATED, animationManager.isAnimated());
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        boolean animated = savedInstanceState.getBoolean(IS_ANIMATED);
        animationManager.setAnimated(mainContent, animated);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showStrings();
    }

    @Override
    public void onBackPressed() {
        if (animationManager.isAnimated()) {
            animate();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.departure)
    public void onToolbarClick() {
        animate();
    }

    @OnClick(R.id.products)
    public void onProductsClick() {
        Snackbar.make(mainContent, "Products", Snackbar.LENGTH_SHORT).show();
        animate();
    }

    private void animate() {
        animationManager.animateView(mainContent, () -> Log.i(Constants.TAG, "Animation finished"));
    }

    private void showStrings() {
        List<String> strings = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            strings.add("Element " + i);
        }
        adapter.showProducts(strings);
    }
}
