package cn.zjnu.matcha.fragments.communicate.chat;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.FragmentUtils;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.MessageActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupContract;
import cn.zjnu.matcha.factory.mvp.communicate.chat.ChatGroupPresenter;
import cn.zjnu.matcha.fragments.groupmember.GroupMemberFragment;
import cn.zjnu.matcha.fragments.panel.PanelFragment;
import cn.zjnu.matcha.widget.adapter.TextWatcherAdapter;

/**
 * Created by fsh on 2017/10/17.
 */

public class ChatGroupFragment extends PresenterFragment<ChatGroupContract.Presenter> implements ChatGroupContract.View {

    private long mGroupId;
    private AirPanel.Boss mPanelBoss;
    private PanelFragment mPanelFragment;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_group_name)
    AppCompatTextView mTxtGroupName;
    @BindView(R.id.edit_content)
    EditText mContent;
    @BindView(R.id.btn_submit)
    ImageView mSubmit;


    public static ChatGroupFragment newInstance(Bundle bundle) {
        ChatGroupFragment fragment = new ChatGroupFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.btn_face)
    void onFaceClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showFace();
    }

    @OnClick(R.id.btn_record)
    void onRecordClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showRecord();
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (mSubmit.isActivated()) {
            //发送
            String content = mContent.getText().toString();
            mContent.setText("");
            // TODO: 发送逻辑
        } else {
            onMoreClick();
        }
    }

    private void onMoreClick() {
        mPanelBoss.openPanel();
        mPanelFragment.showGallery();
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_chat_group;
    }

    @Override
    protected void initArgs(Bundle bundle) {
        Bundle mBundle = getArguments();
        mGroupId = mBundle.getLong(MessageActivity.KEY_RECEIVER_ID);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPresenter.getGroupInfo(mGroupId);
        initToolbar();
        initPanel(root);
        initEditContent();
        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);

    }

    private void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_setting);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO: 打开设置界面
                return true;
            }
        });
    }

    private void initPanel(View root) {
        mPanelBoss = (AirPanel.Boss) root.findViewById(R.id.lay_content);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                Util.hideKeyboard(mContent);
            }
        });
        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
            @Override
            public void onPanelStateChanged(boolean isOpen) {
                if (isOpen) {
                    //进行一些界面操作
                }
            }

            @Override
            public void onSoftKeyboardStateChanged(boolean isOpen) {
                if (isOpen) {
                    //进行一些界面操作
                }
            }
        });
    }

    //初始化输入框监听
    private void initEditContent() {
        mContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSendMsg = !TextUtils.isEmpty(content);
                //设置状态 改变对应的icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    @Override
    public void initHeader(GroupInfo group) {
        String groupName = group.getGroupName();
        mTxtGroupName.setText(groupName);
    }

    @Override
    protected ChatGroupContract.Presenter initPresenter() {
        return new ChatGroupPresenter(this);
    }

    @Override
    public boolean onBackPressed() {
        if (mPanelBoss.isOpen()) {
            //关闭面板并且返回true表示自己已经处理了返回消费
            mPanelBoss.closePanel();
            return true;
        }
        return super.onBackPressed();
    }
}
