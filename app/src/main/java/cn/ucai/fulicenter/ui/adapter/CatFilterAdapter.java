package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.ui.view.MFGT;

public class CatFilterAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CategoryChildBean> mlist;
    String groupName;

    public CatFilterAdapter(Context mContext, ArrayList<CategoryChildBean> mlist, String groupName) {
        this.mContext = mContext;
        this.mlist = mlist;
        this.groupName = groupName;
    }

    @Override
    public int getCount() {
        return mlist != null ? mlist.size() : 0;
    }

    @Override
    public CategoryChildBean getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CatFilterViewHolder vh = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_cat_filter, null);
            vh = new CatFilterViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (CatFilterViewHolder) view.getTag();
        }
        vh.bind(position);
        View.inflate(mContext, R.layout.item_cat_filter, null);
        return view;
    }

    class CatFilterViewHolder {
        @BindView(R.id.ivCategoryChildThumb)
        ImageView mIvCategoryChildThumb;
        @BindView(R.id.tvCategoryChildName)
        TextView mTvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout mLayoutCategoryChild;

        CatFilterViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(int childPostion) {
            final CategoryChildBean child = mlist.get(childPostion);
            mTvCategoryChildName.setText(child.getName());
            ImageLoader.downloadImg(mContext, mIvCategoryChildThumb, child.getImageUrl());
            mLayoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.gotoCategoryChild(mContext, child.getId(), groupName, mlist);
                }
            });
        }
    }
}