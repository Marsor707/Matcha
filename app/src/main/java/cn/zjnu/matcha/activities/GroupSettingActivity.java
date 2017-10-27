package cn.zjnu.matcha.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.PresenterActivity;
import cn.zjnu.matcha.core.utils.qrcode.QRCode;
import cn.zjnu.matcha.factory.mvp.communicate.group.GroupSettingContract;
import cn.zjnu.matcha.factory.mvp.communicate.group.GroupSettingPresenter;


/**
 * Created by fsh on 2017/10/23.
 */

public class GroupSettingActivity extends PresenterActivity<GroupSettingContract.Presenter> implements GroupSettingContract.View {

    public static final String GROUP_ID = "GROUP_ID";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_group_member)
    LinearLayout mLinear;
    @BindView(R.id.btn_quit_group)
    AppCompatButton mBtnQuitGroup;
    private long mGroupId;

    @OnClick(R.id.btn_quit_group)
    void onClickQuitGroup() {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("确定要退出该群吗?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.quitGroup(mGroupId);
                    }
                })
                .show();
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_group_setting;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mGroupId = bundle.getLong(GROUP_ID);
        return super.initArgs(bundle);
    }

    @OnClick(R.id.img_qrcode)
    void onQRClick() {
        final Dialog dialog = new AlertDialog.Builder(this).create();
        Bitmap qrcode = QRCode.createQRCode(String.valueOf(mGroupId));
        dialog.show();
        final Window window = dialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_qrcode);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            ((ImageView) window.findViewById(R.id.img_qrcode)).setImageBitmap(qrcode);
        }
    }


    public static void show(Context context, long mGroupId) {
        Intent intent = new Intent(context, GroupSettingActivity.class);
        intent.putExtra(GROUP_ID, mGroupId);
        context.startActivity(intent);
    }

    @Override
    protected GroupSettingContract.Presenter initPresenter() {
        return new GroupSettingPresenter(this);
    }

    @Override
    public void quit() {
        finish();
    }
}
