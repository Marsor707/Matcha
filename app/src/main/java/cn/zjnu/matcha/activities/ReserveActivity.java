package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.factory.model.reserve.ReserveModel;
import cn.zjnu.matcha.fragments.reserve.IReserveTrigger;
import cn.zjnu.matcha.fragments.reserve.ReserveDetailFragment;
import cn.zjnu.matcha.fragments.reserve.ReserveListFragment;

public class ReserveActivity extends BaseActivity implements IReserveTrigger {

    public static final String RESERVE_MODEL = "RESERVE_MODEL";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void show(Context context) {
        Intent intent = new Intent(context, ReserveActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_reserve;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new ReserveListFragment())
                .commit();
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void triggerView(@NonNull ReserveModel model) {
        Bundle bundle = new Bundle();
        bundle.putString(RESERVE_MODEL, JSONObject.toJSONString(model));
        BaseFragment fragment = ReserveDetailFragment.newInstance(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}
