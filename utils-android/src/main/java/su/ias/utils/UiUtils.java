package su.ias.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * для частых работ с UI, таких как показать/скрыть клаву
 * изменить цвет тулбара и т.д.
 */

public final class UiUtils {

    public UiUtils() {
        throw new AssertionError();
    }

    /**
     * можем изменять цвет статус бара сверху (нужно там где его делаю прозрачным)
     *
     * @param colorId - собственно цвет
     */
    public static void setStatusBarColor(@Nullable Activity activity, @ColorRes int colorId) {
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, colorId));
        }
    }

    /**
     * прячет клавиатуру
     */
    public static void hideKeyboard(@Nullable Activity activity) {
        if (activity != null) {
            InputMethodManager imm =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = activity.getCurrentFocus();
            if (focusedView != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            } else {
                imm.hideSoftInputFromWindow((new View(activity)).getWindowToken(), 0);
            }
        }
    }

    /**
     * Прячет клавиатуру, когда клавиатура сфокусирована на каком-то элементе View
     *
     * @param view
     */
    public static void hideKeyboard(@Nullable View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * показать клавиатуру
     *
     * @param context - контекст
     * @param view
     */
    public static void showKeyboard(@Nullable Context context, @Nullable View view) {
        if (context != null && view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
                                                         InputMethodManager.SHOW_FORCED,
                                                         0);
        }
    }

    /**
     * WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE for example
     *
     * @param activity активность, которому меняем работу клавиатуры
     * @param mode     каким образом активность работает с клавиатурой
     */
    public static void setSoftInputMode(@Nullable Activity activity, int mode) {
        if (activity != null) {
            activity.getWindow().setSoftInputMode(mode);
        }
    }
}
