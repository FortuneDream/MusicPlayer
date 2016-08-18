package com.example.q.musicplayer.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.q.musicplayer.model.Music;

import java.util.ArrayList;

/**
 * Created by YQ on 2016/8/18.
 */
public class MusicListUtils {
    public  static void getAllMusic(final Context context, ArrayList<Music> musicArrayList) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.IS_MUSIC};
        Cursor cursor = contentResolver.query(uri,projection,MediaStore.Audio.Media.DURATION+">=180000",null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        Log.e("TAG", String.valueOf(cursor));
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Music music = new Music();
                music.setId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                music.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                music.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                music.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                music.setAlbumId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                music.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                music.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
                music.setUrl(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                music.setIsMusic(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)));
                if (music.getIsMusic()!=0){
                    musicArrayList.add(music);
                }
            }
            cursor.close();
        }
    }
}
