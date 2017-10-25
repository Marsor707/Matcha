package cn.zjnu.matcha.fragments.zone;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneFragment extends BaseFragment {

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
