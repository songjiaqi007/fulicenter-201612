package cn.ucai.fulicenter.model.Dao;

import android.content.Context;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.SharePrefrenceUtils;

public class UserDao {
    public static final String USER_TABLE_NAME = "t_superwechat_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR_ID = "m_user_avatar_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME = "m_user_avatar_lastupdate_time";

    private static UserDao instance;

    public UserDao(Context context) {
        DBManager.getInstance().initDB(context);
    }

    public static UserDao getInstance(Context context) {
        if (instance==null) {
            instance = new UserDao(context);
        }
        return instance;
    }

    public boolean savaUser(User user) {
        return DBManager.getInstance().saveUserInfo(user);
    }

    public User getUser(String username) {
        return DBManager.getInstance().getUserInfo(username);
    }


    public void logout() {
        FuLiCenterApplication.setCurrentUser(null);
        SharePrefrenceUtils.getInstance().removeUser();
        DBManager.getInstance().closeDB();
    }
}
