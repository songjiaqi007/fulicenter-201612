package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.ui.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.ui.fragment.NewGoodsFragment;

/**
 * Created by liuning on 2017/3/14.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.new_good)
    RadioButton newGood;
    @BindView(R.id.cart)
    RadioButton cart;
    @BindView(R.id.category)
    RadioButton category;
    @BindView(R.id.boutique)
    RadioButton boutique;
    @BindView(R.id.personal_center)
    RadioButton personalCenter;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.fl)
    FrameLayout fl;
    Unbinder bind;
    int index = 0;
    int currentIndex = 0;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl, mNewGoodsFragment)
                .add(R.id.fl, mBoutiqueFragment)
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    private void initFragment() {
        mFragments = new Fragment[2];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.new_good:
                index = 0;
                break;
            case R.id.boutique:
                index = 1;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (currentIndex!=index) {
            getSupportFragmentManager().beginTransaction()
                    .hide(mFragments[currentIndex])
                    .show(mFragments[index])
                    .commit();
            currentIndex = index;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
