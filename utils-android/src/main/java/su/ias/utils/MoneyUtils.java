package su.ias.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
        FontUtils.setupRubleSign(textView, MoneyUtils.formatRublesNoRound(cost));
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
        if (cost == null) {
            return null;
        }
        cost = (double) cost.intValue();
        return formatNumber(cost) + NBSP + RUBLE_SIGN;
    }

    /**
     * Формирует строку - unicode вида "{строка} ₽".
     * Строку потом необходимо обрабатывать FontUtils.setupRubleSign для корректного отображения знака рубля
     *
     * @param cost строка, к которой нужно приставить знак рубля
     * @return
     */
    @Nullable
    public static String formatRubles(@Nullable String cost) {
        if (TextUtils.isEmpty(cost)) {
            return null;
        }
        return cost + NBSP + RUBLE_SIGN;
    }

    /**
     * Форматирует число. Делает из числа 1000000 строку "1 000 000"
     *
     * @param cost число, которое форматируется
     */
    public static String formatNumber(@NonNull Double cost) {

        DecimalFormat myFormatter = new DecimalFormat("#,###.00");
        if (cost % 1 == 0) {
            myFormatter = new DecimalFormat("#,###");
        }
        DecimalFormatSymbols formatSymbols = DecimalFormatSymbols.getInstance();
        formatSymbols.setDecimalSeparator(',');
        formatSymbols.setGroupingSeparator(' ');
        formatSymbols.setMinusSign('-');
        myFormatter.setDecimalFormatSymbols(formatSymbols);
        myFormatter.setNegativePrefix("- ");

        return myFormatter.format(cost).replace(" ", NBSP);

    }

    /**
     * Формирует строку - unicode вида "- 1 400.10 ₽" или "1 400 ₽".
     * Строку потом необходимо обрабатывать FontUtils.setupRubleSign для корректного отображения знака рубля
     *
     * @param cost стоимость
     * @return строка вида "- 1 400.10 ₽" или null
     */
    @Nullable
    public static String formatRublesNoRound(@Nullable Double cost) {

        if (cost == null) {
            return null;
        }

        return formatNumber(cost) + NBSP + RUBLE_SIGN;
    }
}
