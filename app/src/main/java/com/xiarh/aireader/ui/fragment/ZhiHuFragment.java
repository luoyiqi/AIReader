package com.xiarh.aireader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.xiarh.aireader.R;
import com.xiarh.aireader.bean.zhihu.ZhiHuDaily;
import com.xiarh.aireader.bean.zhihu.ZhiHuStory;
import com.xiarh.aireader.presenter.IZhiHuPresenter;
import com.xiarh.aireader.presenter.impl.ZhiHuPresenterImpl;
import com.xiarh.aireader.ui.adapter.ZhiHuFragmentAdapter;
import com.xiarh.aireader.ui.iview.IZhiHuFragment;
import com.xiarh.aireader.utils.NetWorkUtil;
import com.xiarh.aireader.utils.SharePreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xiarh on 2016/9/6.
 */
public class ZhiHuFragment extends BaseFragment implements IZhiHuFragment, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private IZhiHuPresenter mZhiHuPresenter;
    private String currentDate;
    private LinearLayoutManager mLinearLayoutManager;
    private ZhiHuFragmentAdapter mZhiHuAdapter;
    private ArrayList<ZhiHuStory> zhiHuStories = new ArrayList<>();
    private boolean loading = false;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mZhiHuPresenter = new ZhiHuPresenterImpl(this, getActivity());
        swipeRefreshLayout.setOnRefreshListener(this);
        setSwipeRefreshLayoutColor(swipeRefreshLayout);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mZhiHuAdapter = new ZhiHuFragmentAdapter(zhiHuStories, getActivity());
        recyclerView.setAdapter(mZhiHuAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //向下滚动
                {
                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        onLoadMore();
                    }
                }
            }
        });
        mZhiHuPresenter.getLastFromCache();
        if (SharePreferenceUtil.isRefreshOnlyWifi(getActivity())) {
            if (NetWorkUtil.isWifiConnected(getActivity())) {
                onRefresh();
            }
        } else {
            onRefresh();
        }
    }

    private void onLoadMore() {
        mZhiHuPresenter.getDailyStories(currentDate);
    }

    @Override
    public void onRefresh() {
        currentDate = getDate();
        zhiHuStories.clear();
        mZhiHuAdapter.notifyDataSetChanged();
        mZhiHuPresenter.getDailyStories(currentDate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mZhiHuPresenter.onUnsubsrible();
    }

    @Override
    public void updateList(ZhiHuDaily zhiHuDaily) {
        currentDate = zhiHuDaily.getDate();
        zhiHuStories.addAll(zhiHuDaily.getStories());
        mZhiHuAdapter.notifyDataSetChanged();
        //若未填满屏幕
        if (!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            onLoadMore();
        }
    }

    @Override
    public void showProgressDialog() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        if (progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            loading = false;
        }
    }

    @Override
    public void showError(String error) {
        if (recyclerView != null) {
            Snackbar.make(recyclerView, error, Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }
    }

    /**
     * 获得下一天的时间
     *
     * @return
     */
    public String getDate() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, +1);//负数把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(date);
        return dateString;
    }
}
