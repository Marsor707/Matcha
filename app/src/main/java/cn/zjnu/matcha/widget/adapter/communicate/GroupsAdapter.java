package cn.zjnu.matcha.widget.adapter.communicate;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.R;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class GroupsAdapter extends BaseQuickAdapter<GroupInfo, BaseViewHolder> {

    public GroupsAdapter(@LayoutRes int layoutResId, @Nullable List<GroupInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupInfo item) {
        helper.setText(R.id.txt_group_name,item.getGroupName());
    }
}
