package com.mllweb.banner;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by AndyJeason on 2016/9/30.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
    }
}
