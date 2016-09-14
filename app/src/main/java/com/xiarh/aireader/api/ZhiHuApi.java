package com.xiarh.aireader.api;


import com.xiarh.aireader.bean.zhihu.ZhiHuDaily;
import com.xiarh.aireader.bean.zhihu.ZhiHuItemDetial;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xiarh on 2016/9/7.
 */
public interface ZhiHuApi {
    @GET("/api/4/news/before/{date}")
    Observable<ZhiHuDaily> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhiHuItemDetial> getZhihuItemDetial(@Path("id") String id);
}
