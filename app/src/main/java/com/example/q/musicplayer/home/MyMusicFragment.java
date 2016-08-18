package com.example.q.musicplayer.home;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.musicplayer.R;
import com.example.q.musicplayer.adapter.MusicListAdapter;
import com.example.q.musicplayer.model.Music;
import com.example.q.musicplayer.utils.MusicListUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by YQ on 2016/8/16.
 */
public class MyMusicFragment extends Fragment {
    @BindView(R.id.music_lv)
    ListView musicLv;
    @BindView(R.id.music_pic)
    GifImageView musicPic;
    @BindView(R.id.music_name)
    TextView musicName;
    @BindView(R.id.music_art)
    TextView musicArt;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.bottom_music)
    RelativeLayout bottomMusic;
    private ArrayList<Music> musicArrayList = new ArrayList<>();
    private MusicListAdapter adapter;
    public final int GET_ALL_MUSIC = 1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_ALL_MUSIC:
                    if (adapter==null){
                        adapter=new MusicListAdapter(getContext(),musicArrayList);
                        musicLv.setAdapter(adapter);
                    }
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_music, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        getAllMusic();
    }

    private void getAllMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MusicListUtils.getAllMusic(getContext(),musicArrayList);
                Message message=Message.obtain();
                message.what=GET_ALL_MUSIC;
                mHandler.sendMessage(message);
            }
        }).start();
    }


    @OnClick({R.id.play, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                break;
            case R.id.next:
                break;
        }
    }

}
