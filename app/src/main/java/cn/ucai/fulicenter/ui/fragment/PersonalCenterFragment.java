package cn.ucai.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.ui.view.MFGT;

/**
 * Created by liuning on 2017/3/21.
 */

public class PersonalCenterFragment extends Fragment {
    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;
    User mUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUser = FuLiCenterApplication.getCurrentUser();
        if (mUser == null) {
            MFGT.gotoLogin(getActivity());
        } else {
            showUserInfo();
        }
    }

    private void showUserInfo() {
        mTvUserName.setText(mUser.getMuserNick());
        ImageLoader.downloadImg(getContext(), mIvUserAvatar, mUser.getAvatar());
    }


    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void goSettings() {
        MFGT.gotoSettings(getActivity());
    }
}

