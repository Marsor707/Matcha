package cn.zjnu.matcha.fragments.zone;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zjnu.matcha.R;
import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.core.utils.qrcode.QRCode;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoneFragment extends BaseFragment {

    @BindView(R.id.edit_content)
    EditText mEditContent;
    @BindView(R.id.img_qr)
    ImageView mImgQR;

    @OnClick(R.id.btn_submit)
    void onClick() {
        String edit = mEditContent.getText().toString();
        Bitmap bitmap = QRCode.createQRCode(edit);
        mImgQR.setImageBitmap(bitmap);
    }

    @Override
    protected Object getContentLayoutId() {
        return R.layout.fragment_zone;
    }

    public static ZoneFragment newInstance(Bundle bundle) {
        ZoneFragment fragment = new ZoneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
