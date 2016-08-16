package com.example.q.musicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by YQ on 2016/7/21.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> list;
    private List<String> names;
    public SimpleFragmentPagerAdapter(FragmentManager fm,List<Fragment> list,List<String> names) {
        super(fm);
        this.list=list;
        this.names=names;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }
}