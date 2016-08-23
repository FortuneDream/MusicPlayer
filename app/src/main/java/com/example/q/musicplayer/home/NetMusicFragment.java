package com.example.q.musicplayer.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.q.musicplayer.utils.Constant;
import com.example.q.musicplayer.R;
import com.example.q.musicplayer.adapter.NetMusicAdapter;
import com.example.q.musicplayer.model.SearchMusic;
import com.example.q.musicplayer.utils.DownLoadUtils;
import com.example.q.musicplayer.utils.HideKeyBroadUtils;
import com.example.q.musicplayer.utils.LoadNetDateTask;
import com.example.q.musicplayer.utils.SearchMusicUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by YQ on 2016/8/16.
 */
public class NetMusicFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.net_music_lv)
    ListView netMusicLv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private int page = 1;
    private NetMusicAdapter adaper;
    private ArrayList<SearchMusic> netMusicList;
    private ArrayList<SearchMusic> searchMusicList;
    private int NOW_LIST;
    private final int NET = 1;
    private final int SEARCH = 2;

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
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

    }

    private void loadNetData() {
        LoadNetDateTask loadNetDateTask = (LoadNetDateTask) new LoadNetDateTask(getContext(), progressBar).execute(Constant.BAIDU_URL + Constant.BAIDU_DATHOT);
        loadNetDateTask.setOnSuccess(new LoadNetDateTask.OnSuccess() {
            @Override
            public void onResult(ArrayList<SearchMusic> searchMusics) {
                //记住Java是值传递
                netMusicList = searchMusics;
                adaper = new NetMusicAdapter(getContext(), netMusicList);
                netMusicLv.setAdapter(adaper);
                NOW_LIST = NET;
            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("TAG", "Submit");
        HideKeyBroadUtils.hideSoftInput(getContext(), getActivity(), searchView);
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getActivity(), "请输入关键字", Toast.LENGTH_SHORT).show();
        }
        SearchMusicUtils.getInstance().setSearchResultListener(new SearchMusicUtils.OnSearchResultListener() {
            @Override
            public void onSearchResult(ArrayList<SearchMusic> list) {
                Log.e("TAG", "SearchMusics大小" + list.size());
                searchMusicList = list;
                adaper = new NetMusicAdapter(getContext(), searchMusicList);
                netMusicLv.setAdapter(adaper);
                progressBar.setVisibility(View.INVISIBLE);
                NOW_LIST = SEARCH;
            }
        }).search(query, page);
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        Log.e("TAG", "onQueryTextChange");
        return true;
    }

    //取消之后显示推荐音乐
    @Override
    public boolean onClose() {
        HideKeyBroadUtils.hideSoftInput(getContext(), getActivity(), searchView);
        adaper = new NetMusicAdapter(getContext(), netMusicList);
        netMusicLv.setAdapter(adaper);
        NOW_LIST = NET;
        return true;
    }

    @OnItemClick(R.id.net_music_lv)
    void onItemClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final SearchMusic searchMusic;
        if (NOW_LIST == NET) {
            searchMusic = netMusicList.get(position);
        } else {
            searchMusic = searchMusicList.get(position);
        }
        builder.setTitle("下载音乐").setMessage(searchMusic.getMusicName()).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "开始下载"+searchMusic.getMusicName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}