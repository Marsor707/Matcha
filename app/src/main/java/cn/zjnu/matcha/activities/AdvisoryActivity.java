package cn.zjnu.matcha.activities;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseActivity;
import cn.zjnu.matcha.fragments.advisory.AdvisoryFragment;
import cn.zjnu.matcha.interfaze.IKeySend;

public class AdvisoryActivity extends BaseActivity {
    private IKeySend mSendListener;

    public static void show(Context context) {
        Intent intent = new Intent(context, AdvisoryActivity.class);
        context.startActivity(intent);
    }

    public void setSendListener(IKeySend mSendListener) {
        this.mSendListener = mSendListener;
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.activity_advisory;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new AdvisoryFragment())
                .commit();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (mSendListener != null) {
                mSendListener.onSendMessage();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
