package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by liuning on 2017/3/24.
 */

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CartBean> mList;
    CompoundButton.OnCheckedChangeListener listener;



    public CartAdapter(Context context, List<CartBean> list) {
        mContext = context;
        mList = list;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartViewHolder vh = (CartViewHolder) holder;
        final CartBean bean = mList.get(position);
        vh.bind(position);
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


     class CartViewHolder extends ViewHolder {
        @BindView(R.id.cb_cart_selected)
        CheckBox mCbCartSelected;
        @BindView(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.iv_cart_add)
        ImageView mIvCartAdd;
        @BindView(R.id.tv_cart_count)
        TextView mTvCartCount;
        @BindView(R.id.iv_cart_del)
        ImageView mIvCartDel;
        @BindView(R.id.tv_cart_price)
        TextView mTvCartPrice;

         CartViewHolder(View view) {
             super(view);
             ButterKnife.bind(this, view);
        }


         public void bind(int position) {
             CartBean bean = mList.get(position);
             mTvCartCount.setText("("+bean.getCount()+")");
             mCbCartSelected.setChecked(bean.isChecked());
             GoodsDetailsBean goods = bean.getGoods();
             if (goods!=null){
                 ImageLoader.downloadImg(mContext,mIvCartThumb,goods.getGoodsThumb());
                 mTvCartGoodName.setText(goods.getGoodsName());
                 mTvCartPrice.setText(goods.getCurrencyPrice());
             }
             mCbCartSelected.setTag(position);
             mCbCartSelected.setOnCheckedChangeListener(listener);
         }
     }
}
