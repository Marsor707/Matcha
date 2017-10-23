package cn.zjnu.matcha.fragments.communicate.chat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.MessageActivity;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupContract;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupPresenter;
import cn.zjnu.matcha.fragments.communicate.chat.adapter.ChatGroupAdapter;
import cn.zjnu.matcha.fragments.communicate.chat.view.TipItem;
import cn.zjnu.matcha.fragments.communicate.chat.view.TipView;

/**
 * Created by fsh on 2017/10/17.
 */

public class ChatGroupFragment extends PresenterFragment<ChatGroupContract.Presenter> implements ChatGroupContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_group_name)
    AppCompatTextView mTxtGroupName;
    @BindView(R.id.btn_submit)
    ImageView mBtnSubmit;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.edit_content)
    EditText mEditContent;

    private long mGroupId;
    private boolean fromGroup;

    private Conversation mConversation;
    private ChatGroupAdapter mAdapter;

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
        fromGroup = mBundle.getBoolean(MessageActivity.KEY_RECEIVER_IS_GROUP);
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

    @Override
    protected void initData() {
        super.initData();
        if (fromGroup) {
            mConversation = JMessageClient.getGroupConversation(mGroupId);
            mAdapter = new ChatGroupAdapter(getContext(), mConversation, longClickListener);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
        }
        initView();
    }

    private void initView() {
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msgContent = mEditContent.getText().toString();
                if (msgContent.equals("")) {
                    return;
                }
                Message msg;
                TextContent content = new TextContent(msgContent);
                msg = mConversation.createSendMessage(content);
                mAdapter.addMsgToList(msg);
                JMessageClient.sendMessage(msg);
                mEditContent.setText("");
            }
        });
    }

    public void onEvent(MessageEvent event) {
        final Message message = event.getMessage();
        switch (message.getContentType()) {
            case text:
                TextContent content = (TextContent) message.getContent();
                content.getText();
                mAdapter.addMsgToList(message);
                Log.d("message_content", content.getText());
                break;
            default:
                Log.d("message_content", "111111");
                break;
        }
    }

    private ChatGroupAdapter.ContentLongClickListener longClickListener = new ChatGroupAdapter.ContentLongClickListener() {
        @Override
        public void onContentLongClick(int position, View view) {
            final Message msg = mAdapter.getMessage(position);

            if (msg == null) {
                return;
            }

            if (msg.getContentType() == ContentType.text) {
                if (msg.getDirect() == MessageDirect.receive) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(getContext(), mRecyclerView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String name, int position) {
                                    if (position == 0) {
                                        if (msg.getContentType() == ContentType.text) {
                                            final String content = ((TextContent) msg.getContent()).getText();
                                            ClipboardManager clipboardManager = (ClipboardManager) getActivity()
                                                    .getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("Simple text", content);
                                            clipboardManager.setPrimaryClip(clip);
                                            Matcha.showToast("已复制");
                                        } else {
                                            Matcha.showToast("只支持复制文字");
                                        }
                                    } else {
                                        mConversation.deleteMessage(msg.getId());
                                        mAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                } else {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(getContext(), mRecyclerView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("撤回"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String name, int position) {
                                    if (position == 0) {
                                        if (msg.getContentType() == ContentType.text) {
                                            final String content = ((TextContent) msg.getContent()).getText();
                                            ClipboardManager clipboardManager = (ClipboardManager) getActivity()
                                                    .getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("Simple text", content);
                                            clipboardManager.setPrimaryClip(clip);
                                            Matcha.showToast("已复制");
                                        } else {
                                            Matcha.showToast("只支持复制文字");
                                        }
                                    } else if (position == 1) {
                                        mConversation.retractMessage(msg, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i == 855001) {
                                                    Matcha.showToast("消息发送超过3分钟，不能撤回");
                                                } else if (i == ResponseCodes.SUCCESSFUL) {
                                                    mAdapter.delMsgRetract(msg);
                                                }
                                            }
                                        });
                                    } else {
                                        mConversation.deleteMessage(msg.getId());
                                        mAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
            }
        }
    };
}
