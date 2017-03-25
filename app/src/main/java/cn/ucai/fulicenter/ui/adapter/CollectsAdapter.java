package cn.ucai.fulicenter.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.net.GoodsModel;
import cn.ucai.fulicenter.model.net.IGoodsModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.ui.activity.GoodsDetailsActivity;

/**
 * Created by liuning on 2017/3/23.
 */

public class CollectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CollectBean> mList;
    boolean isMore;
    String footerText;
    IGoodsModel mModel = new GoodsModel();
    @BindView(R.id.ivGoodsThumb)
    ImageView mIvGoodsThumb;
    @BindView(R.id.tvGoodsName)
    TextView mTvGoodsName;
    @BindView(R.id.tvGoodsPrice)
    TextView mTvGoodsPrice;
    @BindView(R.id.layout_goods)
    RelativeLayout mLayoutGoods;

    public CollectsAdapter(Context context, ArrayList<CollectBean> list) {
        this.context = context;
        mList = list;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                layout = View.inflate(context, R.layout.item_footer, null);
                return new FooterHolder(layout);
            case I.TYPE_ITEM:
                layout = View.inflate(context, R.layout.item_collect, null);
                return new CollectViewHolder(layout);
        }
        return null;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterHolder holder = (FooterHolder) parentHolder;
            holder.tvFooter.setVisibility(View.VISIBLE);
            holder.tvFooter.setText(footerText);
            return;
        }

        CollectViewHolder holder = (CollectViewHolder) parentHolder;
        holder.itemView.setTag(mList.get(position));
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() + 1;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(CollectBean bean) {
        mList.remove(bean);
        notifyDataSetChanged();
    }

    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;


        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            final CollectBean bean = mList.get(position);
            tvGoodsName.setText(bean.getGoodsName());
            ImageLoader.downloadImg(context, ivGoodsThumb, bean.getGoodsThumb());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mingYue", "onClick: +++++++++++");
                    ((Activity) context).startActivityForResult(new Intent(context, GoodsDetailsActivity.class)
                            .putExtra(I.Goods.KEY_GOODS_ID, bean.getGoodsId()), I.REQUEST_CODE_COLLECT);

                }
            });
        }

        @OnClick(R.id.iv_collect_del)
        public void deleteCollect() {
            final CollectBean goods = (CollectBean) itemView.getTag();
            String username = FuLiCenterApplication.getCurrentUser().getMuserName();
            mModel.deleteCollect(context, username, goods.getGoodsId(), new OnCompleteListener<CollectBean>() {
                @Override
                public void onSuccess(CollectBean result) {
                    if (result != null) {
                        mList.remove(goods);
                        notifyDataSetChanged();
                    } else {
                        CommonUtils.showLongToast(result != null ?
                                context.getResources().getString(R.string.delete_collect_success) :
                                context.getResources().getString(R.string.delete_collect_fail));

                    }
                }

                @Override
                public void onError(String error) {
                    L.e("error=" + error);
                    CommonUtils.showLongToast(context.getResources().getString(R.string.delete_collect_fail));
                }
            });
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        TextView tvFooter;

        public FooterHolder(View itemView) {
            super(itemView);
            tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);

        }
    }


}
