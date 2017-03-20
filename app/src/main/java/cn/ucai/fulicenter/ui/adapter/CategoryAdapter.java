package cn.ucai.fulicenter.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.ui.view.MFGT;

/**
 * Created by liuning on 2017/3/16.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<CategoryGroupBean> groupList;
    ArrayList<ArrayList<CategoryChildBean>> childList;


    public CategoryAdapter(Context context) {

        mContext = context;
        this.groupList = new ArrayList<>();
        this.childList = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return groupList != null ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList != null && childList.get(groupPosition) != null
                ? childList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup parent) {
        CategroyViewHolder vh = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            vh = new CategroyViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (CategroyViewHolder) view.getTag();
        }
        CategoryGroupBean group = getGroup(groupPosition);
        vh.mtvGroupName.setText(group.getName());
        ImageLoader.downloadImg(mContext, vh.mivGroupThumb, group.getImageUrl());
        vh.mivIndicator.setImageResource(isExpand ? R.mipmap.expand_off : R.mipmap.expand_on);
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        CategoryChildViewHolder vh = null;
        if (view==null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            vh = new CategoryChildViewHolder(view);
            view.setTag(vh);
        }else {
            vh = (CategoryChildViewHolder) view.getTag();
        }
        final CategoryChildBean child = getChild(groupPosition, childPosition);
        if (child!=null) {
            vh.mtvCategoryChildName.setText(child.getName() );
            ImageLoader.downloadImg(mContext,vh.mivCategoryChildThumb,child.getImageUrl());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.gotoCategoryChild(mContext, child.getId(), getGroup(groupPosition).getName(), childList.get(groupPosition));
                }
            });
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> mGroupList, ArrayList<ArrayList<CategoryChildBean>> mChildList) {
        groupList.addAll(mGroupList);
        childList.addAll(mChildList);
        notifyDataSetChanged();
    }

     class CategroyViewHolder {
        @BindView(R.id.iv_group_thumb)
        ImageView mivGroupThumb;
        @BindView(R.id.tv_group_name)
        TextView mtvGroupName;
        @BindView(R.id.iv_indicator)
        ImageView mivIndicator;

        CategroyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

     class CategoryChildViewHolder {
        @BindView(R.id.iv_category_child_thumb)
        ImageView mivCategoryChildThumb;
        @BindView(R.id.tv_category_child_name)
        TextView mtvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout mlayoutCategoryChild;

        CategoryChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
