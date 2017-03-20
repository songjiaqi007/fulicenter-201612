package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by liuning on 2017/3/16.
 */

public class UserModel implements IUserModel {


    @Override
    public void login(Context context, String username,String password, OnCompleteListener<String> listener) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.PASSWORD,password)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void register(Context context, String username, String nickname,String password, OnCompleteListener<String> listener) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.PASSWORD, password)
                .addParam(I.User.NICK, nickname)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }
}
