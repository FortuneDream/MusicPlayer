package com.example.q.musicplayer.home;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.musicplayer.R;
import com.example.q.musicplayer.model.Music;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by YQ on 2016/8/16.
 */
public class MyMusicFragment extends Fragment {
    //    @BindView(R.id.music_pic)
//    ImageView musicPic;

    public final int GET_ALL_MUSIC = 1;
    @BindView(R.id.music_lv)
    ListView musicLv;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_music, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        //musicPic.setImageResource(R.drawable.ico_huaji);
    }

    private void init() {
        getAllMusic();
    }

    private void getAllMusic() {
        new Thread() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContext().getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projection = {
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                };
                Cursor cursor = contentResolver.query(uri, projection, null, null, null);
                Log.e("TAG", String.valueOf(cursor));
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Music music = new Music();
                        music.setUrl(cursor.getString(0));
                        music.setDuration(cursor.getString(1));
                        music.setSize(cursor.getLong(2));
                        music.setName(cursor.getString(3));
                        music.setArtist(cursor.getString(4));
                        musicArrayList.add(music);
                    }
                    cursor.close();
                }
            }

        }.start();
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
