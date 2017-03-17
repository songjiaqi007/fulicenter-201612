package cn.ucai.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.net.CategroyModel;
import cn.ucai.fulicenter.model.net.ICategroyModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.ui.activity.MainActivity;


public class CategoryFragment extends Fragment {
    ICategroyModel model;
    ExpandableListView mElvCategory;

   // CategoryAdapter mAdapter;
    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    int groupCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
       // mAdapter = new CategoryAdapter(mContext,mGroupList,mChildList);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new CategroyModel();
        loadGroupData();
    }

    private void loadGroupData() {
        model.loadGroupData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void loadChildData(int parentId) {
        model.loadChildData(getContext(), parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
