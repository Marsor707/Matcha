package cn.zjnu.matcha.factory.mvp.advice;

import cn.zjnu.matcha.core.factory.BasePresenter;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class FoodAdvicePresenter extends BasePresenter<FoodAdviceContract.View> implements FoodAdviceContract.Presenter {

    public FoodAdvicePresenter(FoodAdviceContract.View view) {
        super(view);
    }

    @Override
    public void getFoodAdviceData(long id) {

    }
}
