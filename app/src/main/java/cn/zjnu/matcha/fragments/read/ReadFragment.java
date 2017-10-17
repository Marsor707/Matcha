package cn.zjnu.matcha.fragments.read;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadFragment extends BaseFragment {


    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_read;
    }

    public static ReadFragment newInstance(Bundle bundle) {
        ReadFragment fragment = new ReadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
