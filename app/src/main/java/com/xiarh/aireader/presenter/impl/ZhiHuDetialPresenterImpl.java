package com.xiarh.aireader.presenter.impl;

import com.xiarh.aireader.bean.zhihu.ZhiHuItemDetial;
import com.xiarh.aireader.module.IZhiHuDetialModule;
import com.xiarh.aireader.module.impl.ZhiHuDetialModuleImpl;
import com.xiarh.aireader.presenter.IZhiHuDetialPresenter;
import com.xiarh.aireader.ui.activity.ZhiHuDetailActivity;
import com.xiarh.aireader.ui.iview.IZhiHuDetialActivity;

/**
 * Created by xiarh on 2016/9/9.
 */
public class ZhiHuDetialPresenterImpl implements IZhiHuDetialPresenter, ZhiHuDetialModuleImpl.ZhiHuDetialOnListener {
    private IZhiHuDetialModule iZhiHuDetialModule;
    private IZhiHuDetialActivity iZhiHuDetialActivity;

    public ZhiHuDetialPresenterImpl(ZhiHuDetailActivity zhiHuDetialActivity) {
        this.iZhiHuDetialModule = new ZhiHuDetialModuleImpl(this);
        this.iZhiHuDetialActivity = zhiHuDetialActivity;
    }

    @Override
    public void getZhiHuDetail(String id) {
        iZhiHuDetialModule.getZhiHuDetail(id);
    }

    @Override
    public void onUnsubsrible() {
        iZhiHuDetialModule.unsubsrible();
    }

    @Override
    public void onSuccess(ZhiHuItemDetial zhiHuItemDetial) {
        iZhiHuDetialActivity.showZhiHuDetial(zhiHuItemDetial);
    }

    @Override
    public void onFailure(Throwable e) {
        iZhiHuDetialActivity.showError(e);
    }
}
