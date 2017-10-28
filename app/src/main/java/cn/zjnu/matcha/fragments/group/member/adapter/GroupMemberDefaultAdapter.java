package cn.zjnu.matcha.fragments.group.member.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.factory.model.group.member.MemberInfo;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hu on 2017/10/27.
 */

public class GroupMemberDefaultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private final LayoutInflater mInflater;
    private List<MemberInfo> mMemberInfos;
    private Context context;

    public GroupMemberDefaultAdapter(Context context, List<MemberInfo> mUserInfos) {
        this.mMemberInfos = mUserInfos;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        sortList(mUserInfos);
    }

    private void sortList(List<MemberInfo> mUserInfos) {
        Collections.sort(mUserInfos, new Comparator<MemberInfo>() {
            @Override
            public int compare(MemberInfo o1, MemberInfo o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderHolder(mInflater.inflate(R.layout.item_group_member_list_head, parent, false));
            default:
                return new NormalHolder(mInflater.inflate(R.layout.item_group_member_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            final HeaderHolder headerHolder = (HeaderHolder) holder;
            JMessageClient.getUserInfo(mMemberInfos.get(position).getUsername(), new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == ResponseCodes.SUCCESSFUL) {
                        setHolderAvatar(headerHolder, userInfo);
                    } else {
                        headerHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                    }
                }
            });
            Character c = mMemberInfos.get(position).getUsername().charAt(0);
            headerHolder.txtGroupMemberNameHead
                    .setText(String.format(
                            Matcha.getApplicationContext().getString(R.string.group_member_header)
                            , getLetter(c)));
            headerHolder.txtGroupMemberName.setText(mMemberInfos.get(position).getUsername());
        } else {
            final NormalHolder normalHolder = (NormalHolder) holder;
            JMessageClient.getUserInfo(mMemberInfos.get(position).getUsername(), new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == ResponseCodes.SUCCESSFUL) {
                        setHolderAvatar(normalHolder, userInfo);
                    } else {
                        normalHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                    }
                }
            });
            Character c = mMemberInfos.get(position).getUsername().charAt(0);
            normalHolder.txtGroupMemberName
                    .setText(String.format(Matcha.getApplicationContext().getString(R.string.group_member_header)
                            , mMemberInfos.get(position).getUsername()));
        }
    }

    private void setHolderAvatar(RecyclerView.ViewHolder holder, UserInfo userInfo) {
        if (holder instanceof HeaderHolder) {
            final HeaderHolder headerHolder = (HeaderHolder) holder;
            userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    if (i == ResponseCodes.SUCCESSFUL) {
                        headerHolder.imgPortrait.setImageBitmap(bitmap);
                    } else {
                        headerHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                    }
                }
            });
        } else {
            final NormalHolder normalHolder = (NormalHolder) holder;
            userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    if (i == ResponseCodes.SUCCESSFUL) {
                        normalHolder.imgPortrait.setImageBitmap(bitmap);
                    } else {
                        normalHolder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMemberInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ||
                (mMemberInfos.get(position).getUsername().charAt(0)
                        != mMemberInfos.get(position - 1).getUsername().charAt(0))) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    private String getLetter(Character c) {
        if (Character.isDigit(c)) {
            return "#";
        } else {
            return Character.toString(Character.toUpperCase(c));
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_group_member_name_head)
        TextView txtGroupMemberNameHead;
        @BindView(R.id.img_portrait)
        CircleImageView imgPortrait;
        @BindView(R.id.txt_group_member_name)
        TextView txtGroupMemberName;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_portrait)
        CircleImageView imgPortrait;
        @BindView(R.id.txt_group_member_name)
        TextView txtGroupMemberName;

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
