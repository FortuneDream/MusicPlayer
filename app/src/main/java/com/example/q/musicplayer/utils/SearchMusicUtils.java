package com.example.q.musicplayer.utils;

import android.os.Handler;
import android.os.Message;

import com.example.q.musicplayer.Constant;
import com.example.q.musicplayer.model.SearchMusic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by YQ on 2016/8/18.
 */
public class SearchMusicUtils {
    private static final int SIZE = 20;//查询前20条
    private static final String URL = Constant.BAIDU_URL + Constant.BAIDU_SEARCH;
    private static SearchMusicUtils searchMusicUtils;
    private ExecutorService mThreadPool;
    private OnSearchResultListener searchResultListener;
    public static int getSIZE() {
        return SIZE;
    }

    public static String getURL() {
        return URL;
    }

    public static SearchMusicUtils getSearchMusicUtils() {
        return searchMusicUtils;
    }

    public static void setSearchMusicUtils(SearchMusicUtils searchMusicUtils) {
        SearchMusicUtils.searchMusicUtils = searchMusicUtils;
    }

    public OnSearchResultListener getSearchResultListener() {
        return searchResultListener;
    }

    public SearchMusicUtils setSearchResultListener(OnSearchResultListener searchResultListener) {
        this.searchResultListener = searchResultListener;
        return searchMusicUtils;
    }

    public interface OnSearchResultListener{
            void onSearchResult(ArrayList<SearchMusic> list);
    }
    private SearchMusicUtils() {
        mThreadPool= Executors.newCachedThreadPool();
    }
    public static synchronized SearchMusicUtils getInstance() {
        if (searchMusicUtils == null) {
            searchMusicUtils = new SearchMusicUtils();
        }
        return searchMusicUtils;
    }

    public void search(final String key, final int page){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case Constant.SUCCESS:
                        if (searchResultListener!=null){
                            searchResultListener.onSearchResult((ArrayList<SearchMusic>) msg.obj);
                        }
                        break;
                    case Constant.FAILED:
                        if (searchResultListener!=null){
                            searchResultListener.onSearchResult(null);
                        }
                        break;
                }
            }
        };
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<SearchMusic> results=getMusicList(key,page);
                if (results==null){
                    handler.sendEmptyMessage(Constant.FAILED);
                    return;
                }
                handler.obtainMessage(Constant.SUCCESS,results).sendToTarget();//简易方式
            }
        });
    }

    private ArrayList<SearchMusic> getMusicList(String key,int page){
        String start= String.valueOf((page-1)*SIZE);
        Elements songInfos;
        ArrayList<SearchMusic> searchMusics=new ArrayList<>();
        try {
            Document doc=Jsoup.connect(URL).data("key",key,"start",start,"size", String.valueOf(SIZE)).userAgent(Constant.USER_AGENT).timeout(6000).get();
            Elements urls=doc.select("div.song-item.clearfix");
            for (Element song: urls){
                songInfos=song.getElementsByTag("a");
                SearchMusic searchMusic=new SearchMusic();
                for (Element info:songInfos){
                    //收费则跳过
                    if (!info.attr("href").startsWith("http://y.baidu.com/song/")){
                        break;
                    }
                    //歌曲链接
                    if (info.attr("href").startsWith("/song")){
                        searchMusic.setMusicName(info.text());
                        searchMusic.setUrl(info.attr("href"));
                    }
                    //歌手链接
                    if (info.attr("href").startsWith("/data")){
                        searchMusic.setArtist(info.text());
                    }
                    if (info.attr("href").startsWith("album")){
                        searchMusic.setAlbum(info.text().replace("<<|>>",""));
                    }
                    searchMusics.add(searchMusic);
                }
            }
            return searchMusics;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }
}
