package cn.zjnu.matcha.fragments.reserve;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.ReserveActivity;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.factory.model.reserve.ReserveModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReserveDetailFragment extends BaseFragment {

    private ReserveModel mReserveModel;

    @BindView(R.id.txt_reserve_name)
    TextView mReserveName;
    @BindView(R.id.txt_reserve_content)
    TextView mReserveContent;

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
}
