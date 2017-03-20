package cn.ucai.fulicenter.model.net;

import android.content.Context;

/**
 * Created by liuning on 2017/3/15.
 */

public interface IUserModel {
    void login(Context context, String username,String password, OnCompleteListener<String> listener);
    void register(Context context, String username, String nickname,String password,OnCompleteListener<String> listener);

}
