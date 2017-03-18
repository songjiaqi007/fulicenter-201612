package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;

/**
 * Created by liuning on 2017/3/15.
 */

public interface ICategroyModel {
    void loadGroupData(Context context, OnCompleteListener<CategoryGroupBean[]> listener);


    void loadChildData(Context context, int parentId, OnCompleteListener<CategoryChildBean[]> listener);

}


