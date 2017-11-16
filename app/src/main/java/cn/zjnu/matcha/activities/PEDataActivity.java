package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterActivity;
import cn.zjnu.matcha.factory.model.pe.PEDataModel;
import cn.zjnu.matcha.factory.mvp.pe.PEDataContract;
import cn.zjnu.matcha.factory.mvp.pe.PEDataPresenter;

public class PEDataActivity extends PresenterActivity<PEDataContract.Presenter> implements PEDataContract.View {

    private LayoutInflater inflater;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pe_data_shape_container)
    LinearLayout mShapeContainer;
    @BindView(R.id.pe_data_function_container)
    LinearLayout mFunctionContainer;
    @BindView(R.id.pe_data_quality_container)
    LinearLayout mQualityContainer;
    @BindView(R.id.txt_pe_data_total_score)
    TextView mTotalScore;
    @BindView(R.id.btn_pe_data_evaluate)
    Button mBtnEvaluate;

    @OnClick(R.id.btn_pe_data_evaluate)
    void onGoEvaluateClick() {
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, PEDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_pe_data;
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
    protected void initData() {
        super.initData();
        inflater = LayoutInflater.from(this);
        final long userId = JMessageClient.getMyInfo().getUserID();
        mPresenter.getPEData(userId);
    }

    @Override
    protected PEDataContract.Presenter initPresenter() {
        return new PEDataPresenter(this);
    }

    @Override
    public void getPEDataSuccess(List<PEDataModel> shape, List<PEDataModel> function, List<PEDataModel> quality, float total) {
        initShape(shape);
        initFunction(function);
        initQuality(quality);
        initTotalScore(total);
    }

    @Override
    public void getPEDataFail() {
        mBtnEvaluate.setEnabled(false);
    }

    private void initShape(List<PEDataModel> shape) {
        final int size = shape.size();
        for (int i = 0; i < size; i++) {
            View root = inflater.inflate(R.layout.lay_pe_data_detail, mShapeContainer, false);
            TextView peDataName = (TextView) root.findViewById(R.id.txt_lay_pe_data_name);
            TextView peDataMark = (TextView) root.findViewById(R.id.txt_lay_pe_data_mark);
            TextView peDataScore = (TextView) root.findViewById(R.id.txt_lay_pe_data_score);
            peDataName.setText(shape.get(i).getName());
            peDataMark.setText(shape.get(i).getMark());
            peDataScore.setText(String.valueOf(shape.get(i).getScore()));
            mShapeContainer.addView(root);
        }
    }

    private void initFunction(List<PEDataModel> function) {
        final int size = function.size();
        for (int i = 0; i < size; i++) {
            View root = inflater.inflate(R.layout.lay_pe_data_detail, mFunctionContainer, false);
            TextView peDataName = (TextView) root.findViewById(R.id.txt_lay_pe_data_name);
            TextView peDataMark = (TextView) root.findViewById(R.id.txt_lay_pe_data_mark);
            TextView peDataScore = (TextView) root.findViewById(R.id.txt_lay_pe_data_score);
            peDataName.setText(function.get(i).getName());
            peDataMark.setText(function.get(i).getMark());
            peDataScore.setText(String.valueOf(function.get(i).getScore()));
            mFunctionContainer.addView(root);
        }
    }

    private void initQuality(List<PEDataModel> quality) {
        final int size = quality.size();
        for (int i = 0; i < size; i++) {
            View root = inflater.inflate(R.layout.lay_pe_data_detail, mQualityContainer, false);
            TextView peDataName = (TextView) root.findViewById(R.id.txt_lay_pe_data_name);
            TextView peDataMark = (TextView) root.findViewById(R.id.txt_lay_pe_data_mark);
            TextView peDataScore = (TextView) root.findViewById(R.id.txt_lay_pe_data_score);
            peDataName.setText(quality.get(i).getName());
            peDataMark.setText(quality.get(i).getMark());
            peDataScore.setText(String.valueOf(quality.get(i).getScore()));
            mQualityContainer.addView(root);
        }
    }

    private void initTotalScore(float total) {
        mTotalScore.setText(String.format(getString(R.string.label_pe_data_total_score), String.valueOf(total)));
    }

}
