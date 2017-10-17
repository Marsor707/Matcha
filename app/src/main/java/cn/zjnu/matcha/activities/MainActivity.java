package cn.zjnu.matcha.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.fragments.personal.PersonalFragment;
import cn.zjnu.matcha.widget.BottomNavigationHelper;
import cn.zjnu.matcha.widget.MyViewPager;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    MyViewPager mViewpager;
    @BindView(R.id.bottom_nav_view)
    BottomNavigationView mBottomNavView;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        BottomNavigationHelper.disableShiftMode(mBottomNavView);
    }

    @Override
    protected void initData() {
        super.initData();
        //服务器传过来的一些数据
        Bundle mPersonBundle = new Bundle();
        mPersonBundle.putInt("key", 1);
        fragments.add(PersonalFragment.newInstance(mPersonBundle));
    }
}
