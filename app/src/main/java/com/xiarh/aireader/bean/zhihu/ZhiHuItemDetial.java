package com.xiarh.aireader.bean.zhihu;

import java.util.List;

/**
 * Created by xiarh on 2016/9/9.
 */
public class ZhiHuItemDetial {
    private String body;
    private String title;
    private String image;
    private String share_url;
    private List<String> css;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
