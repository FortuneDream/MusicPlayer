package com.example.q.musicplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.musicplayer.R;
import com.example.q.musicplayer.model.Music;
import com.example.q.musicplayer.model.SearchMusic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by YQ on 2016/8/18.
 */
public class NetMusicAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<SearchMusic> musicArrayList;
        public NetMusicAdapter(Context context, ArrayList<SearchMusic> musicArrayList) {
            this.context = context;
            this.musicArrayList = musicArrayList;
        }

        @Override
        public int getCount() {
            return musicArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return musicArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View root = view;
            ViewHolder viewHolder;
            if (view == null) {
                root = View.inflate(context, R.layout.item_music, null);
            }
            viewHolder = ViewHolder.getHolder(root);
            SearchMusic music = (SearchMusic) getItem(i);
            viewHolder.musicArtTv.setText(music.getArtist());
            viewHolder.musicNameTv.setText(music.getMusicName());
            return root;
        }

        static class ViewHolder {
            ImageView musicPicIv;
            TextView musicNameTv;
            TextView musicArtTv;
            TextView durationTv;

            public static ViewHolder getHolder(View root) {
                Object tag = root.getTag();
                if (tag == null) {
                    ViewHolder viewHolder = new ViewHolder();
                    viewHolder.musicNameTv= (TextView) root.findViewById(R.id.music_name_tv);
                    viewHolder.musicArtTv= (TextView) root.findViewById(R.id.music_art_tv);
                    tag = viewHolder;
                    root.setTag(tag);
                }
                return (ViewHolder) tag;
            }

    }

}
