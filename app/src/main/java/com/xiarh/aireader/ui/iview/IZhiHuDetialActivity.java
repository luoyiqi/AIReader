package com.xiarh.aireader.ui.iview;

import com.xiarh.aireader.bean.zhihu.ZhiHuItemDetial;

/**
 * Created by xiarh on 2016/9/9.
 */
public interface IZhiHuDetialActivity {
    void showZhiHuDetial(ZhiHuItemDetial zhiHuItemDetial);

    void showError(Throwable e);
}
