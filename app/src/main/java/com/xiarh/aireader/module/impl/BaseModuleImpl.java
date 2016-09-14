package com.xiarh.aireader.module.impl;

import com.xiarh.aireader.module.IBaseModule;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xiarh on 2016/9/8.
 */
public class BaseModuleImpl implements IBaseModule {
    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription subscriptions) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscriptions);
    }

    @Override
    public void unsubsrible() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
