package com.xiarh.aireader.bean.zhihu;

import java.util.List;

/**
 * Created by xiarh on 2016/9/7.
 */
public class  ZhiHuDaily {
    private String date;

    private List<ZhiHuStory> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhiHuStory> getStories() {
        return stories;
    }

    public void setStories(List<ZhiHuStory> stories) {
        this.stories = stories;
    }
}
