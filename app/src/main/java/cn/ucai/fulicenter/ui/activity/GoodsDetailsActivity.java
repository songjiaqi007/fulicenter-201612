package cn.ucai.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.AlbumsBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.net.GoodsModel;
import cn.ucai.fulicenter.model.net.IGoodsModel;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.ui.view.FlowIndicator;
import cn.ucai.fulicenter.ui.view.MFGT;
import cn.ucai.fulicenter.ui.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {
    int goodsId = 0;
    IGoodsModel modle;
    GoodsDetailsBean bean;
    @BindView(R.id.backClickArea)
    LinearLayout mbackClickArea;
    @BindView(R.id.tv_common_title)
    TextView mtvCommonTitle;
    @BindView(R.id.iv_good_share)
    ImageView mivGoodShare;
    @BindView(R.id.iv_good_collect)
    ImageView mivGoodCollect;
    @BindView(R.id.iv_good_cart)
    ImageView mivGoodCart;
    @BindView(R.id.tv_cart_count)
    TextView mtvCartCount;
    @BindView(R.id.layout_title)
    RelativeLayout mlayoutTitle;
    @BindView(R.id.tv_good_name_english)
    TextView mtvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView mtvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView mtvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView mtvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView msalv;
    @BindView(R.id.indicator)
    FlowIndicator mindicator;
    @BindView(R.id.layout_image)
    RelativeLayout mlayoutImage;
    @BindView(R.id.wv_good_brief)
    WebView mwvGoodBrief;
    @BindView(R.id.layout_banner)
    RelativeLayout mlayoutBanner;
    @BindView(R.id.activity_goods_detail)
    RelativeLayout mactivityGoodsDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finish(GoodsDetailsActivity.this);
            return;
        }
        modle = new GoodsModel();
        initData();

    }

    private void initData() {
        modle.loadData(GoodsDetailsActivity.this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    bean = result;
                    showDetails(result);
                }

            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showDetails(GoodsDetailsBean result) {
        mtvGoodNameEnglish.setText(result.getGoodsEnglishName());
        mtvGoodName.setText(result.getGoodsName());
        mtvGoodPriceShop.setText(result.getShopPrice());
        mtvGoodPriceCurrent.setText(result.getCurrencyPrice());
        msalv.startPlayLoop(mindicator,getAblumUrl(),getAblumCount());
        mwvGoodBrief.loadDataWithBaseURL(null,bean.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private int getAblumCount() {
        if (bean.getProperties()!=null&& bean.getProperties().length>0) {
            return bean.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAblumUrl() {
        if (bean.getProperties()!=null&& bean.getProperties().length>0) {
            AlbumsBean[] albums = bean.getProperties()[0].getAlbums();
            if (albums!=null&& albums.length>0) {
                String[] urls = new String[albums.length];
                for (int i=0;i<albums.length;i++) {
                    urls[i] = albums[0].getImgUrl();
                }
                return urls;
            }
        }
        return  null;
    }


    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(GoodsDetailsActivity.this);
    }


}
