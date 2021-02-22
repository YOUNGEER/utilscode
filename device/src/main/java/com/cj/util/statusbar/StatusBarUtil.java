package com.cj.util.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static com.cj.util.statusbar.StatusBarFontHelper.FLYME;
import static com.cj.util.statusbar.StatusBarFontHelper.MIUI;

/**
 * @author：created by leaf on 2019-05-07
 * Github地址：https://github.com/Ye-Miao
 * Desc: 状态栏工具类
 */
public class StatusBarUtil {

    private static final int DEFAULT_ALPHA = 0;


    //****************************设置状态栏开始******************************************************************

    /**
     * 设置状态栏darkMode,字体颜色及icon变黑(目前支持MIUI6以上,Flyme4以上,Android M以上)
     * 有效
     *
     * @param activity 目标activity
     */
    public static void setDarkMode(@NonNull Activity activity) {
        darkMode(activity, true);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void darkMode(Activity activity, boolean dark) {
        if (StatusBarFontHelper.setStatusBarMode(activity, dark) == FLYME) {
            new MIUIHelper().setStatusBarLightMode(activity, dark);
        } else if (StatusBarFontHelper.setStatusBarMode(activity, dark) == MIUI) {
            new FlymeHelper().setStatusBarLightMode(activity, dark);
        }
        darkModeForM(activity.getWindow(), dark);
    }

    /**
     * android 6.0设置字体颜色
     *
     * @param window 目标window
     * @param dark   亮色 or 暗色
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private static void darkModeForM(Window window, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            if (dark) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    //****************************设置状态栏结束******************************************************************


    //****************************浸入式状态栏 开始******************************************************************

    /**
     * 浸入式状态栏实现同时取消5.0以上的阴影,6.0和flyme，miui可以设置状态栏字体颜色
     * <p>
     * ⚠️，不需要进行单独的style配置，xml布局中，如果fragment不需要侵入式的，根布局可以设置
     * android:fitsSystemWindows="true"
     */
    public static void setSinkStatusBar(Activity activity, Boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        darkMode(activity, isDark);
    }


    //****************************浸入式状态栏 结束******************************************************************


    /**
     * 获取状态栏高度
     *
     * @param context 目标Context
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
