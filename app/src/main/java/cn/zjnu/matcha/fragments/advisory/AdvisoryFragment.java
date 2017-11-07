package cn.zjnu.matcha.fragments.advisory;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;
import cn.zjnu.matcha.factory.mvp.advisory.specialist.SpecialistContract;
import cn.zjnu.matcha.factory.mvp.advisory.specialist.SpecialistPresenter;
import cn.zjnu.matcha.fragments.advisory.adpater.AdvisoryAdapter;

/**
 * Created by Hu on 2017/11/7.
 */

public class AdvisoryFragment extends PresenterFragment<SpecialistContract.Presenter>
        implements SpecialistContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_advisory)
    RecyclerView mRecyclerView;
    private AdvisoryAdapter mAdapter;
    private IAdvisoryTrigger trigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trigger = (IAdvisoryTrigger) context;
    }

    @Override
    public void getSpecialistSuccess(String response) {
        final List<SpecialistModel> models = JSON.parseArray(response, SpecialistModel.class);
        mAdapter = new AdvisoryAdapter(R.layout.item_specialist, models);
        mAdapter.setOnItemClickListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected SpecialistContract.Presenter initPresenter() {
        return new SpecialistPresenter(this);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_advisory;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final SpecialistModel model = (SpecialistModel) adapter.getData().get(position);
        if (model != null) {
            trigger.triggerView(model);
        }
    }
}
