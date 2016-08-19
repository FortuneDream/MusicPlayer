package com.example.q.musicplayer.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.q.musicplayer.Constant;
import com.example.q.musicplayer.adapter.NetMusicAdapter;
import com.example.q.musicplayer.home.NetMusicFragment;
import com.example.q.musicplayer.model.SearchMusic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
    private NetMusicAdapter adaper;
    private ListView netMusicLv;
    private ProgressBar progressBar;

    public LoadNetDateTask(Context context, ListView netMusicLv, ProgressBar progressBar) {
        this.context=context;
        this.netMusicLv=netMusicLv;
        this.progressBar=progressBar;
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
        if (list!=null){
            adaper=new NetMusicAdapter(context,searchMusics);
            netMusicLv.setAdapter(adaper);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}
