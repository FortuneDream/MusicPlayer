package com.example.q.musicplayer.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.example.q.musicplayer.model.SearchMusic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by YQ on 2016/8/23.
 */
//然而。。。百度音乐已经封了接口。。弃之
public class DownLoadUtils {
    private static final int SUCCESS_LRC = 0;
    private static final int FILED_LRC = 1;
    private static final int SUCCESS_MP3 = 2;
    private static final int FAILED_MP3 = 3;
    private static final int GET_MP3_URL = 4;
    private static final int GET_FAILED_MP3_URL = 5;
    private static final int MUSIC_EXISTS = 6;
    private SearchMusic resultMusic;
    private String url;
    private static final String DOWNLOAD_URL="/download?__o=%2Fsearch%2Fsong";
    private Context context;
    private OnDownloadListener onDownloadListener;
    private static  DownLoadUtils downLoadUtils;
    private ExecutorService mThreadPool;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_MP3_URL://开始下载
                    downloadMusic(resultMusic,msg.obj);
                    downloadLrc();
                    break;
                case SUCCESS_LRC:
                    if (onDownloadListener!=null){
                        onDownloadListener.onSucceed("歌词下载成功");
                    }
                    break;
                case FILED_LRC:
                    if (onDownloadListener!=null){
                        onDownloadListener.onFailed("歌词下载失败");
                    }
                    break;
                case SUCCESS_MP3:
                    if (onDownloadListener!=null){
                        onDownloadListener.onSucceed("...");
                    }
                    break;
                case FAILED_MP3:
                    if (onDownloadListener!=null){
                        onDownloadListener.onFailed("...");
                    }
                    break;
                case GET_FAILED_MP3_URL:
                    if (onDownloadListener!=null){
                        onDownloadListener.onFailed("下载失败，该歌曲为收费或VIP类型");
                    }
                    break;
                case MUSIC_EXISTS:
                    if (onDownloadListener!=null){
                        onDownloadListener.onFailed("音乐已存在");
                    }
                    break;

            }
        }
    };

    private void downloadMusic(SearchMusic resultMusic, Object obj) {

    }
    private void downloadLrc(){

    }

    public OnDownloadListener getOnDownloadListener() {
        return onDownloadListener;
    }

    public DownLoadUtils setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
        return this;
    }
    public interface OnDownloadListener{
        void onSucceed(String msg);
        void onFailed(String error);
    }
    public static synchronized DownLoadUtils getInstance(){
        if (downLoadUtils==null){
            downLoadUtils=new DownLoadUtils();
        }
        return downLoadUtils;
    }

    private DownLoadUtils() {
        mThreadPool=  Executors.newSingleThreadExecutor();
    }

    //    private DownLoadUtils(String url,Context context){
//        this.url=url;
//        this.context=context;
////        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
////        request.setDestinationInExternalPublicDir("/download/","download1");
////        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
////        downloadManager.enqueue(request);
//    }
    public void download(final SearchMusic resultMusic){
        this.resultMusic=resultMusic;
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                String url=Constant.BAIDU_URL+"/song/"+resultMusic.getUrl().substring(resultMusic.getUrl().lastIndexOf("/")+1)+DOWNLOAD_URL;
                try {
                    Document doc= Jsoup.connect(url).userAgent(Constant.USER_AGENT).timeout(6000).get();
                    Elements targetElements=doc.select("a[data-btndata]");
                    if (targetElements.size()<=0){
                        handler.obtainMessage(GET_FAILED_MP3_URL).sendToTarget();
                        return;
                    }
                    for (Element e:targetElements){
                        if (e.attr("href").contains(".mp3")){
                            String result=e.attr("href");
                            Message message=handler.obtainMessage(GET_MP3_URL,result);
                            message.sendToTarget();
                            return;
                        }
                        if (e.attr("href").startsWith("/vip")){
                            targetElements.remove(e);
                        }
                    }
                    if (targetElements.size()<=0){
                        handler.obtainMessage(GET_FAILED_MP3_URL).sendToTarget();;
                        return;
                    }
                    String result=targetElements.get(0).attr("href");
                    Message message=handler.obtainMessage(GET_MP3_URL,result);
                    message.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.obtainMessage(GET_FAILED_MP3_URL).sendToTarget();
                }
            }
        });
    }
}
