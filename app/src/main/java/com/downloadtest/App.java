package com.downloadtest;

import android.app.Application;

import com.downloader.PRDownloader;

/**
 * Created by brain on 11/30/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PRDownloader.initialize(getApplicationContext());

    }
}
