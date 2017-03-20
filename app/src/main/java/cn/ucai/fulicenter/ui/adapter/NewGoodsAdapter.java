package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.ui.activity.GoodsDetailsActivity;

/**
 * Created by liuning on 2017/3/15.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;
    String footerText;
    int sortBy = I.SORT_BY_ADDTIME_DESC;

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
        isMore = true;
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
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterHolder holder = (FooterHolder) parentHolder;
            holder.tvFooter.setVisibility(View.VISIBLE);
            holder.tvFooter.setText(footerText);
            return;
        }

        GoodsViewHolder holder = (GoodsViewHolder) parentHolder;
        final NewGoodsBean bean = mList.get(position);
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvPrice.setText(bean.getCurrencyPrice());
        ImageLoader.downloadImg(context, holder.ivGoodsThumb, bean.getGoodsThumb());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mingYue", "onClick: +++++++++++");
                context.startActivity(new Intent(context, GoodsDetailsActivity.class)
                        .putExtra(I.Goods.KEY_GOODS_ID, bean.getGoodsId()));

            }
        });
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

    public void setSy(int sy) {
        this.sortBy = sy;
        sortBy();
    }

    private void sortBy() {
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean l, NewGoodsBean r) {
                int result = 0;
                switch (sortBy) {
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (l.getAddTime() - r.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (r.getAddTime() - l.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result = getPrice(l.getCurrencyPrice()) -getPrice(r.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result = getPrice(r.getCurrencyPrice()) -getPrice(l.getCurrencyPrice());
                        break;
                }
                return result;
            }
        });
        notifyDataSetChanged();
    }

    private int getPrice(String p) {
        String Pstr = p.substring(p.indexOf("￥") + 1);
        return Integer.valueOf(Pstr);
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
