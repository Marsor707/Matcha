package cn.zjnu.matcha.fragments.personal;

import android.os.Bundle;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.core.app.Matcha;

/**
 * Created by Hu on 2017/10/17.
 */

public class PersonalFragment extends BaseFragment {
    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_personal_message;
    }

    public static PersonalFragment newInstance(Bundle bundle) {
        PersonalFragment personalFragment = new PersonalFragment();
        personalFragment.setArguments(bundle);
        return personalFragment;
    }
}
