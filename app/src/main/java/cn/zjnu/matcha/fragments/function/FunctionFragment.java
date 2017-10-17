package cn.zjnu.matcha.fragments.function;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionFragment extends BaseFragment {

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_function;
    }

    public static FunctionFragment newInstance(Bundle bundle) {
        FunctionFragment fragment = new FunctionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
