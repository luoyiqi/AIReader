package com.xiarh.aireader.bean.zhihu;

import java.util.List;

/**
 * Created by xiarh on 2016/9/7.
 */
public class ZhiHuStory {
    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;
    private String date;

    public ZhiHuStory(String date, String ga_prefix, int id, List<String> images, String title, int type) {
        this.date = date;
        this.ga_prefix = ga_prefix;
        this.id = id;
        this.images = images;
        this.title = title;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
