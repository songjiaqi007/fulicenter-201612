package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by liuning on 2017/3/15.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;
    String footerText;

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public NewGoodsAdapter(Context context, ArrayList<NewGoodsBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                layout = View.inflate(context, R.layout.item_footer, null);
                return new FooterHolder(layout);
            case I.TYPE_ITEM:
                layout = View.inflate(context, R.layout.item_goods, null);
                return new GoodsViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER) {
            FooterHolder holder = (FooterHolder) parentHolder;
            holder.tvFooter.setVisibility(View.VISIBLE);
            holder.tvFooter.setText(footerText);
            return;
        }

        GoodsViewHolder holder = (GoodsViewHolder) parentHolder;
        NewGoodsBean bean = mList.get(position);
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvPrice.setText(bean.getCurrencyPrice());
        ImageLoader.downloadImg(context, holder.ivGoodsThumb, bean.getGoodsThumb());

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initNewGoodsList(ArrayList<NewGoodsBean> mList) {
        this.mList.clear();
        addNewGoodsList(mList);
    }

    public void addNewGoodsList(ArrayList<NewGoodsBean> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
