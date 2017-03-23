package cn.ucai.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.Dao.UserDao;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IUserModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.UserModel;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.FileUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.ui.view.MFGT;

import static cn.ucai.fulicenter.R.id.backClickArea;

/**
 * Created by liuning on 2017/3/21.
 */

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = SettingActivity.class.getSimpleName();
    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;

    @BindView(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;

    @BindView(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;

    @BindView(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;

    SettingActivity mContext;
    OnSetAvatarListener mOnSetAvatarListener;

    User user ;
    IUserModel mModel;
    String avatarName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        mContext = this;
        mModel = new UserModel();
        initData();
    }


    private void initData() {
        mTvCommonTitle.setText(getString(R.string.user_profile));
        user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            showUserInfo();
        } else {
            backArea();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void showUserInfo() {
        mTvUserProfileName.setText(user.getMuserName());
        mTvUserProfileNick.setText(user.getMuserNick());
        ImageLoader.setAvatar( user.getAvatar(),SettingActivity.this, mIvUserProfileAvatar);
    }

    @OnClick(backClickArea)
    public void backArea() {
        MFGT.finish(SettingActivity.this);
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        UserDao.getInstance(SettingActivity.this).logout();
        MFGT.gotoLogin(SettingActivity.this, I.REQUEST_CODE_LOGIN);
        finish();
    }

    @OnClick(R.id.layout_user_profile_username)
    public void usernameOnClick() {
        CommonUtils.showShortToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.layout_user_profile_nickname)
    public void updateNick() {
        MFGT.gotoUpdateNick(SettingActivity.this);
    }

    @OnClick(R.id.layout_user_profile_avatar)
    public void avatarOnClick() {
       avatarName = getAvatarName();
        mOnSetAvatarListener = new OnSetAvatarListener(SettingActivity.this,
                R.id.layout_user_profile_avatar,avatarName , I.AVATAR_TYPE_USER_PATH);
    }

    private String getAvatarName() {
        avatarName = user.getMuserName() + System.currentTimeMillis();
        L.e(TAG, "avatarName" + avatarName);
        return avatarName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG, "requestCode=" + requestCode + ",resultCode=" + resultCode + ",data=" + data);
        mOnSetAvatarListener.setAvatar(requestCode, data, mIvUserProfileAvatar);
        if (requestCode == I.REQUEST_CODE_NICK) {
        } else if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        }
    }

    private void uploadAvatar() {
//        File file = new File(OnSetAvatarListener.getAvatarPath(mContext,
//                user.getMavatarPath() + "/" + user.getMuserName()
//                        + I.AVATAR_SUFFIX_JPG));
        File file = FileUtils.getAvatarPath(SettingActivity.this,I.AVATAR_TYPE_USER_PATH, avatarName + ".jpg");
        L.e("file=" + file.exists());
        L.e("mingYue=" + file.getAbsolutePath());
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        mModel.updateAvatar(mContext, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s=" + s);
                Result result = ResultUtils.getResultFromJson(s, User.class);
                L.e("result=" + result);
                if (result == null) {
                    CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                } else {
                    User u = (User) result.getRetData();
                    if (result.isRetMsg()) {
                        FuLiCenterApplication.setCurrentUser(u);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u), mContext, mIvUserProfileAvatar);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    } else {
                        CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                L.e("error=" + error);
            }
        });
    }
}
