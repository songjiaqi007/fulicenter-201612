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
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.UserModel;
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
    UserModel mModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        mModel = new UserModel();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();


    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mUser = FuLiCenterApplication.getCurrentUser();
        if (mUser != null) {
            showUserInfo();
            loadCollectsCount();
        }
    }

    private void loadCollectsCount() {
        mModel.loadCollectsCount(getContext(), mUser.getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {
                        if (msg != null && msg.isSuccess()) {
                            mTvCollectCount.setText(msg.getMsg());
                        } else {
                            mTvCollectCount.setText("0");
                        }

                    }

                    @Override
                    public void onError(String error) {
                        mTvCollectCount.setText("0");
                    }
                });
    }


    private void showUserInfo() {
        mTvUserName.setText(mUser.getMuserNick());
        ImageLoader.setAvatar(mUser.getAvatar(), getContext(), mIvUserAvatar);
    }


    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void goSettings() {
        MFGT.gotoSettings(getActivity());
    }

    @OnClick(R.id.layout_center_collect)
    public void onClick() {
        MFGT.gotoCollectsList(getActivity());
    }
}

