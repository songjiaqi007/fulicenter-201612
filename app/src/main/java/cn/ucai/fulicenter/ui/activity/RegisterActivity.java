package cn.ucai.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IUserModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.UserModel;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.MD5;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.ui.view.MFGT;

/**
 * Created by liuning on 2017/3/20.
 */

public class RegisterActivity extends AppCompatActivity {
    String username;
    String nickname;
    String password;
    RegisterActivity mContext;
    IUserModel mModel;
    ProgressDialog pd;


    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.nick)
    EditText mNick;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        mContext = this;
        mModel = new UserModel();
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        mTvCommonTitle.setText(R.string.register_title1);
    }


    private boolean checkInput() {
        username = mUsername.getText().toString().trim();
        nickname = mNick.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        String confirm = mConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mUsername.requestFocus();
            mUsername.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            mUsername.requestFocus();
            mUsername.setError(getString(R.string.illegal_user_name));
            return false;
        }
        if (TextUtils.isEmpty(nickname)) {
            mNick.requestFocus();
            mNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.requestFocus();
            mPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(confirm)) {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getString(R.string.confirm_password_connot_be_empty));
            return false;
        }
        if (!password.equals(confirm)) {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getString(R.string.two_input_password));
            return false;
        }
        return true;
    }


    private void register() {
        if (checkInput()) {
            showDialog();
            mModel.register(RegisterActivity.this, username, nickname,
                    MD5.getMessageDigest(password),
                    new OnCompleteListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    registerSuccess();
                                } else {
                                    if (result.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS) {
                                        CommonUtils.showShortToast(R.string.register_fail_exists);
                                    } else {
                                        CommonUtils.showShortToast(R.string.register_fail);
                                    }
                                }
                            }
                            pd.dismiss();
                        }

                        @Override
                        public void onError(String error) {
                            pd.dismiss();
                            CommonUtils.showShortToast(R.string.register_fail);
                        }
                    });
        }
    }
    private void showDialog() {
        pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage(getString(R.string.registering));
        pd.show();
    }

    private void registerSuccess(){
        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
        CommonUtils.showShortToast(R.string.register_success );
        MFGT.finish(RegisterActivity.this);
    }


    @OnClick({R.id.backClickArea, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backClickArea:
                MFGT.finish(RegisterActivity.this);
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }
}
