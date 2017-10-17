package cn.zjnu.matcha.fragments.function;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionFragment extends BaseFragment {

    @BindView(R.id.txt_hi_username)
    TextView mTxtHiUsername;
    @BindView(R.id.txt_date)
    TextView mTxtDate;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_function;
    }

    public static FunctionFragment newInstance(Bundle bundle) {
        FunctionFragment fragment = new FunctionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mTxtHiUsername.setText(String.format(getString(R.string.label_hi_username), "机车男"));
        mTxtDate.setText(String.format(getString(R.string.label_date), "2017", "10", "17"));
    }
}
