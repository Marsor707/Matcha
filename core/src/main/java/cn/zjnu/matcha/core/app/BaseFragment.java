package cn.zjnu.matcha.core.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public abstract class BaseFragment extends Fragment {
    protected View mRoot;
    protected Unbinder mRootUnBinder;
    protected boolean mIsFirstInitData = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            View root = null;
            if (getContentLayoutId() instanceof Integer) {
                int layId = (int) getContentLayoutId();
                //初始化当前根布局，但是不再创建是就添加进container中去
                root = inflater.inflate(layId, container, false);
            } else if (getContentLayoutId() instanceof View) {
                root = (View) getContentLayoutId();
            }
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                //把当前root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsFirstInitData) {
            //触发一次后就不会触发
            mIsFirstInitData = false;
            onFirstInit();
        }
        //当view创建完后初始化数据
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRootUnBinder.unbind();
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * 得到当前界面的资源文件ID
     * 子类必须复写
     *
     * @return 资源文件ID
     */
    protected abstract Object getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(View root) {
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 当首次初始化数据时会调用
     */
    protected void onFirstInit() {

    }

    /**
     * 返回按键触发时调用
     *
     * @return 返回true代表我已处理逻辑，Activity不用自己finish
     * 返回false代表我没有处理逻辑，Activity走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }
}
