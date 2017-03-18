package cn.ucai.fulicenter.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.utils.L;

import static android.content.ContentValues.TAG;

/**
 * Created by liuning on 2017/3/18.
 */

public class CatChildFilterButton extends android.support.v7.widget.AppCompatButton{
    Context mContext;
    boolean isExpan = false;
    PopupWindow mPopupWindow;

    public CatChildFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        ShowArrow();
    }

    private void initPop() {
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        TextView tv = new TextView(mContext);
        tv.setTextColor(getResources().getColor(R.color.red));
        tv.setTextSize(30);
        tv.setText("CatChildFilterButton");
        mPopupWindow.setContentView(tv);
        mPopupWindow.showAsDropDown(this);
    }

    private void ShowArrow() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpan) {
                   initPop();
                }else {
                    if (mPopupWindow!=null&&mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                L.e(TAG, "setOnClickListener," + isExpan);
                Drawable end = getResources().getDrawable(isExpan ?
                        R.drawable.arrow2_up : R.drawable.arrow2_down);
                setCompoundDrawablesWithIntrinsicBounds(null, null, end, null);
                isExpan = !isExpan;
            }
        });
    }
}
