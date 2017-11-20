package cn.zjnu.matcha.factory.mvp.pe;

import cn.zjnu.matcha.core.factory.BasePresenter;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class EvaluatePresenter extends BasePresenter<EvaluateContract.View> implements EvaluateContract.Presenter {

    public EvaluatePresenter(EvaluateContract.View view) {
        super(view);
    }

    @Override
    public void getEvaluateContent(long userId) {
        getView().getEvaluateContentFail();
    }
}
