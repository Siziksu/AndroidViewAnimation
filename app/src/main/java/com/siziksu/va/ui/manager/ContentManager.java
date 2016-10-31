package com.siziksu.va.ui.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.siziksu.va.R;
import com.siziksu.va.ui.fragment.ProductsFragment;
import com.siziksu.va.ui.fragment.ProfileFragment;

import java.util.HashMap;
import java.util.Map;

public final class ContentManager {

    public static final String PRODUCTS = "products";
    public static final String PROFILE = "profile";

    private Map<String, Fragment> fragments = new HashMap<>();
    private final FragmentManager manager;
    private String section;

    public ContentManager(FragmentManager fragmentManager) {
        manager = fragmentManager;
        fragments.put(PRODUCTS, new ProductsFragment());
        fragments.put(PROFILE, new ProfileFragment());
    }

    public void show(String fragment) {
        if (fragments.containsKey(fragment)) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.mainContent, fragments.get(fragment), fragment).commit();
            section = fragment;
        }
    }

    public String getSection() {
        return section;
    }
}
