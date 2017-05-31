package su.ias.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * для частых работ с UI, таких как показать/скрыть клаву
 * изменить цвет тулбара и т.д.
 */

public final class UiUtils {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param context Context to get resources and device specific display metrics
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param context Context to get resources and device specific display metrics
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float pixelsToDp(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
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
