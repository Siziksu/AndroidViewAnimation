package com.siziksu.va.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.va.R;
import com.siziksu.va.ui.activity.IMainView;
import com.siziksu.va.ui.activity.adapter.ProductsAdapter;
import com.siziksu.va.ui.activity.managers.ActionBarManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ProductsFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final int SPAN_COUNT = 2;

    private IMainView parent;
    private ProductsAdapter adapter;

    public ProductsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        parent = (IMainView) getActivity();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBarManager actionBarManager = new ActionBarManager();
        actionBarManager.setActionBar(((AppCompatActivity) getActivity()).getSupportActionBar());
        adapter = new ProductsAdapter(getActivity(), (clickedView, position) -> {
            Snackbar.make(view, "Product " + (position + 1) + " clicked", Snackbar.LENGTH_SHORT).show();
            parent.animateIfAlreadyAnimated();
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        showStrings();
    }

    @OnClick(R.id.actionMenu)
    public void onToolbarClick() {
        parent.animate();
    }

    private void showStrings() {
        List<String> strings = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            strings.add("Element " + i);
        }
        adapter.showProducts(strings);
    }
}
