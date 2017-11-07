package cn.zjnu.matcha.fragments.advisory;

import android.os.Bundle;

import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * Created by Hu on 2017/11/7.
 */

public class AdvisoryDetailFragment extends BaseFragment {
    public static AdvisoryDetailFragment newInstance(Bundle args) {
        AdvisoryDetailFragment fragment = new AdvisoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object getContentLayoutId() {
        return null;
    }
}
