package cn.zjnu.matcha.fragments.advisory.adpater;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;

/**
 * Created by Hu on 2017/11/7.
 */

public class AdvisoryAdapter extends BaseQuickAdapter<SpecialistModel, BaseViewHolder> {
    public AdvisoryAdapter(@LayoutRes int layoutResId, @Nullable List<SpecialistModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialistModel item) {
        helper.setText(R.id.specialist_name,
                String.format(Matcha.getApplicationContext().getString(R.string.specialist_name), item.getName()));
        helper.setText(R.id.specialist_skill,
                String.format(Matcha.getApplicationContext().getString(R.string.specialist_skill), item.getSkill()));
    }
}
