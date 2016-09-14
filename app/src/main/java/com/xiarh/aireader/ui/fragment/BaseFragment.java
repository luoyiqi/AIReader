package com.xiarh.aireader.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.xiarh.aireader.R;
import com.xiarh.aireader.utils.SharePreferenceUtil;

/**
 * Created by xiarh on 2016/9/6.
 */
public class BaseFragment extends Fragment {

    public void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setColorSchemeColors(getActivity().getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).getInt(SharePreferenceUtil.VIBRANT, ContextCompat.getColor(getActivity(), R.color.colorAccent)));
    }
}
