package cn.ucai.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.net.CategroyModel;
import cn.ucai.fulicenter.model.net.ICategroyModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.ui.activity.MainActivity;
import cn.ucai.fulicenter.ui.adapter.CategoryAdapter;


public class CategoryFragment extends Fragment {
    ICategroyModel model;

    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    CategoryAdapter mAdapter;
    MainActivity mContext;
    ArrayList<ArrayList<CategoryChildBean>> mChildList = new ArrayList<>();
    ArrayList<CategoryGroupBean> mGroupList = new ArrayList<>();
    int loadIndex = 0;
    @BindView(R.id.layout_tips)
    LinearLayout layoutTips;
    View loadView;
    View loadFail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        loadView = LayoutInflater.from(getContext()).inflate(R.layout.loading, layoutTips, false);
        loadFail = LayoutInflater.from(getContext()).inflate(R.layout.load_fail, layoutTips, false);
        layoutTips.addView(loadView);
        layoutTips.addView(loadFail);
        loadView.setVisibility(View.VISIBLE);
        loadFail.setVisibility(View.GONE);
        showDialog(true,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new CategroyModel();
        mAdapter = new CategoryAdapter(getContext());
        elvCategory.setAdapter(mAdapter);
        elvCategory.setGroupIndicator(null);
        loadGroupData();
    }

    private void loadGroupData() {
        model.loadGroupData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                Log.i("main", "fweerfe");
                if (result != null) {
                    ArrayList<CategoryGroupBean> list = ResultUtils.array2List(result);
                    mGroupList.clear();
                    mGroupList.addAll(list);

                    for (int i = 0; i < list.size(); i++) {
                        mChildList.add(i, null);
                        loadChildData(list.get(i).getId(), i);
                    }
                }
            }

            @Override
            public void onError(String error) {
                showDialog(false,false);
            }
        });
    }

    private void loadChildData(int parentId, final int indext) {
        model.loadChildData(getContext(), parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                loadIndex++;
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ResultUtils.array2List(result);
                    Log.d("mingYue", "onSuccess: " + indext + ", " + mChildList.size());
                    mChildList.set(indext, list);
                }
                if (loadIndex == mGroupList.size()) {
                    mAdapter.initData(mGroupList, mChildList);
                    showDialog(false,true);
                }

            }

            @Override
            public void onError(String error) {
                loadIndex++;
                showDialog(false,false);
                }
        });
    }

    @OnClick(R.id.layout_tips)
    public void loadAgain() {
        if (loadFail.getVisibility()==View.VISIBLE) {
            loadGroupData();
            showDialog(true,false);
        }
    }

    private void showDialog(boolean dialog, boolean success) {
        loadView.setVisibility(dialog?View.VISIBLE:View.GONE);
        if (dialog) {
            loadFail.setVisibility(View.GONE);
            layoutTips.setVisibility(View.VISIBLE);
        }else{
            loadFail.setVisibility(success?View.GONE:View.VISIBLE);
            layoutTips.setVisibility(success?View.GONE:View.VISIBLE);
        }
    }
}
