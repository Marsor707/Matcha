package cn.zjnu.matcha.factory.mvp.advice;

import cn.zjnu.matcha.core.factory.BasePresenter;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class TrainAdvicePresenter extends BasePresenter<TrainAdviceContract.View> implements TrainAdviceContract.Presenter {

    public TrainAdvicePresenter(TrainAdviceContract.View view) {
        super(view);
    }

    @Override
    public void getTrainAdviceData(long id) {
        //TODO 请求后端拿数据
    }
}
