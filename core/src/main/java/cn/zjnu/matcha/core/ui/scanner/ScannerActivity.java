package cn.zjnu.matcha.core.ui.scanner;

import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;
import cn.zjnu.matcha.core.utils.callback.IGlobalCallback;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ScannerActivity extends BaseActivity implements ZBarScannerView.ResultHandler {

    private ScanView mScanView = null;

    @Override
    protected void initWindows() {
        super.initWindows();
        if (mScanView == null) {
            mScanView = new ScanView(this);
        }
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    protected Object getContentLayoutId() {
        return mScanView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView != null) {
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView != null) {
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked")
        final IGlobalCallback<String> callback =
                CallbackManager.getInstance().getCallback(CallbackTypes.ON_SCAN);
        if (callback != null) {
            callback.executeCallback(result.getContents());
        }
        finish();
    }
}
