package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
    }

    public void onCheckedChange(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
