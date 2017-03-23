package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;

/**
 * Created by liuning on 2017/3/15.
 */

public interface IUserModel {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);

    void register(Context context, String username, String nickname, String password, OnCompleteListener<String> listener);

    void updateNick(Context context, String username, String newnick, OnCompleteListener<String> listener);

    void updateAvatar(Context context, String username, File file, OnCompleteListener<String> listener);

    void loadCollectsCount(Context context, String username, OnCompleteListener<MessageBean> listener);

    void loadCollects(Context context, String username, int pageId, int pageSize,
                      OnCompleteListener<CollectBean[]> listener);


}
