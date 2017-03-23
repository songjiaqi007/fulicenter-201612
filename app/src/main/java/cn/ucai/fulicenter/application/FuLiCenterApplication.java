package cn.ucai.fulicenter.application;

import android.app.Application;
import android.content.Context;

import cn.ucai.fulicenter.model.Dao.UserDao;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.SharePrefrenceUtils;

/**
 * Created by liuning on 2017/3/14.
 */

public class FuLiCenterApplication extends Application {
    static FuLiCenterApplication instance;
    static User currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }


    public static User getCurrentUser() {
        if (currentUser==null) {
            final String username = SharePrefrenceUtils.getInstance().getUserName();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    currentUser = UserDao.getInstance(instance).getUser(username);
                }
            }).start();
        }
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        FuLiCenterApplication.currentUser = currentUser;
    }
}
