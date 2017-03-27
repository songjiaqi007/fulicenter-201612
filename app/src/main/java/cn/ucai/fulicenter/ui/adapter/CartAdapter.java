package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.net.CartModel;
import cn.ucai.fulicenter.model.net.ICartModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;

/**
 * Created by liuning on 2017/3/24.
 */

public class CartAdapter extends RecyclerView.Adapter {
    private static final String TAG = CartAdapter.class.getSimpleName();
    Context mContext;
    List<CartBean> mList;
    CompoundButton.OnCheckedChangeListener listener;
    ICartModel mModel = new CartModel();


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
        final CartBean bean = mList.get(position);

        CartViewHolder vh = (CartViewHolder) holder;
        vh.bind(position);
        vh.mIvCartAdd.setTag(position);
        ((CartViewHolder) holder).mTvCartCount.setText("("+bean.getCount()+")");
        ((CartViewHolder) holder).mCbCartSelected.setChecked(bean.isChecked());
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
            mTvCartCount.setText("(" + bean.getCount() + ")");
            mCbCartSelected.setChecked(bean.isChecked());
            GoodsDetailsBean goods = bean.getGoods();
            if (goods != null) {
                ImageLoader.downloadImg(mContext, mIvCartThumb, goods.getGoodsThumb());
                mTvCartGoodName.setText(goods.getGoodsName());
                mTvCartPrice.setText(goods.getCurrencyPrice());
            }
            mCbCartSelected.setTag(position);
            mCbCartSelected.setOnCheckedChangeListener(listener);

        }

        @OnClick(R.id.iv_cart_add)
        public void addCart() {
            final int position = (int) mIvCartAdd.getTag();
            CartBean cart = mList.get(position);
            mModel.catrAction(mContext, I.ACTION_CART_UPDATA, String.valueOf(cart.getId()), null, null,
                    cart.getCount() + 1, new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                mList.get(position).setCount(mList.get(position).getCount() + 1);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                L.e(TAG,"");
                                mTvCartCount.setText("(" + (mList.get(position).getCount()) + ")");
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
        }

        @OnClick(R.id.iv_cart_del)
        public void delCart() {
            final int position = (int) mIvCartAdd.getTag();
            CartBean cart = mList.get(position);
            if (cart.getCount()>1) {

            mModel.catrAction(mContext, I.ACTION_CART_UPDATA, String.valueOf(cart.getId()), null, null,
                    cart.getCount() - 1, new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            Log.d(TAG, "onSuccess: " + result.toString());
                            if (result != null && result.isSuccess()) {
                                mList.get(position).setCount(mList.get(position).getCount() - 1);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                mTvCartCount.setText("(" + (mList.get(position).getCount()) + ")");
                            }
                        }

                        @Override
                        public void onError(String error) {
                            Log.d(TAG, "onError: " + error);
                        }
                    });
            }else {
                mModel.catrAction(mContext, I.ACTION_CART_DEL, String.valueOf(cart.getId()), null,
                        null, 0, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null && result.isSuccess()) {
                            mList.remove(position);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

            }
        }


    }

}


