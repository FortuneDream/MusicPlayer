package com.example.q.musicplayer.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.q.musicplayer.model.SearchMusic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by YQ on 2016/8/18.
 */
//加载网络音乐的异步任务
public class LoadNetDateTask extends AsyncTask<String,Integer,ArrayList<SearchMusic>>{
    private ArrayList<SearchMusic> searchMusics=new ArrayList<>();
    private Context context;
    private ProgressBar progressBar;
    private OnSuccess onSuccess;
    public interface OnSuccess{
        void onResult(ArrayList<SearchMusic> searchMusics);
    }
    public LoadNetDateTask(Context context,  ProgressBar progressBar) {
        this.context=context;
        this.progressBar=progressBar;
    }

    public void setOnSuccess(OnSuccess onSuccess){
        this.onSuccess=onSuccess;
    }
    //异步任务的准备工作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        searchMusics.clear();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<SearchMusic> doInBackground(String... strings) {
        //取得地址
        String url=strings[0];
        try {
            //设置userAgent---用电脑访问
            Document doc=Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();//Get请求
//            Log.e("TAG", String.valueOf(doc)+"----DOC");
            Log.e("TAG","已经获得数据");
            Elements songTitles=doc.select("span.song-title");//查找所有的span  --class
            Elements artist=doc.select("span.author_list");
            for (int i=0;i<songTitles.size();i++){
                SearchMusic searchMusic=new SearchMusic();
                Elements urls=songTitles.get(i).getElementsByTag("a");//取出每一个span并找到第i个的<a href>标签
                searchMusic.setUrl(urls.get(0).attr("href"));//找到href属性
                searchMusic.setMusicName(urls.get(0).text());//值

                Elements artistElements=artist.get(i).getElementsByTag("a");
                searchMusic.setArtist(artistElements.get(0).text());
                searchMusic.setAlbum("新歌榜");
                searchMusics.add(searchMusic);
                Log.e("TAG",searchMusics.get(i).getMusicName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return searchMusics;
    }

    //后台任务完成

    @Override
    protected void onPostExecute(ArrayList<SearchMusic> list) {
        super.onPostExecute(list);
        if (onSuccess!=null){
            onSuccess.onResult(searchMusics);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}
