package cn.zjnu.matcha.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class MarsViewFinderView extends ViewFinderView {

    public MarsViewFinderView(Context context) {
        this(context, null);
    }

    public MarsViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        mBorderPaint.setColor(Color.GREEN);
        mLaserPaint.setColor(Color.GREEN);
    }
}
