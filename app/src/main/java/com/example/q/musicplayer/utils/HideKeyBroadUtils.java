package com.example.q.musicplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

/**
 * Created by YQ on 2016/8/18.
 */
public class HideKeyBroadUtils {
    public static void hideSoftInput(Context context, Activity activity, SearchView searchView){
        InputMethodManager inputMethodManager= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager!=null){
            View v= activity.getCurrentFocus();
            if (v==null){
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            searchView.clearFocus();
        }
    }
}
