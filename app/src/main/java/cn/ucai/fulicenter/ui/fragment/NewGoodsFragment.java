package cn.ucai.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.net.INewGoodsModel;
import cn.ucai.fulicenter.model.net.NewGoodsModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.ui.activity.MainActivity;
import cn.ucai.fulicenter.ui.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

/**
 * Created by liuning on 2017/3/15.
 */

public class NewGoodsFragment extends Fragment {
    private static final String TAG = NewGoodsFragment.class.getSimpleName();
    static final int ACTION_DOWNLOAD = 1;
    static final int ACTION_PULL_DOWN = 2;
    static final int ACTION_PULL_UP = 3;
    @BindView(R.id.rvGoods)
    RecyclerView mRvGoods;
    INewGoodsModel mModel = new NewGoodsModel();
    Unbinder bind;
    ArrayList<NewGoodsBean> mList = new ArrayList<>();
    GridLayoutManager mLayoutManager;
    NewGoodsAdapter mAdapter;
    INewGoodsModel model = new NewGoodsModel();
    SwipeRefreshLayout mSwipeRefreshLayout;
    MainActivity mainActivity;
    int pageId = 1;
    GridLayoutManager gm;
    @BindView(R.id.tvRefreshHint)
    TextView mtvRefreshHint;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        bind = ButterKnife.bind(this, layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl);
        return layout;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new NewGoodsModel();
        initView();
        initData();
        setListener();
        pageId = 1;
        downloadNewGoods(pageId, ACTION_DOWNLOAD);
    }

    private void downloadNewGoods(int pageId, final int action) {
        mModel.loadData(mainActivity, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                Log.d(TAG, "onSuccess: " + action);
                mAdapter.setMore(result != null && result.length > 0);
                if (!mAdapter.isMore()) {
                    if (action == ACTION_PULL_UP) {
                    mAdapter.setFooterText("没有更多数据加载");
                    }
                    return;
                }
                mList = ResultUtils.array2List(result);
                switch (action) {
                    case ACTION_DOWNLOAD:
                        mAdapter.initNewGoodsList(mList);
                        mAdapter.setFooterText("加载更多数据");
                        break;
                    case ACTION_PULL_DOWN:
                        mAdapter.initNewGoodsList(mList);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mtvRefreshHint.setVisibility(View.GONE);
                        mAdapter.setFooterText("加载更多数据");
                        break;
                    case ACTION_PULL_UP:
                        mAdapter.addNewGoodsList(mList);
                        mAdapter.setFooterText("加载更多数据");
                        break;
                }

            }

            @Override
            public void onError(String error) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, "onError: " + error);
                Toast.makeText(mainActivity, error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ImageLoader.release();
                mSwipeRefreshLayout.setRefreshing(true);
                mtvRefreshHint.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadNewGoods(pageId, ACTION_PULL_DOWN);
            }
        });
    }

    private void setPullUpListener() {
        mRvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = mLayoutManager.findLastVisibleItemPosition();
                if (lastPosition == mAdapter.getItemCount() - 1 && RecyclerView.SCROLL_STATE_IDLE == newState && mAdapter.isMore()) {
                    pageId++;
                    downloadNewGoods(pageId, ACTION_PULL_UP);
                }
            }
        });
    }


    private void initView() {
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        mRvGoods.setLayoutManager(gm);
        mRvGoods.setHasFixedSize(true);
        mainActivity = (MainActivity) getActivity();
        mLayoutManager = new GridLayoutManager(getActivity(), I.COLUM_NUM);
        mRvGoods.setLayoutManager(mLayoutManager);
        mRvGoods.setHasFixedSize(true);
        mAdapter = new NewGoodsAdapter(getContext(), mList);
        mRvGoods.setAdapter(mAdapter);
        mRvGoods.addItemDecoration(new SpaceItemDecoration(12));
    }

    private void initData() {
        model.loadData(getContext(), pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                L.e(TAG, "initData,result = " + result);
                if (result != null && result.length > 0) {
                    L.e(TAG, "initData,result.length = " + result.length);
                }
                ArrayList<NewGoodsBean> list = ResultUtils.array2List(result);
                mList.clear();
                mList.addAll(list);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "initData,error = " + error);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
    }
}
