package cn.ucai.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import cn.ucai.fulicenter.model.utils.MD5;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.model.utils.SharePrefrenceUtils;
import cn.ucai.fulicenter.ui.view.MFGT;

/**
 * Created by liuning on 2017/3/20.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    String username, password;
    IUserModel mModel;
    ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mModel = new UserModel();
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backClickArea, R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backClickArea:
                MFGT.finish(LoginActivity.this);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                MFGT.gotoRegister(LoginActivity.this);
                break;
        }
    }

    private void login() {
        if (checkInput()) {
            showDialog();
            mModel.login(LoginActivity.this, username, MD5.getMessageDigest(password),
                    new OnCompleteListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    User user = (User) result.getRetData();
                                    if (user != null) {
                                        loginSuccess(user);
                                    }
                                } else {
                                    if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                        CommonUtils.showShortToast(R.string.login_fail_unknow_user);
                                    }
                                    if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                        CommonUtils.showShortToast(R.string.login_fail_error_password);
                                    }
                                }
                            }
                            pd.dismiss();
                        }


                        @Override
                        public void onError(String error) {
                            CommonUtils.showShortToast(R.string.login_fail);
                            pd.dismiss();
                        }
                    });
        }
    }

    private void loginSuccess(final User user) {
        FuLiCenterApplication.setCurrentUser(user);
        SharePrefrenceUtils.getInstance().setUserName(user.getMuserName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = UserDao.getInstance(LoginActivity.this).savaUser(user);
                Log.d("mingYue", "run: " + isSuccess);
                if (isSuccess) {
                    MFGT.finish(LoginActivity.this);
                }
            }
        }).start();
        setResult(RESULT_OK);

    }

    private void showDialog() {
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage(getString(R.string.registering));
        pd.show();
    }


    private boolean checkInput() {
        username = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();
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
        if (TextUtils.isEmpty(password)) {
            mPassword.requestFocus();
            mPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        return true;
    }


}

