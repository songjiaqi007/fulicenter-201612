package cn.ucai.fulicenter.application;

import android.app.Application;
import android.content.Context;

import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by liuning on 2017/3/14.
 */

public class FuLiCenterApplication extends Application {
    static FuLiCenterApplication instance;
    static User mUser;

    public static User getmUser() {
        return mUser;
    }

    public static void setmUser(User mUser) {
        FuLiCenterApplication.mUser = mUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }
}
