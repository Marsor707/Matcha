package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterActivity;
import cn.zjnu.matcha.factory.mvp.pe.EvaluateContract;
import cn.zjnu.matcha.factory.mvp.pe.EvaluatePresenter;

public class EvaluateActivity extends PresenterActivity<EvaluateContract.Presenter> implements EvaluateContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_empty_stub)
    LinearLayout mEmptyStub;

    public static void show(Context context) {
        Intent intent = new Intent(context, EvaluateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initToolbar();
    }

    @Override
    protected void initData() {
        super.initData();
        final long userId = JMessageClient.getMyInfo().getUserID();
        mPresenter.getEvaluateContent(userId);
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
    protected EvaluateContract.Presenter initPresenter() {
        return new EvaluatePresenter(this);
    }

    @Override
    public void getEvaluateContentSuccess() {
        mEmptyStub.setVisibility(View.GONE);
    }

    @Override
    public void getEvaluateContentFail() {
        mEmptyStub.setVisibility(View.VISIBLE);
    }
}
