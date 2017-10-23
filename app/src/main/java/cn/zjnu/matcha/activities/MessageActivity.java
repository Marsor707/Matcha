package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.fragments.communicate.chat.ChatGroupFragment;

public class MessageActivity extends BaseActivity {

    //接收者id 可以是群 也可以是人
    public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";
    //是否是群
    public static final String KEY_RECEIVER_IS_GROUP = "KEY_RECEIVER_IS_GROUP";

    private long mReceiverId;
    private boolean mIsGroup;

    /**
     * 发起群聊
     */
    public static void show(Context context, GroupInfo group) {
        if (context == null || group == null || group.getGroupID() == -1)
            return;
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, group.getGroupID());
        intent.putExtra(KEY_RECEIVER_IS_GROUP, true);
        context.startActivity(intent);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mReceiverId = bundle.getLong(KEY_RECEIVER_ID);
        mIsGroup = bundle.getBoolean(KEY_RECEIVER_IS_GROUP);
        return mReceiverId != -1;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        BaseFragment fragment;
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_RECEIVER_ID, mReceiverId);
        if (mIsGroup) {
            fragment = ChatGroupFragment.newInstance(bundle);
        } else {
            fragment = null;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lay_container, fragment)
                .commit();
    }
}
