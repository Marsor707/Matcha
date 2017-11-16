package cn.zjnu.matcha.fragments.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.RegexUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.AccountActivity;
import cn.zjnu.matcha.activities.PEDataActivity;
import cn.zjnu.matcha.core.app.Matcha;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.personal.PersonalContract;
import cn.zjnu.matcha.factory.mvp.personal.PersonalPresenter;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hu on 2017/10/17.
 */

public class PersonalFragment extends PresenterFragment<PersonalContract.Presenter> implements PersonalContract.View {

    RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_portrait)
    CircleImageView mImgPortrait;
    @BindView(R.id.txt_nickname)
    SuperTextView mTxtNickname;
    @BindView(R.id.txt_name)
    SuperTextView mTxtName;
    @BindView(R.id.txt_phone)
    SuperTextView mTxtPhone;
    @BindView(R.id.txt_pe_data)
    SuperTextView mTxtPeData;
    @BindView(R.id.btn_log_out)
    AppCompatButton mBtnLogOut;


    @OnClick(R.id.btn_log_out)
    void onClickLogOut() {
        new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("温馨提示")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JMessageClient.logout();
                        Intent intent = new Intent(getContext(), AccountActivity.class);
                        getActivity().startActivity(intent);
                        Matcha.finishAllActivities();
                    }
                })
                .setMessage("确定登出吗？")
                .show();

    }

    @OnClick(R.id.txt_nickname)
    void onNickNameClick() {
        final EditText editText = new EditText(getContext());
        editText.setBackground(null);
        editText.setPadding(80, 80, 80, 80);
        editText.setMaxLines(1);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("请输入新的昵称")
                .setView(editText)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickName = editText.getText().toString();
                        if (TextUtils.isEmpty(nickName)) {
                            Matcha.showToast("昵称不能为空");
                        } else {
                            mPresenter.setNickName(nickName);
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.txt_phone)
    void onPhoneClick() {
        if (Objects.equals(mTxtPhone.getRightString(), "未设置")) {
            final EditText editText = new EditText(getContext());
            editText.setBackground(null);
            editText.setPadding(80, 80, 80, 80);
            editText.setMaxLines(1);
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setTitle("请输入手机号")
                    .setView(editText)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String phone = editText.getText().toString();
                            final boolean isPhone = RegexUtils.isMobileSimple(phone);
                            if (isPhone) {
                                mPresenter.setPhone(phone);
                            } else {
                                Matcha.showToast("你输入的手机号有误");
                            }
                        }
                    })
                    .show();
        }
    }


    @OnClick(R.id.txt_portrait)
    void onPortraitClick() {
        mPresenter.initCallback();
        startCameraWithCheck();
    }

    @OnClick(R.id.txt_pe_data)
    void onPeDataClick() {
        PEDataActivity.show(getContext());
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.onFirstInit();
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_personal_message;
    }

    public static PersonalFragment newInstance(Bundle bundle) {
        PersonalFragment personalFragment = new PersonalFragment();
        personalFragment.setArguments(bundle);
        return personalFragment;
    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public void initPortrait(String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(mImgPortrait);
        }
    }

    @Override
    public void initUserName(String userName) {
        mTxtName.setRightString(userName);
    }

    @Override
    public void initNickName(String nickName) {
        mTxtNickname.setRightString(nickName);
    }

    @Override
    public void initPhone(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            mTxtPhone.setRightString(phone);
            mTxtPhone.setRightTextColor(Color.BLACK);
        }
    }

    @Override
    public void setUserPortrait(Uri uri) {
        Glide.with(getContext())
                .load(uri)
                .apply(requestOptions)
                .into(mImgPortrait);
    }

    @Override
    public void updateNickName(String nickName) {
        mTxtNickname.setRightString(nickName);
    }

    @Override
    public void setPhoneSuccess(String phone) {
        mTxtPhone.setRightString(phone);
        mTxtPhone.setRightTextColor(Color.BLACK);
    }
}
