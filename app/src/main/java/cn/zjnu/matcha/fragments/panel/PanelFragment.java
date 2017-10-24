package cn.zjnu.matcha.fragments.panel;


import android.view.View;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;

/**
 * panel初始化在这里
 */
public class PanelFragment extends BaseFragment {

    private View mFacePanel, mGalleryPanel, mRecordPanel;

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_panel;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initFace(root);
        initGallery(root);
        initRecord(root);
    }

    //初始化表情
    private void initFace(View root) {
        final View facePanel = mFacePanel = root.findViewById(R.id.lay_panel_face);
    }

    //初始化图片
    private void initGallery(View root) {
        final View galleryPanel = mGalleryPanel = root.findViewById(R.id.lay_panel_gallery);
    }

    //初始化语音
    private void initRecord(View root) {
        final View recordPanel = mRecordPanel = root.findViewById(R.id.lay_panel_record);
    }

    public void showFace() {
        mGalleryPanel.setVisibility(View.GONE);
        mRecordPanel.setVisibility(View.GONE);
        mFacePanel.setVisibility(View.VISIBLE);
    }

    public void showRecord() {
        mGalleryPanel.setVisibility(View.GONE);
        mRecordPanel.setVisibility(View.VISIBLE);
        mFacePanel.setVisibility(View.GONE);
    }

    public void showGallery() {
        mGalleryPanel.setVisibility(View.VISIBLE);
        mRecordPanel.setVisibility(View.GONE);
        mFacePanel.setVisibility(View.GONE);
    }
}
