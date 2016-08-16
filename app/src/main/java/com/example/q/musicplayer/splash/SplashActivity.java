package com.example.q.musicplayer.splash;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import com.example.q.musicplayer.home.MainActivity;
import com.example.q.musicplayer.R;

public class SplashActivity extends AppCompatActivity {
    private static final int START_ACTIVITY = 1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case START_ACTIVITY:
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.sendEmptyMessageDelayed(START_ACTIVITY,2000);
    }

}
