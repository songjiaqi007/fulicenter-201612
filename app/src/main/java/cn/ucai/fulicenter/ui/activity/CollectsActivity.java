package cn.ucai.fulicenter.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IUserModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.UserModel;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.ui.adapter.CollectsAdapter;
import cn.ucai.fulicenter.ui.view.MFGT;
import cn.ucai.fulicenter.ui.view.SpaceItemDecoration;

import static cn.ucai.fulicenter.application.I.ACTION_DOWNLOAD;


/**
 * Created by liuning on 2017/3/23.
 */

public class CollectsActivity extends AppCompatActivity {
    private static final String TAG = CollectsActivity.class.getSimpleName();

    IUserModel mModel;
    int pageId = 1;
    GridLayoutManager gm;
    ArrayList<CollectBean> mList = new ArrayList<>();

    CollectsAdapter mAdapter;
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newgoods);
        ButterKnife.bind(this);
        mModel = new UserModel();
        initData();
        initView();
        downloadCollect(pageId, ACTION_DOWNLOAD);
        setListener();
    }

    private void downloadCollect(int pageId, final int action) {
        User user = FuLiCenterApplication.getCurrentUser();
        mModel.loadCollects(CollectsActivity.this, user.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT,
                new OnCompleteListener<CollectBean[]>() {
                    @Override
                    public void onSuccess(CollectBean[] result) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mTvRefresh.setVisibility(View.GONE);
                        mAdapter.setMore(true);
                        if (result != null && result.length > 0) {
                            ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                            if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                                mAdapter.initData(list);
                                mAdapter.setFooterText("没有更多数据加载");
                            } else {
                                mAdapter.addData(list);
                            }
                            if (list.size() < I.PAGE_SIZE_DEFAULT) {
                                mAdapter.setMore(false);
                            }
                        } else {
                            mAdapter.setMore(false);
                        }
                    }


                    @Override
                    public void onError(String error) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mTvRefresh.setVisibility(View.GONE);
                        mAdapter.setMore(false);
                        CommonUtils.showShortToast(error);
                        L.e("error:" + error);
                    }
                });
    }


    private void setListener() {
        setPullDownListener();
        setPullUpListener();
        IntentFilter filter = new IntentFilter("update_collect");
        registerReceiver(mReceiver, filter);
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ImageLoader.release();
                mSwipeRefreshLayout.setRefreshing(true);
                pageId = 1;
                downloadCollect(pageId, I.ACTION_PULL_DOWN);
            }
        });
    }

    private void setPullUpListener() {
        mRvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = gm.findLastVisibleItemPosition();
                if (lastPosition == mAdapter.getItemCount() - 1 && RecyclerView.SCROLL_STATE_IDLE == newState && mAdapter.isMore()) {
                    pageId++;
                }
            }
        });
    }


    private void initView() {
        gm = new GridLayoutManager(CollectsActivity.this, I.COLUM_NUM);
        gm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mList.size()) {
                    return I.COLUM_NUM;
                }
                return 1;
            }
        });

        gm = new GridLayoutManager(CollectsActivity.this, I.COLUM_NUM);
        mRvGoods.setLayoutManager(gm);
        mRvGoods.setHasFixedSize(true);
        mAdapter = new CollectsAdapter(CollectsActivity.this, mList);
        mRvGoods.addItemDecoration(new SpaceItemDecoration(12));
        mRvGoods.setAdapter(mAdapter);
    }

    private void initData() {
        User user = FuLiCenterApplication.getCurrentUser();
        if (user == null) {
            finish();
        }
        downloadCollect(pageId, ACTION_DOWNLOAD);
    }


    updateCollectReceiver mReceiver;


    class updateCollectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int goodsId = intent.getIntExtra(I.Collect.GOODS_ID, 0);
            if (goodsId != 0) {
                CollectBean bean = new CollectBean(goodsId);
                bean.setGoodsId(goodsId);
                mAdapter.remove(bean);
                L.e("delete..." + goodsId);
            }
        }
    }
        @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    @OnClick(R.id.backClickArea)
    public void backArea() {
        MFGT.finish(CollectsActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==I.REQUEST_CODE_COLLECT) {
            boolean isCollected = data.getBooleanExtra(I.GoodsDetails.KEY_IS_COLLECTED, true);
            int goodsId = data.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
            if (!isCollected) {
                // update adapter
                mList.remove(new CollectBean(goodsId));
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}



