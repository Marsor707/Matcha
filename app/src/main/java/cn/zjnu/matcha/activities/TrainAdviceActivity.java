package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterActivity;
import cn.zjnu.matcha.factory.mvp.advice.TrainAdviceContract;
import cn.zjnu.matcha.factory.mvp.advice.TrainAdvicePresenter;

public class TrainAdviceActivity extends PresenterActivity<TrainAdviceContract.Presenter> implements TrainAdviceContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void show(Context context) {
        Intent intent = new Intent(context, TrainAdviceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_train_advice;
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
    protected TrainAdvicePresenter initPresenter() {
        return new TrainAdvicePresenter(this);
    }

    @Override
    public void getTrainAdviceSuccess(String advice) {

    }
}
