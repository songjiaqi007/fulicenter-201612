package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.Dao.UserDao;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.ui.view.MFGT;

import static cn.ucai.fulicenter.R.id.backClickArea;

/**
 * Created by liuning on 2017/3/21.
 */

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;

    @BindView(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;
    @BindView(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mTvCommonTitle.setText(getString(R.string.user_profile));
        User user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            showUserInfo(user);
        }else {
            backArea();
        }
    }

    private void showUserInfo(User user) {
        mTvUserProfileName.setText(user.getMuserName());
        mTvUserProfileNick.setText(user.getMuserNick());
        ImageLoader.downloadImg(SettingActivity.this, mIvUserProfileAvatar, user.getAvatar());
    }

    @OnClick(backClickArea)
    public void backArea() {
        MFGT.finish(SettingActivity.this);
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        UserDao.getInstance(SettingActivity.this).logout();
        MFGT.gotoLogin(SettingActivity.this,I.REQUEST_CODE_LOGIN);
        finish();
    }
}
