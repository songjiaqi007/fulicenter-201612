package cn.ucai.fulicenter.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;

public class FooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvFooter)
    public TextView mTvFooter;

    public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setmTvFooter(String footerString) {
        mTvFooter.setText(footerString);

    }

    public void setmTvFooter(int footerInt) {
        mTvFooter.setText(footerInt);
    }
}