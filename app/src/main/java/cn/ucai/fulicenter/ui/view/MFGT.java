package cn.ucai.fulicenter.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.ui.activity.BoutiqueChileActivity;
import cn.ucai.fulicenter.ui.activity.CategoryChileActivity;
import cn.ucai.fulicenter.ui.activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.ui.activity.MainActivity;

/**
 * Created by liuning on 2017/3/16.
 */

public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void starActivity(Activity activity, Class cls) {
        activity.startActivity(new Intent(activity,cls));
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public static void statActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public static void gotoMain(Activity activity) {
        starActivity(activity, MainActivity.class);
    }

    public static void gotoBoutiqueChild(Context activity, BoutiqueBean bean) {
         activity.startActivity(new Intent(activity, BoutiqueChileActivity.class)
        .putExtra(I.NewAndBoutiqueGoods.CAT_ID,bean.getId())
        .putExtra(I.Boutique.TITLE,bean.getTitle()));

    }



    public static void gotoDetails(Context activity, int goodsId) {
        activity.startActivity(new Intent(activity, GoodsDetailsActivity.class)
        .putExtra(I.Goods.KEY_GOODS_ID,goodsId));
    }

    public static void gotoCategoryChild(Context activity, int catId, String groupName,
                                         ArrayList<CategoryChildBean> list) {
        activity.startActivity(new Intent(activity, CategoryChileActivity.class)
        .putExtra(I.NewAndBoutiqueGoods.CAT_ID,catId)
        .putExtra(I.CategoryGroup.NAME,groupName)
        .putExtra(I.CategoryChild.DATA,list));
    }
}
