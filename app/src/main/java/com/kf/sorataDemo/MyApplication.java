package com.kf.sorataDemo;

import android.app.Application;
import android.content.Context;

import com.kf.sorataDemo.service.SorataInformationParse;

/**
 * Created by "sinlov" on 2017/2/3.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        new SorataInformationParse().infirmationParse(this,null);
    }
}
