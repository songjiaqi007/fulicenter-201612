package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by liuning on 2017/3/15.
 */

public interface IBoutiqueModel {
    void loadData(Context context, OnCompleteListener<BoutiqueBean[]> listener);

}
