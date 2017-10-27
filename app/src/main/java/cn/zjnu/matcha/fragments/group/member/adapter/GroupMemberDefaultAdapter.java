package cn.zjnu.matcha.fragments.group.member.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.factory.model.group.member.MemberInfo;
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
            HeaderHolder headerHolder = (HeaderHolder) holder;
            Character c = mMemberInfos.get(position).getUsername().charAt(0);
            headerHolder.txtGroupMemberNameHead
                    .setText(String.format(
                            Matcha.getApplicationContext().getString(R.string.group_member_header)
                            , getLetter(c), "2"));
        } else {
            NormalHolder normalHolder = (NormalHolder) holder;
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
