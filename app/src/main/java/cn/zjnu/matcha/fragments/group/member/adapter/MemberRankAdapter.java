package cn.zjnu.matcha.fragments.group.member.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.factory.model.group.member.MemberRankInfo;
import cn.zjnu.matcha.factory.model.jiguang.ResponseCodes;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class MemberRankAdapter extends BaseQuickAdapter<MemberRankInfo, BaseViewHolder> {

    public MemberRankAdapter(@LayoutRes int layoutResId, @Nullable List<MemberRankInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MemberRankInfo item) {
        final String userName = item.getUserName();
        final ImageView imageView = helper.getView(R.id.img_portrait);
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == ResponseCodes.SUCCESSFUL) {
                    File file = userInfo.getAvatarFile();
                    if (file.exists()) {
                        Glide.with(mContext)
                                .load(file)
                                .into(imageView);
                    }
                }
            }
        });
        helper.setText(R.id.txt_group_member_name, userName);
    }
}
