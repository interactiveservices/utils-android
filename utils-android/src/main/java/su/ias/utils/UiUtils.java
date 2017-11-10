package su.ias.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.InvocationTargetException;

/**
 * для частых работ с UI, таких как показать/скрыть клаву
 * изменить цвет тулбара и т.д.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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
     * @param view    - view в фокусе, requestFocus которой запрашивается
     */
    public static void showKeyboard(@Nullable Context context, @Nullable View view) {
        if (context != null) {

            if (view == null && context instanceof Activity) {
                view = ((Activity) context).getCurrentFocus();
            }

            if (view == null) {
                return;
            }

            view.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
                                                         InputMethodManager.SHOW_FORCED,
                                                         0);
        }
    }

    /**
     * может и пригодится кому-то
     */
    public static void toggleKeyboard(@NonNull Context context) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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

    public static boolean isNavBarExists(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        return appUsableSize.y < realScreenSize.y;
    }

    /**
     * get used application screen size
     *
     * @param context app context
     * @return application screen size
     */
    public static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * get full screen size
     *
     * @param context app context
     * @return real screen size
     */
    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 15) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }

        return size;
    }

    /**
     * Fetches accent color value of app (colorAccent in app's theme)
     *
     * @param context context to get color (context of activity or app)
     * @return color value
     */
    @ColorInt
    public static int fetchAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a =
                context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, ContextCompat.getColor(context, android.R.color.white));
        a.recycle();
        return color;
    }

    /**
     * Fetches primary color value of app (colorPrimary in app's theme)
     *
     * @param context context to get color (context of activity or app)
     * @return color value
     */
    @ColorInt
    public static int fetchPrimaryColor(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a =
                context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, ContextCompat.getColor(context, android.R.color.holo_blue_light));

        a.recycle();

        return color;
    }

    /**
     * Fetches text appearance
     *
     * @param context context to get text appearance (textAppearanceMedium) (context of activity or app)
     * @return textAppearance
     */
    @StyleRes
    public static int fetchTextAppearance(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                                                      new int[]{android.R.attr.textAppearanceMedium});

        int textAppearance = a.getResourceId(0, 0);
        a.recycle();

        return textAppearance;
    }
}
