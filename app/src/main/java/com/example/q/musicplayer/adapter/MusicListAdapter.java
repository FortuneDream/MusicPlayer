package com.example.q.musicplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.musicplayer.R;
import com.example.q.musicplayer.model.Music;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YQ on 2016/8/18.
 */
public class MusicListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Music> musicArrayList;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
    public MusicListAdapter(Context context, ArrayList<Music> musicArrayList) {
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
        Music music = (Music) getItem(i);
        viewHolder.musicArtTv.setText(music.getArtist()+" ");
        viewHolder.musicNameTv.setText(music.getName());
        viewHolder.durationTv.setText(simpleDateFormat.format(new Date(music.getDuration())));
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
                viewHolder.durationTv = (TextView) root.findViewById(R.id.duration_tv);
                viewHolder.musicNameTv= (TextView) root.findViewById(R.id.music_name_tv);
                viewHolder.musicArtTv= (TextView) root.findViewById(R.id.music_art_tv);
                tag = viewHolder;
                root.setTag(tag);
            }
            return (ViewHolder) tag;
        }
    }
}
