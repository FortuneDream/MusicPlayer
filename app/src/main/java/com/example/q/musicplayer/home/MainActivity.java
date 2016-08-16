package com.example.q.musicplayer.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.q.musicplayer.R;
import com.example.q.musicplayer.adapter.SimpleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    private List<Fragment> fragmentList;
    private List<String> names;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab)
    TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTabLayout();



    }

    private void initTabLayout() {
        names=new ArrayList<>();
        fragmentList=new ArrayList<>();
        MyMusicFragment myMusicFragment=new MyMusicFragment();
        NetMusicFragment netMusicFragment =new NetMusicFragment();
        fragmentList.add(myMusicFragment);
        fragmentList.add(netMusicFragment);
        names.add("我的音乐");
        names.add("网络音乐");
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,names));
        tab.setupWithViewPager(viewPager);
        tab.setTabMode(TabLayout.MODE_FIXED);
    }
}
