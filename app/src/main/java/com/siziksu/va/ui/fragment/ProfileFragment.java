package com.siziksu.va.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.va.R;
import com.siziksu.va.ui.activity.IMainView;
import com.siziksu.va.ui.activity.managers.ActionBarManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ProfileFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private IMainView parent;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        parent = (IMainView) getActivity();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBarManager actionBarManager = new ActionBarManager();
        actionBarManager.setActionBar(((AppCompatActivity) getActivity()).getSupportActionBar());
    }

    @OnClick(R.id.actionMenu)
    public void onToolbarClick() {
        parent.animate();
    }
}
