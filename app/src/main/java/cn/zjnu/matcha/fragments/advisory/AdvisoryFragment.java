package cn.zjnu.matcha.fragments.advisory;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.activities.AdvisoryActivity;
import cn.zjnu.matcha.core.app.PresenterFragment;
import cn.zjnu.matcha.core.ui.recycler.DataConvert;
import cn.zjnu.matcha.factory.model.advisory.LeaveMessageModel;
import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;
import cn.zjnu.matcha.factory.mvp.advisory.specialist.SpecialistContract;
import cn.zjnu.matcha.factory.mvp.advisory.specialist.SpecialistPresenter;
import cn.zjnu.matcha.fragments.advisory.adpater.AdvisoryAdapter;
import cn.zjnu.matcha.fragments.advisory.adpater.MsgDataConvert;
import cn.zjnu.matcha.fragments.advisory.adpater.SpecialDataConvert;
import cn.zjnu.matcha.interfaze.IKeySend;

/**
 * Created by Hu on 2017/11/7.
 */

public class AdvisoryFragment extends PresenterFragment<SpecialistContract.Presenter>
        implements SpecialistContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private AdvisoryAdapter mAdapter;
    private AirPanel.Boss mPanelBoss;
    private long mUserId = -1;
    @BindView(R.id.lin_msg_bar)
    LinearLayoutCompat mMsgBar;
    @BindView(R.id.edit_content)
    AppCompatEditText mContent;
    @BindView(R.id.rv_advisory)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private IKeySend mSendListener = new IKeySend() {
        @Override
        public void onSendMessage() {
            String content = mContent.getText().toString();
            if (!content.equals("")) {
                mContent.setText("");
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AdvisoryActivity) getActivity()).setSendListener(mSendListener);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getSpecialist();
        final UserInfo userInfo = JMessageClient.getMyInfo();
        if (userInfo != null) {
            mUserId = userInfo.getUserID();
        }
    }

    @Override
    public void getSpecialistSuccess(String response) {
        final DataConvert convert = new SpecialDataConvert();
        mAdapter = AdvisoryAdapter.create(convert.setJsonData(response));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void getMsgSuccess(String response) {
        final MsgDataConvert convert = new MsgDataConvert();
        final List<LeaveMessageModel> messageModels = convert.convert();

    }

    @Override
    protected SpecialistContract.Presenter initPresenter() {
        return new SpecialistPresenter(this);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_advisory;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initToolbar();
        initRecyclerView();
        initPanel(root);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mMsgBar.setVisibility(View.VISIBLE);
        final SpecialistModel model = (SpecialistModel) adapter.getData().get(position);
        final String specialId = model.getExpertId();
        mPresenter.getMsg(mUserId, specialId);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        hidePanelAndKeyBoard(view);
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hidePanelAndKeyBoard(recyclerView);
            }
        });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (v instanceof RecyclerView) {
                        hidePanelAndKeyBoard(v);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanelBoss.closePanel();
                mMsgBar.setVisibility(View.GONE);
                getActivity().finish();
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
//        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
//            @Override
//            public void onPanelStateChanged(boolean isOpen) {
//                if (isOpen) {
//                    //进行一些界面操作
//
//                }
//            }
//
//            @Override
//            public void onSoftKeyboardStateChanged(boolean isOpen) {
//                if (isOpen) {
//                    //进行一些界面操作
//
//                }
//            }
//        });
    }

    @Override
    public boolean onBackPressed() {
        if (mPanelBoss.isOpen()) {
            //关闭面板并且返回true表示自己已经处理了返回消费
            mPanelBoss.closePanel();
            mMsgBar.setVisibility(View.GONE);
            return true;
        }
        return super.onBackPressed();
    }

    private void hidePanelAndKeyBoard(View view) {
        if (mPanelBoss.isOpen()) {
            mPanelBoss.closePanel();
        }
        Util.hideKeyboard(view);
        mMsgBar.setVisibility(View.GONE);
    }

}
