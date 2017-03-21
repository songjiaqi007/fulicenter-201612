package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.model.Dao.UserDao;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.SharePrefrenceUtils;
import cn.ucai.fulicenter.ui.view.MFGT;

/**
 * Created by liuning on 2017/3/14.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    int time = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userName = SharePrefrenceUtils.getInstance().getUserName();
                if (userName!=null) {
                    User user = UserDao.getInstance(SplashActivity.this).getUser(userName);
                    L.e(TAG,"user="+user);
                    FuLiCenterApplication.setCurrentUser(user);
                }
                MFGT.gotoMain(SplashActivity.this);
                SplashActivity.this.finish();
            }
        }, time);
    }
}
