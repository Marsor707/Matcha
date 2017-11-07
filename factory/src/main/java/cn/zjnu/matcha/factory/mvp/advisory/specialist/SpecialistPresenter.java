package cn.zjnu.matcha.factory.mvp.advisory.specialist;

import cn.zjnu.matcha.core.factory.BasePresenter;

/**
 * Created by Hu on 2017/11/7.
 */

public class SpecialistPresenter extends BasePresenter<SpecialistContract.View> implements SpecialistContract.Presenter {
    public SpecialistPresenter(SpecialistContract.View view) {
        super(view);
    }

    @Override
    public void getSpecialist() {
        //TODO: 从后端获取专家数据
    }
}
