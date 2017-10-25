package cn.zjnu.matcha.fragments.zone;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.LogUtils;

import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.IError;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneFragment extends BaseFragment {

    @OnClick(R.id.btn_test)
    void onTestClick() {
        final UserInfo userInfo = JMessageClient.getMyInfo();
        final String userName = userInfo.getUserName();
        RestClient.builder()
                .url("https://api.im.jpush.cn/v1/users/" + userName)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtils.json(response);
                        Matcha.showToast(response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Matcha.showToast(msg);
                    }
                })
                .build()
                .get();
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_zone;
    }

    public static ZoneFragment newInstance(Bundle bundle) {
        ZoneFragment fragment = new ZoneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
