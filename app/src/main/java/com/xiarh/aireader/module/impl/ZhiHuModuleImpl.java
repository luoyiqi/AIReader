package com.xiarh.aireader.module.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.xiarh.aireader.api.ApiManage;
import com.xiarh.aireader.bean.zhihu.ZhiHuDaily;
import com.xiarh.aireader.bean.zhihu.ZhiHuStory;
import com.xiarh.aireader.config.Config;
import com.xiarh.aireader.module.IZhiHuModule;
import com.xiarh.aireader.utils.CacheUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiarh on 2016/9/8.
 */
public class ZhiHuModuleImpl extends BaseModuleImpl implements IZhiHuModule {
    private ZhiHuOnListener mZhiHuOnListener;
    private CacheUtil mCacheUtil;
    private Gson gson;

    public ZhiHuModuleImpl(ZhiHuOnListener mZhiHuOnListener, Context mContext) {
        this.mZhiHuOnListener = mZhiHuOnListener;
        mCacheUtil = CacheUtil.get(mContext);
        gson = new Gson();
    }

    @Override
    public void getDailyStories(String date) {
        Subscription subscription = ApiManage.getInstance().getZhiHuService().getTheDaily(date)
                .map(new Func1<ZhiHuDaily, ZhiHuDaily>() {
                    @Override
                    public ZhiHuDaily call(ZhiHuDaily zhiHuDaily) {
                        String date = zhiHuDaily.getDate();
                        for (ZhiHuStory zhiHuStory : zhiHuDaily.getStories()) {
                            zhiHuStory.setDate(date);
                        }
                        return zhiHuDaily;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mZhiHuOnListener.onFailure(e);
                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        mCacheUtil.put(Config.ZHIHU, gson.toJson(zhiHuDaily));
                        mZhiHuOnListener.onSuccess(zhiHuDaily);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getLastFromCache() {
        if (mCacheUtil.getAsObject(Config.ZHIHU) != null) {
            ZhiHuDaily zhiHuDaily = gson.fromJson(mCacheUtil.getAsObject(Config.ZHIHU).toString(), ZhiHuDaily.class);
            mZhiHuOnListener.onSuccess(zhiHuDaily);
        }
    }

    public interface ZhiHuOnListener {
        void onSuccess(ZhiHuDaily zhiHuDaily);

        void onFailure(Throwable e);
    }
}
