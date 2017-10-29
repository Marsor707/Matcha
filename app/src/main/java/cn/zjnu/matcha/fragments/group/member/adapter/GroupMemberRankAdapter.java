package cn.zjnu.matcha.fragments.group.member.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.factory.model.group.member.MemberInfo;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hu on 2017/10/29.
 */

public class GroupMemberRankAdapter extends RecyclerView.Adapter<GroupMemberRankAdapter.ViewHolder> {

    private Context mContext;
    private final LayoutInflater mInflater;
    private List<MemberInfo> mMemberInfos;

    public GroupMemberRankAdapter(Context mContext, List<MemberInfo> mMemberInfos) {
        this.mContext = mContext;
        this.mMemberInfos = mMemberInfos;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_group_member_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        JMessageClient.getUserInfo(mMemberInfos.get(position).getUsername(), new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    setAvatar(holder, userInfo);
                } else {
                    holder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                }
            }
        });
        holder.txtGroupMemberName.setText(mMemberInfos.get(position).getUsername());
    }

    private void setAvatar(final ViewHolder holder, UserInfo userInfo) {
        userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int i, String s, Bitmap bitmap) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    holder.imgPortrait.setImageBitmap(bitmap);
                } else {
                    holder.imgPortrait.setImageResource(R.drawable.bg_user_default_portrait);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMemberInfos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_portrait)
        CircleImageView imgPortrait;
        @BindView(R.id.txt_group_member_name)
        TextView txtGroupMemberName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
