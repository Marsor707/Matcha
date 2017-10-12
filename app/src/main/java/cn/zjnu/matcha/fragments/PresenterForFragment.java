package cn.zjnu.matcha.fragments;

import cn.zjnu.matcha.core.factory.BasePresenter;
import cn.zjnu.matcha.core.net.RestClient;
import cn.zjnu.matcha.core.net.callbacks.ISuccess;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class PresenterForFragment extends BasePresenter<BlankFragment> implements ContractForFragment.Presenter {

    public PresenterForFragment(BlankFragment view) {
        super(view);
    }

    @Override
    public void startRest() {
        RestClient.builder()
                .url("about.php")
                .loader(getView().getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        getView().restSuccess(response);
                    }
                })
                .build()
                .get();
    }
}
