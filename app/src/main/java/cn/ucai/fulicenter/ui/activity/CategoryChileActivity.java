package cn.ucai.fulicenter.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.ui.fragment.NewGoodsFragment;

/**
 * Created by liuning on 2017/3/17.
 */

public class CategoryChileActivity extends AppCompatActivity {
    boolean sortPrice;
    boolean sortAddTime;
    int sortBy = I.SORT_BY_ADDTIME_DESC;
    NewGoodsFragment mNewGoodsFragment;
    @BindView(R.id.btn_sort_price)
    Button mbtnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button mbtnSortAddtime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_category_child);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .commit();
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void sortList(View view) {
        Drawable end = null;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                sortBy = sortPrice ? I.SORT_BY_PRICE_ASC : I.SORT_BY_PRICE_DESC;
                sortPrice = !sortPrice;
                end = getResources().getDrawable(sortPrice?
                        R.drawable.arrow_order_up:R.drawable.arrow_order_down);
                mbtnSortPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,end,null);
                break;
            case R.id.btn_sort_addtime:
                sortBy = sortAddTime ? I.SORT_BY_PRICE_ASC : I.SORT_BY_PRICE_DESC;
                sortAddTime = !sortAddTime;
                end = getResources().getDrawable(sortAddTime?
                        R.drawable.arrow_order_up:R.drawable.arrow_order_down);
                mbtnSortAddtime.setCompoundDrawablesWithIntrinsicBounds(null,null,end,null);
                break;
        }
        mNewGoodsFragment.sortBy(sortBy);
    }
}
