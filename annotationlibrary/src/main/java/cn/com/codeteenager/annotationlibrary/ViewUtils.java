package cn.com.codeteenager.annotationlibrary;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jiangshuaijie on 2017/12/1.
 */

public class ViewUtils {
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);

    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);

    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    //兼容上面三个方法
    public static void inject(ViewFinder finder, Object object) {
        injectField(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入方法
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //获取类中所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //获取OnClick的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    View view = finder.findViewById(viewId);
                    if (view != null) {
                        view.setOnClickListener(new DeclaredOnClickListener(method, object));
                    }
                }
            }
        }
        //findViewById找到view
        //设置监听器
        //反射执行方法

    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectField(ViewFinder finder, Object object) {
        //获取类中所有的属性
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        //获取BindView的value值
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                int viewId = bindView.value();
                //findViewById找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    //能够注入所有修饰符
                    field.setAccessible(true);
                    //动态的注入找到的View
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;

        public DeclaredOnClickListener(Method method, Object object) {
            this.mMethod = method;
            this.mObject = object;
        }

        @Override
        public void onClick(View v) {
            //点击调用该方法
            try {
                //所有方法都可以 包括公有私有
                mMethod.setAccessible(true);
                //反射执行方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
