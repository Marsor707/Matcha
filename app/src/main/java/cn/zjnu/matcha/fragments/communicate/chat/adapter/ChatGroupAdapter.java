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
    private ContentLongClickListener mLongClickListener;

    //文本
    private final int TYPE_SEND_TXT = 0;
    private final int TYPE_RECEIVE_TXT = 1;

    //自定义消息
    private final int TYPE_CUSTOM_TXT = 13;

    public ChatGroupAdapter(Context context, Conversation conversation, ContentLongClickListener longClickListener) {
        this.mContext = context;
        this.mConversation = conversation;
        this.mInflater = LayoutInflater.from(context);
        this.mLongClickListener = longClickListener;
        DisplayMetrics dm = new DisplayMetrics();
        mActivity = (Activity) context;
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        mMsgList = mConversation.getMessagesFromNewest(0, mOffset);
        reverse(mMsgList);
        mStart = mOffset;
        GroupInfo groupInfo = (GroupInfo) mConversation.getTargetInfo();
        mGroupId = groupInfo.getGroupID();
    }

    public ChatGroupAdapter(Context context, Conversation conversation,
                            ContentLongClickListener longClickListener,
                            int msgId) {
        this.mContext = context;
        this.mConversation = conversation;
        this.mInflater = LayoutInflater.from(context);
        this.mLongClickListener = longClickListener;
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
            default:
                return null;
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
            sendViewHolder.imgPortrait.setOnLongClickListener(mLongClickListener);
            switch (msg.getContentType()) {
                case text:
                    final String content = ((TextContent) msg.getContent()).getText();
                    sendViewHolder.txtContent.setText(content);
                    sendViewHolder.txtContent.setTag(position);
                    sendViewHolder.txtContent.setOnLongClickListener(mLongClickListener);
                    break;
                default:
                    break;
            }
        } else if (holder instanceof TextReceiverViewHolder) {
            final TextReceiverViewHolder receiverViewHolder = (TextReceiverViewHolder) holder;
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())) {
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
            receiverViewHolder.imgPortrait.setOnLongClickListener(mLongClickListener);
            switch (msg.getContentType()) {
                case text:
                    final String content = ((TextContent) msg.getContent()).getText();
                    receiverViewHolder.txtContent.setText(content);
                    receiverViewHolder.txtContent.setTag(position);
                    receiverViewHolder.txtContent.setOnLongClickListener(mLongClickListener);
                    break;
                default:
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
            default:
                return TYPE_CUSTOM_TXT;
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    public class TextSendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.img_portrait)
        CircleImageView imgPortrait;

        public TextSendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class TextReceiverViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.img_portrait)
        CircleImageView imgPortrait;

        public TextReceiverViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public Message getMessage(int position) {
        return mMsgList.get(position);
    }


    public void removeMessage(Message message) {
        for (Message msg : mMsgList) {
            if (msg.getServerMessageId().equals(message.getServerMessageId())) {
                delMessage.add(msg);
            }
        }
        mMsgList.removeAll(delMessage);
        notifyDataSetChanged();
    }

    //找到撤回的那一条消息,并且用撤回后event下发的去替换掉这条消息在集合中的原位置
    public void delMsgRetract(Message message) {
        int i = 0;
        for (Message msg : mMsgList) {
            if (msg.getServerMessageId().equals(message.getServerMessageId())) {
                i = mMsgList.indexOf(msg);
                forDel.add(msg);
            }
        }
        mMsgList.removeAll(forDel);
        mMsgList.add(i, message);
        notifyDataSetChanged();
    }

    public static abstract class ContentLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            onContentLongClick((Integer) v.getTag(), v);
            return true;
        }

        public abstract void onContentLongClick(int position, View view);
    }

}
