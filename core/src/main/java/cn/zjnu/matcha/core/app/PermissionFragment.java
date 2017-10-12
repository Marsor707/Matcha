package cn.zjnu.matcha.core.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import cn.zjnu.matcha.core.ui.camera.CameraImageBean;
import cn.zjnu.matcha.core.ui.camera.MarsCamera;
import cn.zjnu.matcha.core.ui.camera.RequestCodes;
import cn.zjnu.matcha.core.ui.scanner.ScannerActivity;
import cn.zjnu.matcha.core.utils.callback.CallbackManager;
import cn.zjnu.matcha.core.utils.callback.CallbackTypes;
import cn.zjnu.matcha.core.utils.callback.IGlobalCallback;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

@RuntimePermissions
public abstract class PermissionFragment extends BaseFragment {

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void startCamera() {
        MarsCamera.start(this);
    }

    public void startCameraWithCheck() {
        PermissionFragmentPermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(Context context) {
        Intent intent = new Intent(context, ScannerActivity.class);
        context.startActivity(intent);
    }

    public void startScanWithCheck(Context context) {
        PermissionFragmentPermissionsDispatcher.startScanWithPermissionCheck(this, context);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = MarsCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked")
                    final IGlobalCallback<Uri> callback =
                            CallbackManager.getInstance().getCallback(CallbackTypes.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
