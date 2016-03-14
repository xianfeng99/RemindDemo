package com.jikexueyuan.timer;

import android.app.Application;

/**
 * Created by lixianfeng on 16/3/14.
 */
public class RemindApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RemindUtil.initDataBase(this);

    }
}
