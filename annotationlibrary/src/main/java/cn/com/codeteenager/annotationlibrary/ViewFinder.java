package cn.com.codeteenager.annotationlibrary;

import android.app.Activity;
import android.view.View;

/**
 * Created by jiangshuaijie on 2017/12/1.
 *
 * @description ：View的findViewById的辅助类
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
