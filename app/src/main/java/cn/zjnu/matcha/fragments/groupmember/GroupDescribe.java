package cn.zjnu.matcha.fragments.groupmember;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.GroupMemberActivity;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.fragments.communicate.chat.ChatGroupFragment;


/**
 * Created by fsh on 2017/10/23.
 */

public class GroupDescribe extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_group_member)
    LinearLayout mLinear;
    @BindView(R.id.ib_add_group_member)
    ImageButton mAdd;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_group_desc;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberActivity.show(v.getContext());
            }
        });
    }

    public static void show(Context context) {
        Intent intent = new Intent(context, GroupDescribe.class);
        context.startActivity(intent);
    }


}
