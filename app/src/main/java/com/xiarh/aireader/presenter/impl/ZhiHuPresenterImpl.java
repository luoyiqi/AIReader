package com.xiarh.aireader.presenter.impl;

import android.content.Context;

import com.xiarh.aireader.bean.zhihu.ZhiHuDaily;
import com.xiarh.aireader.module.IZhiHuModule;
import com.xiarh.aireader.module.impl.ZhiHuModuleImpl;
import com.xiarh.aireader.presenter.IZhiHuPresenter;
import com.xiarh.aireader.ui.iview.IZhiHuFragment;

/**
 * Created by xiarh on 2016/9/8.
 */
public class ZhiHuPresenterImpl implements IZhiHuPresenter, ZhiHuModuleImpl.ZhiHuOnListener {
    private IZhiHuFragment iZhiHuFragment;
    private IZhiHuModule izhiHuModule;

    public ZhiHuPresenterImpl(IZhiHuFragment zhiZhuFragment, Context mContext) {
        this.iZhiHuFragment = zhiZhuFragment;
        this.izhiHuModule = new ZhiHuModuleImpl(this, mContext);
    }

    @Override
    public void getDailyStories(String date) {
        iZhiHuFragment.showProgressDialog();
        izhiHuModule.getDailyStories(date);
    }

    @Override
    public void getLastFromCache() {
        iZhiHuFragment.showProgressDialog();
        izhiHuModule.getLastFromCache();
    }

    @Override
    public void onUnsubsrible() {
        izhiHuModule.unsubsrible();
    }

    @Override
    public void onSuccess(ZhiHuDaily zhiHuDaily) {
        iZhiHuFragment.updateList(zhiHuDaily);
        iZhiHuFragment.hideProgressDialog();
    }

    @Override
    public void onFailure(Throwable e) {
        iZhiHuFragment.hideProgressDialog();
        iZhiHuFragment.showError(e.getMessage());
    }
}
