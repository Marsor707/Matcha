package cn.zjnu.matcha.fragments.read;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadFragment extends BaseFragment {
    AgentWeb mAgentWeb;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("https://github.com/Marsor707");
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_read;
    }

    @Override
    public boolean onBackPressed() {
        return mAgentWeb.back();
    }

    public static ReadFragment newInstance(Bundle bundle) {
        ReadFragment fragment = new ReadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
