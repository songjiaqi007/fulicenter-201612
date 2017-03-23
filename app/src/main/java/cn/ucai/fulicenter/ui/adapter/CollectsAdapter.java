package cn.ucai.fulicenter.ui.adapter;

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
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.GoodsDetailsActivity;

/**
 * Created by liuning on 2017/3/23.
 */

public class CollectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CollectBean> mList;
    boolean isMore;
    String footerText;
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
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterHolder holder = (FooterHolder) parentHolder;
            holder.tvFooter.setVisibility(View.VISIBLE);
            holder.tvFooter.setText(footerText);
            return;
        }

        CollectViewHolder holder = (CollectViewHolder) parentHolder;
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
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
                    context.startActivity(new Intent(context, GoodsDetailsActivity.class)
                            .putExtra(I.Goods.KEY_GOODS_ID, bean.getGoodsId()));

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
