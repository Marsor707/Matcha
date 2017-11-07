package cn.zjnu.matcha.fragments.advisory.adpater;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.ui.recycler.DataConvert;
import cn.zjnu.matcha.factory.model.advisory.LeaveMessageModel;
import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;

/**
 * Created by Hu on 2017/11/7.
 */

public class AdvisoryAdapter extends BaseQuickAdapter<SpecialistModel, BaseViewHolder> {
    private AdvisoryAdapter(@Nullable List<SpecialistModel> data) {
        super(R.layout.item_specialist, data);
    }

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public static AdvisoryAdapter create(List<SpecialistModel> data) {
        return new AdvisoryAdapter(data);
    }

    public static AdvisoryAdapter create(DataConvert convert) {
        return new AdvisoryAdapter(((AdvisoryDataConvert) convert).convert());
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialistModel item) {
        helper.setText(R.id.specialist_name,
                String.format(mContext.getString(R.string.specialist_name), item.getName()));
        helper.setText(R.id.specialist_skill,
                String.format(mContext.getString(R.string.specialist_skill), item.getSkill()));
        helper.addOnClickListener(R.id.img_leave_msg);
        final int leaveMsgSize = item.getMessageModels().size();
        if (leaveMsgSize > 0) {
            helper.getView(R.id.lin_leave_msg).setVisibility(View.VISIBLE);
            for (int i = 0; i < leaveMsgSize; i++) {
                final LeaveMessageModel leaveMessageModel = item.getMessageModels().get(i);
                final LinearLayoutCompat mParentView = helper.getView(R.id.lin_leave_msg);
                final AppCompatTextView msgContent = (AppCompatTextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.item_leave_msg_content, mParentView, false);
                mParentView.addView(msgContent);
                if (mOnClickListener != null) {
                    msgContent.setOnClickListener(mOnClickListener);
                }
                msgContent.setText(String.format(mContext.getString(R.string.leave_msg_content)
                        , leaveMessageModel.getUserName()
                        , leaveMessageModel.getContent()));
            }
        } else {
            helper.getView(R.id.lin_leave_msg).setVisibility(View.INVISIBLE);
        }
    }
}
