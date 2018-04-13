package cn.zjnu.matcha.fragments.function;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.AdvisoryActivity;
import cn.zjnu.matcha.activities.FoodAdviceActivity;
import cn.zjnu.matcha.activities.PEDataActivity;
import cn.zjnu.matcha.activities.ReserveActivity;
import cn.zjnu.matcha.activities.TrainAdviceActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.function.FunctionContract;
import cn.zjnu.matcha.factory.mvp.function.FunctionPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionFragment extends PresenterFragment<FunctionContract.Presenter> implements FunctionContract.View {

    RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    @BindView(R.id.txt_hi_username)
    TextView mTxtHiUsername;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.img_portrait)
    ImageView mPortrait;

    @OnClick(R.id.linear_reserve)
    void onReserveClick() {
        ReserveActivity.show(getContext());
    }

    @OnClick(R.id.linear_advisory)
    void onAdvisoryClick() {
        AdvisoryActivity.show(getContext());
    }

    @OnClick(R.id.linear_evaluate)
    void onEvaluateClick() {
        PEDataActivity.show(getContext());
    }

    @OnClick(R.id.linear_train_advice)
    void onTrainAdviceClick() {
        TrainAdviceActivity.show(getContext());
    }

    @OnClick(R.id.linear_food_advice)
    void onFoodAdviceClick() {
        FoodAdviceActivity.show(getContext());
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_function;
    }

    public static FunctionFragment newInstance(Bundle bundle) {
        FunctionFragment fragment = new FunctionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        initUserInfo();
        mPresenter.getUserPortrait();
    }

    private void initUserInfo() {
        UserInfo userInfo = JMessageClient.getMyInfo();
        final String username = userInfo.getUserName();
        mTxtHiUsername.setText(String.format(getString(R.string.label_hi_username), username));
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        String nowData = TimeUtils.getNowString(format);
        mTxtDate.setText(String.format(getString(R.string.label_date), nowData));
    }

    @Override
    protected FunctionContract.Presenter initPresenter() {
        return new FunctionPresenter(this);
    }

    @Override
    public void initPortrait(String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(mPortrait);
        }
    }
}
