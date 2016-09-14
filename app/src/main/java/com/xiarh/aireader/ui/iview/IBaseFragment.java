package com.xiarh.aireader.ui.iview;

/**
 * Created by xiarh on 2016/9/7.
 */
public interface IBaseFragment {
    void showProgressDialog();

    void hideProgressDialog();

    void showError(String error);
}
