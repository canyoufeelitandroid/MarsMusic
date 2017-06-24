package com.byhuang.marsmusic.marsmusic.Listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.byhuang.marsmusic.marsmusic.R;

/**
 * Created by 64088 on 2017/6/21.
 */

public class MyTabListener implements ActionBar.TabListener {
    private Fragment fragment;

    public MyTabListener(Fragment fragment1){
        this.fragment=fragment1;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.replace_place_1,fragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
