package cn.zjnu.matcha.fragments.communicate;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunicateFragment extends BaseFragment {

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_communicate;
    }

    public static CommunicateFragment newInstance(Bundle bundle) {
        CommunicateFragment fragment = new CommunicateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
