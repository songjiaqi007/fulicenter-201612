package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import cn.ucai.fulicenter.ui.activity.BoutiqueChileActivity;


/**
 * Created by liuning on 2017/3/15.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<BoutiqueBean> mList;



    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = null;
        switch (viewType) {
            case I.TYPE_ITEM:
                layout = View.inflate(context, R.layout.item_boutique, null);
                return new BotuiqueViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {

        BotuiqueViewHolder holder = (BotuiqueViewHolder) parentHolder;
        final BoutiqueBean bean = mList.get(position);
        ImageLoader.downloadImg(context,holder.mivBoutiqueImg,bean.getImageurl());
        holder.mtvBoutiqueTitle.setText(bean.getTitle());
        holder.mtvBoutiqueDescription.setText(bean.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BoutiqueChileActivity.class)
                        .putExtra(I.NewAndBoutiqueGoods.CAT_ID, bean.getId())
                        .putExtra("title", bean.getTitle())
                );

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }



    public void initNewGoodsList(ArrayList<BoutiqueBean> mList) {
        this.mList.clear();
        addNewGoodsList(mList);
    }

    public void addNewGoodsList(ArrayList<BoutiqueBean> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
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
