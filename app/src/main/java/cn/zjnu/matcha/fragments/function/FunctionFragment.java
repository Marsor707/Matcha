package cn.zjnu.matcha.fragments.function;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
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
        UserInfo userInfo = JMessageClient.getMyInfo();
        final String username = userInfo.getUserName();
        mTxtHiUsername.setText(String.format(getString(R.string.label_hi_username), username));
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        String nowData = TimeUtils.getNowString(format);
        mTxtDate.setText(String.format(getString(R.string.label_date), nowData));
    }
}
