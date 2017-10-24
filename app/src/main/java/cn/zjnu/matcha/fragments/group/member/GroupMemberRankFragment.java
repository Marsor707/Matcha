package cn.zjnu.matcha.fragments.group.member;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zjnu.matcha.R;

/**
 * Created by fsh on 2017/10/22.
 */

public class GroupMemberRankFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_member_rank, container, false);
        return view;
    }
}
