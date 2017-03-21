package cn.ucai.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.ucai.fulicenter.application.FuLiCenterApplication;

/**
 * Created by liuning on 2017/3/21.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_PREFRENCE_NAME = "cn.ucai.fulicenter_save_userinfo";
    private static final String SAVE_USERINFO_USERNAME = "m_user_username";
    static SharePrefrenceUtils instance;
    Context mContext;
    SharedPreferences sharePrefrences;
    SharedPreferences.Editor editor;

    public SharePrefrenceUtils() {
        sharePrefrences = FuLiCenterApplication.getInstance().
                getSharedPreferences(SHARE_PREFRENCE_NAME, Context.MODE_PRIVATE);
        editor = sharePrefrences.edit();
    }

    public static SharePrefrenceUtils getInstance(){
        if (instance==null) {
            instance = new SharePrefrenceUtils();
        }
        return instance;
    }

    public void setUserName(String username) {
        editor.putString(SAVE_USERINFO_USERNAME, username).commit();
    }

    public String getUserName() {
        return sharePrefrences.getString(SAVE_USERINFO_USERNAME, null);
    }

    public void removeUser() {
        editor.remove(SAVE_USERINFO_USERNAME).commit();
    }
}

