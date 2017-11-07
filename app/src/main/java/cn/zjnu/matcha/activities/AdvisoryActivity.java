package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;
import cn.zjnu.matcha.fragments.advisory.AdvisoryDetailFragment;
import cn.zjnu.matcha.fragments.advisory.AdvisoryFragment;
import cn.zjnu.matcha.fragments.advisory.IAdvisoryTrigger;

public class AdvisoryActivity extends BaseActivity implements IAdvisoryTrigger {
    public static final String ADVISORY_MODEL = "ADVISORY_MODEL";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void show(Context context) {
        Intent intent = new Intent(context, AdvisoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_advisory;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new AdvisoryFragment())
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
    public void triggerView(@Nullable SpecialistModel model) {
        Bundle bundle = new Bundle();
        bundle.putString(ADVISORY_MODEL, JSONObject.toJSONString(model));
        BaseFragment fragment = AdvisoryDetailFragment.newInstance(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }
}
