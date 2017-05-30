package su.ias.utils;

import android.content.Context;
import android.support.annotation.StringRes;

public class GrammarUtils {

    /**
     * Выбирает нужное склонение существительного, в зависимости от числа
     * Например: 1 фотография, но 2 фотографии
     * <b>актуально для русского языка</b>
     *
     * @param number           число, в зависимости от которого происходит склонение
     * @param idSingular       единственное число - с числами 1, и с числами, оканчивающимися на 1, кроме 11 (1 фотография)
     * @param idPlural         вариант склонения с числами 2, 3, 4 и числами, оканчивающимися на 2, 3, 4, кроме 12, 13, 14. (33 фотографии)
     * @param idAccusativeCase вариант склонения с числами 0, 5-19, и окончанием на 5-9. (0 фотографий)
     * @return идентификатор строки в верном склонении
     */
    private static int getCaseIdByNumber(int number,
                                        @StringRes int idSingular,
                                        @StringRes int idPlural,
                                        @StringRes int idAccusativeCase) {
        int mod10 = number % 10;
        int mod100 = number % 100;
        if (1 == mod10 && 11 != mod100) {
            return idSingular;
        } else if ((2 == mod10 || 3 == mod10 || 4 == mod10) && 12 != mod100 && 13 != mod100 && 14 != mod100) {
            return idPlural;
        } else {
            return idAccusativeCase;
        }
    }

    /**
     * Возвращает строку с нужным склонением существительного, в зависимости от числа
     * Например: 1 фотография, но 2 фотографии
     * <b>актуально для русского языка</b>
     *
     * @param number           число, в зависимости от которого происходит склонение
     * @param idSingular       единственное число - с числами 1, и с числами, оканчивающимися на 1, кроме 11 (1 фотография)
     * @param idPlural         вариант склонения с числами 2, 3, 4 и числами, оканчивающимися на 2, 3, 4, кроме 12, 13, 14. (33 фотографии)
     * @param idAccusativeCase вариант склонения с числами 0, 5-19, и окончанием на 5-9. (0 фотографий)
     * @return идентификатор строки в верном склонении
     */
    public static String getCaseByNumber(Context context,
                                           int number,
                                           @StringRes int idSingular,
                                           @StringRes int idPlural,
                                           @StringRes int idAccusativeCase) {
        String caseString = context.getString(getCaseIdByNumber(number, idSingular, idPlural, idAccusativeCase));
        return context.getString(R.string.format_concat_number_string, number, caseString);
    }

    public static String getPhotoCaseByNumber(Context context, int number) {
        return getCaseByNumber(context,
                                 number,
                                 R.string.photo_singular,
                                 R.string.photo_plural,
                                 R.string.photo_accusative);
    }

    public static String getMonthCaseByNumber(Context context, int number) {
        return getCaseByNumber(context,
                                 number,
                                 R.string.month_singular,
                                 R.string.month_plural,
                                 R.string.month_accusative);
    }

    public static String getDayCaseByNumber(Context context, int number) {
        return getCaseByNumber(context,
                                 number,
                                 R.string.day_singular,
                                 R.string.day_plural,
                                 R.string.day_accusative);
    }

    public static String getHourCaseByNumber(Context context, int number) {
        return getCaseByNumber(context,
                                 number,
                                 R.string.hour_singular,
                                 R.string.hour_plural,
                                 R.string.hour_accusative);
    }

    public static String getMinuteCaseByNumber(Context context, int number) {
        return getCaseByNumber(context,
                                 number,
                                 R.string.minute_singular,
                                 R.string.minute_plural,
                                 R.string.minute_accusative);
    }

    public static String getSecondCaseByNumber(Context context, int number) {
        return getCaseByNumber(context,
                                 number,
                                 R.string.second_singular,
                                 R.string.second_plural,
                                 R.string.second_accusative);
    }

}
