package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.fragments.communicate.CommunicateFragment;
import cn.zjnu.matcha.fragments.function.FunctionFragment;
import cn.zjnu.matcha.fragments.personal.PersonalFragment;
import cn.zjnu.matcha.fragments.read.ReadFragment;
import cn.zjnu.matcha.fragments.zone.ZoneFragment;
import cn.zjnu.matcha.widget.BottomNavigationHelper;
import cn.zjnu.matcha.widget.MyViewPager;
import cn.zjnu.matcha.widget.adapter.BottomViewAdapter;

public class MainActivity extends BaseActivity implements OnNavigationItemSelectedListener {

    @BindView(R.id.viewpager)
    MyViewPager mViewpager;
    @BindView(R.id.bottom_nav_view)
    BottomNavigationView mBottomNavView;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

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
        fragments.add(ZoneFragment.newInstance(null));
        fragments.add(CommunicateFragment.newInstance(null));
        fragments.add(FunctionFragment.newInstance(null));
        fragments.add(ReadFragment.newInstance(null));
        Bundle mPersonBundle = new Bundle();
        mPersonBundle.putInt("key", 1);
        fragments.add(PersonalFragment.newInstance(mPersonBundle));

        BottomViewAdapter mAdapter = new BottomViewAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(mAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBottomNavView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zone:
                mViewpager.setCurrentItem(0, false);
                break;
            case R.id.communicate:
                mViewpager.setCurrentItem(1, false);
                break;
            case R.id.function:
                mViewpager.setCurrentItem(2, false);
                break;
            case R.id.read:
                mViewpager.setCurrentItem(3, false);
                break;
            case R.id.personal:
                mViewpager.setCurrentItem(4, false);
                break;
            default:
                break;
        }
        return true;
    }
}
