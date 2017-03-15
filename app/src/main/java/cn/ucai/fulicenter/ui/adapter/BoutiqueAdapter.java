package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by liuning on 2017/3/15.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<BoutiqueBean> mList;
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

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> mList) {
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
                layout = View.inflate(context, R.layout.item_boutique, null);
                return new BotuiqueViewHolder(layout);
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

        BotuiqueViewHolder holder = (BotuiqueViewHolder) parentHolder;
        BoutiqueBean bean = mList.get(position);
        ImageLoader.downloadImg(context,holder.mivBoutiqueImg,bean.getImageurl());
        holder.mtvBoutiqueTitle.setText(bean.getTitle());
        holder.mtvBoutiqueDescription.setText(bean.getDescription());
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

    public void initNewGoodsList(ArrayList<BoutiqueBean> mList) {
        this.mList.clear();
        addNewGoodsList(mList);
    }

    public void addNewGoodsList(ArrayList<BoutiqueBean> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }



    class FooterHolder extends RecyclerView.ViewHolder {
        TextView tvFooter;

        public FooterHolder(View itemView) {
            super(itemView);
            tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);

        }
    }

     class BotuiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView mivBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mtvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mtvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mtvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout mlayoutBoutiqueItem;

         BotuiqueViewHolder(View view) {
             super(view);
             ButterKnife.bind(this, view);
        }
    }
}
