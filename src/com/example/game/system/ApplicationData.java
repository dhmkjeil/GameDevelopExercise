package com.example.game.system;

import android.app.Application;
import android.content.res.Configuration;
import android.media.MediaPlayer;

/**
 * Created by ION on 2015-06-16.
 */
public class ApplicationData extends Application{

    public MediaPlayer menuBGM;
    public MediaPlayer battleBGM;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
