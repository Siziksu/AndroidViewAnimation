package com.siziksu.va.ui.manager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.siziksu.va.R;
import com.siziksu.va.common.Constants;
import com.siziksu.va.ui.fragment.ProductsFragment;
import com.siziksu.va.ui.fragment.ProfileFragment;

public final class ContentManager {

    private final FragmentManager manager;

    private String section = "";

    public ContentManager(FragmentManager fragmentManager) {
        manager = fragmentManager;
    }

    public void show(String fragment) {
        if (!section.equals(fragment)) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit);
            switch (fragment) {
                case Constants.PRODUCTS_FRAGMENT:
                    transaction.replace(R.id.mainContent, new ProductsFragment(), Constants.PRODUCTS_FRAGMENT).commit();
                    section = fragment;
                    break;
                case Constants.PROFILE_FRAGMENT:
                    transaction.replace(R.id.mainContent, new ProfileFragment(), Constants.PROFILE_FRAGMENT).commit();
                    section = fragment;
                    break;
                default:
                    break;
            }
        }
    }

    public String getSection() {
        return section;
    }
}

