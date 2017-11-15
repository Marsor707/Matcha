package cn.zjnu.matcha.fragments.advisory.adpater;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.ui.recycler.DataConvert;
import cn.zjnu.matcha.factory.model.advisory.LeaveMessageModel;
import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hu on 2017/11/7.
 */

public class AdvisoryAdapter extends BaseQuickAdapter<SpecialistModel, BaseViewHolder> {
    private Map<String, List<LeaveMessageModel>> msgList = new HashMap<>();
    private RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    private AdvisoryAdapter(@Nullable List<SpecialistModel> data) {
        super(R.layout.item_specialist, data);
    }

    public static AdvisoryAdapter create(List<SpecialistModel> data) {
        return new AdvisoryAdapter(data);
    }

    public static AdvisoryAdapter create(DataConvert convert) {
        return new AdvisoryAdapter(((SpecialDataConvert) convert).convert());
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialistModel item) {
        helper.setText(R.id.specialist_name,
                String.format(mContext.getString(R.string.specialist_name), item.getExpertName()));
        helper.setText(R.id.specialist_skill,
                String.format(mContext.getString(R.string.specialist_skill), item.getArea()));
        final CircleImageView mImageView = helper.getView(R.id.img_portrait);
        final int msgSize = msgList.get(item.getExpertId()) == null ? 0 : msgList.get(item.getExpertId()).size();
        final LinearLayoutCompat linMsg = helper.getView(R.id.lin_leave_msg);
        if (msgSize > 0) {
            linMsg.setVisibility(View.VISIBLE);
            if (linMsg.getChildCount() > 0) {
                linMsg.removeAllViews();
            }
            for (int i = 0; i < msgSize; i++) {
                final AppCompatTextView textView = (AppCompatTextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.item_leave_msg_content, linMsg, false);
                final int type = msgList.get(item.getExpertId()).get(i).getItem();
                String name = null;
                if (type == 1) {
                    name = msgList.get(item.getExpertId()).get(i).getUserName();
                } else if (type == 2) {
                    name = msgList.get(item.getExpertId()).get(i).getExpertName();
                }
                textView.setText(String.format(mContext.getString(R.string.leave_msg_content),
                        name,
                        msgList.get(item.getExpertId()).get(i).getContent()));
                linMsg.addView(textView);
            }
        } else {
            linMsg.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(item.getPicture())
                .apply(requestOptions)
                .into(mImageView);
        helper.addOnClickListener(R.id.img_leave_msg);
    }

    public final void showMsg(List<LeaveMessageModel> models, String expertId, int position) {
        msgList.put(expertId, models);
        notifyItemChanged(position);
    }

    public final void updateMsg(String expertId, LeaveMessageModel messageModel, int position) {
        final List<LeaveMessageModel> model = msgList.get(expertId);
        if (model != null) {
            msgList.get(expertId).add(messageModel);
        } else {
            final List<LeaveMessageModel> models = new ArrayList<>();
            models.add(messageModel);
            msgList.put(expertId, models);
        }
        notifyItemChanged(position);
    }
}
