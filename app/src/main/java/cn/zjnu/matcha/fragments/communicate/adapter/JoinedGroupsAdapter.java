package cn.zjnu.matcha.fragments.communicate.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.R;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class JoinedGroupsAdapter extends BaseQuickAdapter<GroupInfo, BaseViewHolder> {

    private RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    public JoinedGroupsAdapter(@LayoutRes int layoutResId, @Nullable List<GroupInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupInfo item) {
        helper.setText(R.id.txt_group_name, item.getGroupName());
        final File file = item.getAvatarFile();
        final ImageView groupBackground = helper.getView(R.id.img_group_bg);
        if (file != null) {
            Glide.with(mContext)
                    .load(file)
                    .apply(requestOptions)
                    .into(groupBackground);
        }
    }
}
