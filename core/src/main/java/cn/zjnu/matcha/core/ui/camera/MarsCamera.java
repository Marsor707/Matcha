package cn.zjnu.matcha.core.ui.camera;

import android.net.Uri;

import cn.zjnu.matcha.core.app.BaseFragment;
import cn.zjnu.matcha.core.utils.file.FileUtil;


/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

//相机调用类
public class MarsCamera {

    public static Uri createCropFile() {
        return Uri.parse(
                FileUtil.createFile("crop_image", FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(BaseFragment fragment) {
        new CameraHandler(fragment).beginCameraDialog();
    }
}
