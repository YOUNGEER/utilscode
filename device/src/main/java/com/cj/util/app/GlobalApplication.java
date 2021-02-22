package com.cj.util.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * 2017/9/1.
 * <p>
 * 全局Application
 */

public class GlobalApplication extends Application {
    protected static Context context;
    protected static Application application;
    protected static Handler handler;
    protected static int mainThreadId;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();

        PreferencesUtil.Companion.get(this);

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }


    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Application getApplication() {
        return application;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }
}
