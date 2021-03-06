package cn.zjnu.matcha.fragments.communicate.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Hu on 2017/10/22.
 */

public class ChatGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int PAGE_MESSAGE_COUNT = 18;
    private Activity mActivity;
    private Context mContext;
    private final LayoutInflater mInflater;
    private Conversation mConversation;
    private int mWidth;
    private int mOffset = PAGE_MESSAGE_COUNT;
    //当前第0项消息的位置
    private int mStart;
    private long mGroupId;
    private Queue<Message> mMessageQueue = new LinkedList<>();
    private List<Message> mMsgList = new ArrayList<>();
    //删除消息
    private List<Message> delMessage = new ArrayList<>();
    //撤回消息
    private List<Message> forDel = new ArrayList<>();

    //文本
    private final int TYPE_SEND_TXT = 0;
    private final int TYPE_RECEIVE_TXT = 1;
    //群成员变动
    private final int TYPE_GROUP_CHANGE = 10;
    //自定义消息
    private final int TYPE_CUSTOM_TXT = 13;

    public ChatGroupAdapter(Context context, Conversation conversation) {
        this.mContext = context;
        this.mConversation = conversation;
        this.mInflater = LayoutInflater.from(context);
        DisplayMetrics dm = new DisplayMetrics();
        mActivity = (Activity) context;
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        if (mConversation != null) {
            mMsgList = mConversation.getMessagesFromNewest(0, mOffset);
        }
        reverse(mMsgList);
        mStart = mOffset;
//        GroupInfo groupInfo = (GroupInfo) mConversation.getTargetInfo();
//        mGroupId = groupInfo.getGroupID();
    }

    public ChatGroupAdapter(Context context, Conversation conversation, int msgId) {
        this.mContext = context;
        this.mConversation = conversation;
        this.mInflater = LayoutInflater.from(context);
        DisplayMetrics dm = new DisplayMetrics();
        mActivity = (Activity) context;
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        if (mConversation.getUnReadMsgCnt() > PAGE_MESSAGE_COUNT) {
            mMsgList = mConversation.getMessagesFromNewest(0, mConversation.getUnReadMsgCnt());
            mStart = mConversation.getUnReadMsgCnt();
        } else {
            mMsgList = mConversation.getMessagesFromNewest(0, mOffset);
            mStart = mOffset;
        }
        reverse(mMsgList);
        mStart = mOffset;
        GroupInfo groupInfo = (GroupInfo) mConversation.getTargetInfo();
        mGroupId = groupInfo.getGroupID();
    }

    private void reverse(List<Message> mMsgList) {
        if (mMsgList.size() > 0) {
            Collections.reverse(mMsgList);//反转指定列表中的元素的顺序。
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SEND_TXT:
                return new TextSendViewHolder(mInflater.inflate(R.layout.cell_chat_right_text, parent, false));
            case TYPE_RECEIVE_TXT:
                return new TextReceiverViewHolder(mInflater.inflate(R.layout.cell_chat_left_text, parent, false));
            case TYPE_GROUP_CHANGE:
                return new GroupChangeViewHolder(mInflater.inflate(R.layout.cell_chat_item_group_change, parent, false));
            default:
                return new GroupChangeViewHolder(mInflater.inflate(R.layout.cell_chat_item_group_change, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Message msg = mMsgList.get(position);
        final UserInfo userInfo = msg.getFromUser();
        long nowDate = msg.getCreateTime();
        if (holder instanceof TextSendViewHolder) {
            final TextSendViewHolder sendViewHolder = (TextSendViewHolder) holder;
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())) {
                if (userInfo.getNickname() != null) {
                    sendViewHolder.texNickname.setText(userInfo.getNickname());
                } else {
                    sendViewHolder.texNickname.setText("");
                }
                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int i, String s, Bitmap bitmap) {
                        if (i == ResponseCodes.SUCCESSFUL) {
                            sendViewHolder.imgPortrait.setImageBitmap(bitmap);
                        } else {
                            sendViewHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                        }
                    }
                });
            } else {
                sendViewHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
            }
            sendViewHolder.imgPortrait.setTag(position);
            switch (msg.getContentType()) {
                case text:
                    final String content = ((TextContent) msg.getContent()).getText();
                    sendViewHolder.txtContent.setText(content);
                    sendViewHolder.txtContent.setTag(position);
                    break;
                default:
                    break;
            }
        } else if (holder instanceof TextReceiverViewHolder) {
            final TextReceiverViewHolder receiverViewHolder = (TextReceiverViewHolder) holder;
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())) {
                if (userInfo.getNickname() != null) {
                    receiverViewHolder.texNickname.setText(userInfo.getNickname());
                } else {
                    receiverViewHolder.texNickname.setText("");
                }
                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int i, String s, Bitmap bitmap) {
                        if (i == ResponseCodes.SUCCESSFUL) {
                            receiverViewHolder.imgPortrait.setImageBitmap(bitmap);
                        } else {
                            receiverViewHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                        }
                    }
                });
            } else {
                receiverViewHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
            }
            receiverViewHolder.imgPortrait.setTag(position);
            switch (msg.getContentType()) {
                case text:
                    final String content = ((TextContent) msg.getContent()).getText();
                    receiverViewHolder.txtContent.setText(content);
                    receiverViewHolder.txtContent.setTag(position);
                    break;
                default:
                    break;
            }
        } else {
            final GroupChangeViewHolder groupChangeViewHolder = (GroupChangeViewHolder) holder;
            switch (msg.getContentType()) {
                case eventNotification:
                    final String content = ((EventNotificationContent) msg.getContent()).getEventText();
                    EventNotificationContent.EventNotificationType type =
                            ((EventNotificationContent) msg.getContent()).getEventNotificationType();
                    switch (type) {
                        case group_member_added:
                        case group_member_exit:
                        case group_member_removed:
                        case group_info_updated:
                            groupChangeViewHolder.groupContent.setText(content);
                            groupChangeViewHolder.groupContent.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                    break;
                case prompt:
                    final String promptContent = ((PromptContent) msg.getContent()).getPromptText();
                    groupChangeViewHolder.groupContent.setText(promptContent);
                    groupChangeViewHolder.groupContent.setVisibility(View.VISIBLE);
                    break;
                default:
                    groupChangeViewHolder.groupContent.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = mMsgList.get(position);
        switch (msg.getContentType()) {
            case text:
                return msg.getDirect() == MessageDirect.send ? TYPE_SEND_TXT :
                        TYPE_RECEIVE_TXT;
            case eventNotification:
            case prompt:
                return TYPE_GROUP_CHANGE;
            default:
                return TYPE_CUSTOM_TXT;
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    class TextSendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.img_portrait)
        CircleImageView imgPortrait;
        @BindView(R.id.txt_nickname)
        TextView texNickname;

        public TextSendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TextReceiverViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.img_portrait)
        CircleImageView imgPortrait;
        @BindView(R.id.txt_nickname)
        TextView texNickname;

        public TextReceiverViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GroupChangeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.group_content)
        TextView groupContent;

        public GroupChangeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addMsgToList(Message message) {
        int oldItemCount = mMsgList.size();
        mMsgList.add(message);
        int newItemCount = mMsgList.size();
        incrementStartPosition();
        notifyItemInserted(newItemCount - 1);
    }

    //当有新消息加到MsgList，自增mStart
    private void incrementStartPosition() {
        ++mStart;
    }

    public void loadMoreMessage() {
        List<Message> oldMessage = mConversation.getMessagesFromNewest(mStart, mStart + 17);
        if (oldMessage != null && oldMessage.size() > 0) {
            mStart += oldMessage.size();
            reverse(oldMessage);
            mMsgList.addAll(0, oldMessage);
            notifyItemRangeInserted(0, oldMessage.size());
        }
    }
}
