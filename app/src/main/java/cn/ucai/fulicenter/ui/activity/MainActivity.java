package cn.ucai.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.ui.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.ui.fragment.CartFragment;
import cn.ucai.fulicenter.ui.fragment.CategoryFragment;
import cn.ucai.fulicenter.ui.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.ui.fragment.PersonalCenterFragment;
import cn.ucai.fulicenter.ui.view.MFGT;

/**
 * Created by liuning on 2017/3/14.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

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
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mPersonalCenterFragment;
    CartFragment mCartFragment;
    RadioButton[] mRadioButtons;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initFragment();
        initRadioButton();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl, mNewGoodsFragment)
                .add(R.id.fl, mBoutiqueFragment)
                .add(R.id.fl, mCategoryFragment)
                .hide(mBoutiqueFragment).hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    private void initRadioButton() {
        mRadioButtons = new RadioButton[5];
        mRadioButtons[0] = newGood;
        mRadioButtons[1] = boutique;
        mRadioButtons[2] = category;
        mRadioButtons[3] = cart;
        mRadioButtons[4] = personalCenter;
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mCartFragment = new CartFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[3] = mCartFragment;
        mFragments[4] = mPersonalCenterFragment;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.new_good:
                index = 0;
                break;
            case R.id.boutique:
                index = 1;
                break;
            case R.id.category:
                index = 2;
                break;
            case R.id.cart:
                if (FuLiCenterApplication.getCurrentUser() == null) {
                    MFGT.gotoLogin(MainActivity.this,I.REQUEST_CODE_LOGIN_FROM_CART);
                } else {
                    index = 3;
                }

                break;
            case R.id.personal_center:
                if (FuLiCenterApplication.getCurrentUser() == null) {
                    MFGT.gotoLogin(MainActivity.this, I.REQUEST_CODE_LOGIN);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (currentIndex != index) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()) {
                fragmentTransaction.add(R.id.fl , mFragments[index]);
            }
            fragmentTransaction.show(mFragments[index]).commitAllowingStateLoss();
            currentIndex = index;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "index=" + index + ",currentIndex=" + currentIndex);
        if (currentIndex == 4) {
            if (FuLiCenterApplication.getCurrentUser() == null){
                index = 0;
            }

            setFragment();
        }
        setRadioButton();

    }

    //避免跳转到登录后底部菜单显示不正确
    private void setRadioButton() {
        for (int i = 0; i < mRadioButtons.length; i++) {
            if (i == currentIndex) {
                mRadioButtons[i].setChecked(true);
            }
//            else{
//                mRadioButtons[i].setChecked(false);
//            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            // 点击个人中心
            if (requestCode==I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if (requestCode==I.REQUEST_CODE_LOGIN_FROM_CART) {
                index = 3;
            }
            setFragment();
            setRadioButton();
        }
    }
}
