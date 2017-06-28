package su.ias.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtils {

    public static final String RUBLE_SIGN = "&#8381;";
    public static final String NBSP = "&nbsp;";

    public MoneyUtils() {
        throw new AssertionError();
    }

    /**
     * Отображает стоимость в текстовом поле вида "1 400 ₽".
     * Метод делает всю работу по отображению знака рубля и приведению числа к нужному формату
     *
     * @param textView TextView, где будет отображаться текст
     * @param cost     стоимость, которая будет форматироваться
     */
    public static void inflateTextViewWithCost(@Nullable TextView textView, @Nullable Double cost) {
        FontUtils.setupRubleSign(textView, MoneyUtils.formatRubles(cost));
    }

    /**
     * Формирует строку - unicode вида "1 400 ₽". У цены отбрасывается дробная часть.
     * Строку потом необходимо обрабатывать FontUtils.setupRubleSign для корректного отображения знака рубля
     *
     * @param cost стоимость
     * @return
     */
    @Nullable
    public static String formatRubles(@Nullable Double cost) {
        int intCost = 0;
        if (cost != null) {
            intCost = cost.intValue();
        } else {
            return null;
        }
        String formattedCost = formatNumber(intCost);
        return formattedCost + NBSP + RUBLE_SIGN;
    }

    /**
     * Форматирует число. Делает из числа 1000000 строку "1 000 000"
     *
     * @param cost число, которое форматируется
     */
    public static String formatNumber(@NonNull Integer cost) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        // возвращаем форматированную строку с разделителем - неразрывным пробелом
        return formatter.format(cost).replace(" ", NBSP);
    }
}
