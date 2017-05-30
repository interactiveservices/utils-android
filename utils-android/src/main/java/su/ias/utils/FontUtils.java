package su.ias.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Работа со шрифтами
 */

public class FontUtils {

    private final static Map<String, Typeface> fonts = new HashMap<>();

    /**
     * Настройка работы со знаком рубля в текущем тексте
     * @param textView textView, где нужно отобразить знак рубля
     * @param text текст
     */
    public static void setupRubleSign(@Nullable TextView textView, @Nullable String text) {
        if (textView == null || TextUtils.isEmpty(text)) {
            return;
        }
        htmlCompat(textView, text);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            textView.setTypeface(getFont(textView.getContext(), "Roboto-Regular.ttf"));
        }
    }

    public static void htmlCompat(@Nullable TextView textView, @Nullable String text) {
        if (textView != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                //noinspection deprecation
                textView.setText(Html.fromHtml(text));
            } else {
                textView.setText((Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)));
            }
        }
    }

    public static Typeface getFont(Context context, String fontName) {

        if (fonts.containsKey(fontName)) {
            return fonts.get(fontName);
        } else {
            Typeface myTypeface =
                    Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            fonts.put(fontName, myTypeface);
            return myTypeface;
        }
    }

}
