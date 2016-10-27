package com.siziksu.va.ui.activity.managers;

import android.support.v7.app.ActionBar;

public class ActionBarManager {

    public ActionBarManager() {}

    public void setActionBar(ActionBar bar) {
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false);
            bar.setDisplayHomeAsUpEnabled(false);
            bar.setHomeButtonEnabled(false);
        }
    }
}
