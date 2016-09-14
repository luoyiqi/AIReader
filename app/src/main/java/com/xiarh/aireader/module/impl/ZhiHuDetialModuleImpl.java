package com.xiarh.aireader.module.impl;

import com.xiarh.aireader.api.ApiManage;
import com.xiarh.aireader.bean.zhihu.ZhiHuItemDetial;
import com.xiarh.aireader.module.IZhiHuDetialModule;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiarh on 2016/9/9.
 */
public class ZhiHuDetialModuleImpl extends BaseModuleImpl implements IZhiHuDetialModule {
    private ZhiHuDetialOnListener mZhiHuDetialOnListener;

    public ZhiHuDetialModuleImpl(ZhiHuDetialOnListener zhiHuDetialOnListener) {
        this.mZhiHuDetialOnListener = zhiHuDetialOnListener;
    }

    @Override
    public void getZhiHuDetail(String id) {
        Subscription subscription = ApiManage.getInstance().getZhiHuService().getZhihuItemDetial(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuItemDetial>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mZhiHuDetialOnListener.onFailure(e);
                    }

                    @Override
                    public void onNext(ZhiHuItemDetial zhiHuItemDetial) {
                        mZhiHuDetialOnListener.onSuccess(zhiHuItemDetial);
                    }
                });
        addSubscription(subscription);
    }

    public interface ZhiHuDetialOnListener {
        void onSuccess(ZhiHuItemDetial zhiHuItemDetial);

        void onFailure(Throwable e);
    }
}
