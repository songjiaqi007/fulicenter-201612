package cn.ucai.fulicenter.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.ui.adapter.CatFilterAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by liuning on 2017/3/18.
 */

public class CatChildFilterButton extends android.support.v7.widget.AppCompatButton {
    Context mContext;
    boolean isExpan = false;
    PopupWindow mPopupWindow;
    GridView gv;
    CatFilterAdapter adapter;
    ArrayList<CategoryChildBean> list = new ArrayList<>();



    public CatChildFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        Log.d("mingYue", "CatChildFilterButton: ");
        ShowArrow();
    }

    private void initPop() {
        Log.d("mingYue", "initPop: ");
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));

        mPopupWindow.setContentView(gv);
        mPopupWindow.showAsDropDown(this);
    }

    private void ShowArrow() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpan) {
                    initPop();
                } else {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                L.e(TAG, "setOnClickListener," + isExpan);
                Drawable end = getResources().getDrawable(isExpan ?
                        R.drawable.arrow2_down : R.drawable.arrow2_up);
                setCompoundDrawablesWithIntrinsicBounds(null, null, end, null);
                isExpan = !isExpan;
            }
        });
    }

    public void initView(String groupName, ArrayList<CategoryChildBean> l) {
        if (groupName==null || l== null) {
            CommonUtils.showShortToast("小类数据获取异常");
            return;
        }
        this.setText(groupName);
        list = l;

        gv = new GridView(mContext);
        Log.d("mingYue", "initView++++++: " + list.toString());
        adapter = new CatFilterAdapter(mContext, list, groupName);
        gv.setAdapter(adapter);
    }



}
