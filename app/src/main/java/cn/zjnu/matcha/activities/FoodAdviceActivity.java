package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterActivity;
import cn.zjnu.matcha.factory.mvp.advice.FoodAdviceContract;
import cn.zjnu.matcha.factory.mvp.advice.FoodAdvicePresenter;

public class FoodAdviceActivity extends PresenterActivity<FoodAdviceContract.Presenter> implements FoodAdviceContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_advice)
    TextView mTvAdvice;
    @BindView(R.id.linear_empty_stub)
    LinearLayout mLayEmpty;

    public static void show(Context context) {
        Intent intent = new Intent(context, FoodAdviceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_food_advice;
    }

    @Override
    protected void initData() {
        final long userId = JMessageClient.getMyInfo().getUserID();
        mPresenter.getFoodAdviceData(userId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar();
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

    @Override
    public void getFoodAdviceSuccess(String advice) {
        mLayEmpty.setVisibility(View.GONE);
        mTvAdvice.setText(advice);
        mTvAdvice.setVisibility(View.VISIBLE);
    }

    @Override
    protected FoodAdviceContract.Presenter initPresenter() {
        return new FoodAdvicePresenter(this);
    }
}
