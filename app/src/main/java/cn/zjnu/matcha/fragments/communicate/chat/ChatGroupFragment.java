package cn.zjnu.matcha.fragments.communicate.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.content.EventNotificationContent;
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
import cn.zjnu.matcha.fragments.panel.PanelFragment;
import cn.zjnu.matcha.interfaze.IKeySend;

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
    @BindView(R.id.btn_more)
    ImageView mMore;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private IKeySend mSendListener = new IKeySend() {
        @Override
        public void onSendMessage() {
            String content = mContent.getText().toString();
            if (!content.equals("")) {
                mContent.setText("");
                // TODO: 发送逻辑
                Message msg;
                TextContent msgContent = new TextContent(content);
                msg = mConversation.createSendMessage(msgContent);
                mAdapter.addMsgToList(msg);
                mPresenter.sendMessage(msg);
                scrollToBottom();
            }
        }
    };


    public static ChatGroupFragment newInstance(Bundle bundle) {
        ChatGroupFragment fragment = new ChatGroupFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.btn_more)
    void onMoreClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showMore();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MessageActivity) getActivity()).setSendListener(mSendListener);
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
        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);

    }

    private void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanelBoss.closePanel();
                getActivity().finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_setting);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                GroupSettingActivity.show(getContext(), mGroupId);
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
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (v instanceof RecyclerView) {
                        if (mPanelBoss.isOpen()) {
                            mPanelBoss.closePanel();
                        }
                        Util.hideKeyboard(v);
                    }
                    return true;
                }
                return false;
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mPanelBoss.isOpen()) {
                    mPanelBoss.closePanel();
                }
                LinearLayoutManager scrollManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = scrollManager.findFirstVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && visibleItemCount == 0) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadMoreMessage();
                        }
                    });
                }
            }
        });
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
            mAdapter.addMsgToList(message);
            scrollToBottom();
            switch (message.getContentType()) {
                case eventNotification:
                    EventNotificationContent.EventNotificationType type =
                            ((EventNotificationContent) message.getContent()).getEventNotificationType();
                    switch (type) {
                        case group_member_exit:
                            getActivity().finish();
                            break;
                        case group_member_removed:
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}