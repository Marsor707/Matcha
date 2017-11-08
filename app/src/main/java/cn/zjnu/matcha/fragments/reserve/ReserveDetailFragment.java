package cn.zjnu.matcha.fragments.reserve;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.ReserveActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.reserve.ReserveModel;
import cn.zjnu.matcha.factory.mvp.reserve.ReserveDetailContract;
import cn.zjnu.matcha.factory.mvp.reserve.ReserveDetailPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveDetailFragment extends PresenterFragment<ReserveDetailContract.Presenter> implements ReserveDetailContract.View {

    private ReserveModel mReserveModel;

    @BindView(R.id.txt_reserve_name)
    TextView mReserveName;
    @BindView(R.id.txt_reserve_content)
    TextView mReserveContent;
    @BindView(R.id.btn_submit)
    Button mBtnReserve;

    @OnClick(R.id.btn_submit)
    void onReserveClick() {
        mPresenter.reserve(mReserveModel.getId());
    }

    public static ReserveDetailFragment newInstance(Bundle bundle) {
        ReserveDetailFragment fragment = new ReserveDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        final String reserveStr = bundle.getString(ReserveActivity.RESERVE_MODEL);
        mReserveModel = JSONObject.parseObject(reserveStr, ReserveModel.class);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getReserveState(mReserveModel.getId());
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mReserveName.setText(mReserveModel.getName());
        mReserveContent.setText(mReserveModel.getContent());
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_reserve_detail;
    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().
                popBackStack();
        return true;
    }

    @Override
    protected ReserveDetailContract.Presenter initPresenter() {
        return new ReserveDetailPresenter(this);
    }

    @Override
    public void reserveSuccess() {
        setBtnEnabled(false);
    }

    @Override
    public void getReserveStateSuccess(boolean isReserved) {
        setBtnEnabled(!isReserved);
    }

    private void setBtnEnabled(boolean enabled) {
        mBtnReserve.setEnabled(enabled);
        mBtnReserve.setText(enabled ? "确定预约" : "已预约");
    }
}
