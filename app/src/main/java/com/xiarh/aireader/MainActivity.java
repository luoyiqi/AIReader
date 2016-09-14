package com.xiarh.aireader;

import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.xiarh.aireader.ui.fragment.MeiZiFragment;
import com.xiarh.aireader.ui.fragment.WangYiFragment;
import com.xiarh.aireader.ui.fragment.ZhiHuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.drawerlayout)
    DrawerLayout drawaLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.zhihu));
        setSupportActionBar(toolbar);
        //首次加载显示知乎日报Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new ZhiHuFragment()).commit();
        //首次加载NavigationView知乎日报item选中
        navigationView.getMenu().getItem(0).setChecked(true);
        setNavigationView();
    }

    /**
     * 初始化NavigationView
     */
    private void setNavigationView() {
        //显示侧栏小工具
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawaLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawaLayout.setDrawerListener(toggle);
        toggle.syncState();
        //去掉scrollbar
        removeNavigationViewScrollbar(navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            private MenuItem menuItem;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (menuItem != null)
                    menuItem.setChecked(false);
                item.setChecked(true);
                switchFragment(item.getItemId());
                drawaLayout.closeDrawers();
                menuItem = item;
                return true;
            }
        });
    }

    /**
     * 去掉navigationview的scrollbar
     *
     * @param navigationView
     */
    private void removeNavigationViewScrollbar(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    /**
     * 切换Fragment，并且修改title
     *
     * @param itemId
     * @return
     */
    private void switchFragment(int itemId) {
        Fragment fragment = null;
        String title = "";
        switch (itemId) {
            case R.id.item_zhihu:
                fragment = new ZhiHuFragment();
                title = getResources().getString(R.string.zhihu);
                break;
            case R.id.item_wangyi:
                fragment = new WangYiFragment();
                title = getResources().getString(R.string.wangyi);
                break;
            case R.id.item_meizi:
                fragment = new MeiZiFragment();
                title = getResources().getString(R.string.meizi);
                break;
            default:
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        setTitle(title);
    }
}
