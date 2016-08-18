package com.example.q.musicplayer.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.q.musicplayer.Constant;
import com.example.q.musicplayer.LoadNetDateTask;
import com.example.q.musicplayer.R;
import com.example.q.musicplayer.utils.HideKeyBroadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YQ on 2016/8/16.
 */
public class NetMusicFragment extends Fragment {
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.net_music_lv)
    ListView netMusicLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yue_ku, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        loadNetData();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //提交输入框
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(getActivity(), "请输入关键字", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            //输入框文字改变
            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("TAG","onQueryTextChange");
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                HideKeyBroadUtils.hideSoftInput(getContext(),getActivity(),searchView);
                return true;
            }
        });
    }

    private void loadNetData() {
        new LoadNetDateTask(getContext(), netMusicLv).execute(Constant.BAIDU_URL + Constant.BAIDU_DATHOT);
    }


}