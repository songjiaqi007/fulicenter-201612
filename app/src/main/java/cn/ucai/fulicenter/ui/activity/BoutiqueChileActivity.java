package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.ui.fragment.NewGoodsFragment;

/**
 * Created by liuning on 2017/3/16.
 */

public class BoutiqueChileActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.textTitle)
    TextView textTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_botique_child);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        textTitle.setText(title);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();
    }

    @Override
    public void onClick(View v) {
                finish();
        }
}

