package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;

/**
 * Created by liuning on 2017/3/15.
 */

public interface IGoodsModel {
    void loadData(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener);

    void collectAction(Context context, int action,int goodsId, String username,
                           OnCompleteListener<MessageBean> listener);

    void deleteCollect(Context context, String username, int goodsId,
                       OnCompleteListener<CollectBean> listener);
}
