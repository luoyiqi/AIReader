package com.xiarh.aireader.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xiarh.aireader.R;
import com.xiarh.aireader.bean.zhihu.ZhiHuItemDetial;
import com.xiarh.aireader.presenter.IZhiHuDetialPresenter;
import com.xiarh.aireader.presenter.impl.ZhiHuDetialPresenterImpl;
import com.xiarh.aireader.ui.iview.IZhiHuDetialActivity;
import com.xiarh.aireader.utils.ShareUtils;
import com.xiarh.aireader.utils.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiarh on 2016/9/6.
 */
public class ZhiHuDetailActivity extends BaseActivity implements IZhiHuDetialActivity {
    @BindView(R.id.img_backdrop)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorLayout;

    private IZhiHuDetialPresenter mZhiHuDetialPresenter;
    private String id;
    private String title;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);
        initData();
        initToolbar();
        init();
    }

    private void initData() {
        id = getIntent().getIntExtra("id", 0) + "";
        title = getIntent().getStringExtra("title");
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            collapsingToolbarLayout.setTitle(title);
        }
    }

    private void init() {
        mZhiHuDetialPresenter = new ZhiHuDetialPresenterImpl(this);
        WebSettings settings = webView.getSettings();
        // 支持javascript
        settings.setJavaScriptEnabled(true);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        mZhiHuDetialPresenter.getZhiHuDetail(id);
    }

    @Override
    public void showZhiHuDetial(ZhiHuItemDetial zhiHuItemDetial) {
        Picasso.with(this)
                .load(zhiHuItemDetial.getImage())
                .fit()
                .tag(this)
                .into(imageView);
        String data = WebUtil.buildHtmlWithCss(zhiHuItemDetial.getBody(), zhiHuItemDetial.getCss(), false);
        webView.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
        url = zhiHuItemDetial.getShare_url();
    }

    @Override
    public void showError(Throwable e) {
        if (coordinatorLayout != null) {
            Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.more_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_browser:
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.menu_share:
                ShareUtils.shareMsg(ZhiHuDetailActivity.this, null, title, url, null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
