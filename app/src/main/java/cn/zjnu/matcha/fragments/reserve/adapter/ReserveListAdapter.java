package cn.zjnu.matcha.fragments.reserve.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.factory.model.reserve.ReserveModel;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ReserveListAdapter extends BaseQuickAdapter<ReserveModel, BaseViewHolder> {

    public ReserveListAdapter(@LayoutRes int layoutResId, @Nullable List<ReserveModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReserveModel item) {
        helper.setText(R.id.txt_reserve_name, item.getName());
        helper.setText(R.id.txt_reserve_number_now, item.getNumberNow() + "");
        helper.setText(R.id.txt_reserve_number_total, item.getNumberTotal() + "");
    }
}
