package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.fragments.group.member.GroupMemberDefaultFragment;
import cn.zjnu.matcha.fragments.group.member.GroupMemberRankFragment;
import cn.zjnu.matcha.fragments.group.member.adapter.GroupMemberAdapter;

/**
 * Created by fsh on 2017/10/23.
 */

public class GroupMemberActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    public static void show(Context context, long id) {
        Intent intent = new Intent(context, GroupMemberActivity.class);
        intent.putExtra("group_id", id);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_group_member;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar();
        initTabLayout();
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTabLayout() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        GroupMemberRankFragment groupMemberRankFragment = new GroupMemberRankFragment();
        GroupMemberDefaultFragment groupMemberDefaultFragment = GroupMemberDefaultFragment.newInstance(getIntent().getExtras());
        fragmentList.add(groupMemberRankFragment);
        fragmentList.add(groupMemberDefaultFragment);
        String resActivity = getText(R.string.activity_member_list).toString();
        String resDefault = getText(R.string.default_member_list).toString();
        titles.add(resActivity);
        titles.add(resDefault);
        GroupMemberAdapter adapter = new GroupMemberAdapter(getSupportFragmentManager(), fragmentList, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
