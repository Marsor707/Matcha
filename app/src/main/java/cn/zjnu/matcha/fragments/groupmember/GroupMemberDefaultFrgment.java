package cn.zjnu.matcha.fragments.groupmember;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * Created by fsh on 2017/10/22.
 */

public class GroupMemberDefaultFrgment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_member_default, container, false);
        return view;
    }

}
