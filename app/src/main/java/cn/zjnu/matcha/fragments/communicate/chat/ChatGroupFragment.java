package cn.zjnu.matcha.fragments.communicate.chat;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.GroupSettingActivity;
import cn.zjnu.matcha.activities.MessageActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupContract;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupPresenter;
import cn.zjnu.matcha.fragments.communicate.chat.adapter.ChatGroupAdapter;
import cn.zjnu.matcha.fragments.communicate.chat.adapter.TextWatcherAdapter;
import cn.zjnu.matcha.fragments.panel.PanelFragment;

/**
 * Created by fsh on 2017/10/17.
 */

public class ChatGroupFragment extends PresenterFragment<ChatGroupContract.Presenter> implements ChatGroupContract.View {

    private long mGroupId;
    private AirPanel.Boss mPanelBoss;
    private PanelFragment mPanelFragment;

    private Conversation mConversation;
    private ChatGroupAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_group_name)
    AppCompatTextView mTxtGroupName;
    @BindView(R.id.edit_content)
    EditText mContent;
    @BindView(R.id.btn_submit)
    ImageView mSubmit;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;


    public static ChatGroupFragment newInstance(Bundle bundle) {
        ChatGroupFragment fragment = new ChatGroupFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.btn_face)
    void onFaceClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showFace();
    }

    @OnClick(R.id.btn_record)
    void onRecordClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showRecord();
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (mSubmit.isActivated()) {
            //发送
            String content = mContent.getText().toString();
            mContent.setText("");
            // TODO: 发送逻辑
            Message msg;
            TextContent msgContent = new TextContent(content);
            msg = mConversation.createSendMessage(msgContent);
            mAdapter.addMsgToList(msg);
            mPresenter.sendMessage(msg);
            scrollToBottom();
            mContent.setText("");
        } else {
            onMoreClick();
        }
    }

    private void onMoreClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showGallery();
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
        initPanel(root);
        initEditContent();
        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);

    }

    private void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyboard(mContent);
                mPanelBoss.closePanel();
                getActivity().finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_setting);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                GroupSettingActivity.show(getContext());
                return true;
            }
        });
    }

    private void initPanel(View root) {
        mPanelBoss = (AirPanel.Boss) root.findViewById(R.id.lay_content);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                Util.hideKeyboard(mContent);
            }
        });
        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
            @Override
            public void onPanelStateChanged(boolean isOpen) {
                if (isOpen) {
                    //进行一些界面操作
                    scrollToBottom();
                }
            }

            @Override
            public void onSoftKeyboardStateChanged(boolean isOpen) {
                if (isOpen) {
                    //进行一些界面操作
                    scrollToBottom();
                }
            }
        });
    }

    //初始化输入框监听
    private void initEditContent() {
        mContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSendMsg = !TextUtils.isEmpty(content);
                //设置状态 改变对应的icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    @Override
    public void initHeader(GroupInfo group) {
        String groupName = group.getGroupName();
        mTxtGroupName.setText(groupName);
    }

    @Override
    public void getConversation(Conversation conversation) {
        this.mConversation = conversation;
        mAdapter = new ChatGroupAdapter(getContext(), conversation);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
//        scrollToBottom();
    }

    @Override
    protected ChatGroupContract.Presenter initPresenter() {
        return new ChatGroupPresenter(this);
    }

    @Override
    public boolean onBackPressed() {
        if (mPanelBoss.isOpen()) {
            //关闭面板并且返回true表示自己已经处理了返回消费
            mPanelBoss.closePanel();
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getGroupInfo(mGroupId);
        mPresenter.fetchConversation(mGroupId);
    }

    public void scrollToBottom() {
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }

    public void onEvent(MessageEvent event) {
        final Message message = event.getMessage();
        GroupInfo groupInfo = (GroupInfo) message.getTargetInfo();
        if (groupInfo.getGroupID() == mGroupId) {
            switch (message.getContentType()) {
                case text:
                    mAdapter.addMsgToList(message);
                    scrollToBottom();
                    break;
                default:
                    break;
            }
        }
    }
}