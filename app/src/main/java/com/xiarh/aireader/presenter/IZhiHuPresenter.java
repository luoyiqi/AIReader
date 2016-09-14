package com.xiarh.aireader.presenter;

/**
 * Created by xiarh on 2016/9/8.
 */
public interface IZhiHuPresenter extends IBasePresenter {
    void getDailyStories(String date);

    void getLastFromCache();
}
