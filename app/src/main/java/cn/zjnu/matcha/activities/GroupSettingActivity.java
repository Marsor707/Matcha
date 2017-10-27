package cn.zjnu.matcha.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.model.Conversation;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;


/**
 * Created by fsh on 2017/10/23.
 */

public class GroupSettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_group_member)
    LinearLayout mLinear;
    @BindView(R.id.iv_qrcode)
    ImageView mQrcode;
    private final AlertDialog DIALOG = null;
    private Conversation mConversation;
    private long mGroupId;

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

    @OnClick(R.id.toolbar)
    void toQRCode() {
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_qrcode);
//        GroupInfo groupInfo = (GroupInfo) mConversation.getTargetInfo();
//        mGroupId = groupInfo.getGroupID();
//        Bitmap qrcode = QRCode.createQRCode(String.valueOf(mGroupId));
//        final Window window = DIALOG.getWindow();
//        if (window != null) {
//            window.setContentView(R.layout.dialog_qrcode);
//            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
//            window.findViewById(R.id.iv_qrcode).(qrcode);
//        }
//        DIALOG.show();
    }


    public static void show(Context context) {
        Intent intent = new Intent(context, GroupSettingActivity.class);
        context.startActivity(intent);
    }


}
