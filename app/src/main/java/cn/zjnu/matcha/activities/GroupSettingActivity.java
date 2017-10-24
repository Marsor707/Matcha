package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;


/**
 * Created by fsh on 2017/10/23.
 */

public class GroupSettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_group_member)
    LinearLayout mLinear;
    @BindView(R.id.ib_add_group_member)
    ImageButton mAdd;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_group_setting;
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
        Intent intent = new Intent(context, GroupSettingActivity.class);
        context.startActivity(intent);
    }


}
