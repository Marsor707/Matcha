package cn.zjnu.matcha.fragments.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.allen.library.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.AccountActivity;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.core.app.Matcha;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hu on 2017/10/17.
 */

public class PersonalFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_portrait)
    CircleImageView mImgPortrait;
    @BindView(R.id.txt_portrait)
    RelativeLayout mTxtPortrait;
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

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_personal_message;
    }

    public static PersonalFragment newInstance(Bundle bundle) {
        PersonalFragment personalFragment = new PersonalFragment();
        personalFragment.setArguments(bundle);
        return personalFragment;
    }
}
