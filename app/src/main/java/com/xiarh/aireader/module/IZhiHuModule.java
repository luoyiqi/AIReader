package com.xiarh.aireader.module;

/**
 * Created by xiarh on 2016/9/8.
 */
public interface IZhiHuModule extends IBaseModule {
    void getDailyStories(String date);

    void getLastFromCache();
}
