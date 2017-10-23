package cn.zjnu.matcha.fragments.communicate.chat;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.MessageActivity;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupContract;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupPresenter;

/**
 * Created by fsh on 2017/10/17.
 */

public class ChatGroupFragment extends PresenterFragment<ChatGroupContract.Presenter> implements ChatGroupContract.View {

    private long mGroupId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_group_name)
    AppCompatTextView mTxtGroupName;

    public static ChatGroupFragment newInstance(Bundle bundle) {
        ChatGroupFragment fragment = new ChatGroupFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_chat_group;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        Bundle mBundle = getArguments();
        mGroupId = mBundle.getLong(MessageActivity.KEY_RECEIVER_ID);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPresenter.getGroupInfo(mGroupId);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_setting);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Matcha.showToast("hello");
                return true;
            }
        });
    }

    @Override
    public void initHeader(GroupInfo group) {
        String groupName = group.getGroupName();
        mTxtGroupName.setText(groupName);
    }

    @Override
    protected ChatGroupContract.Presenter initPresenter() {
        return new ChatGroupPresenter(this);
    }
}
