package su.ias.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created on 9/8/17.
 * utils to validate email, card, account, etc
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ValidateUtils {

    /**
     * Validate email address
     *
     * @param emailAddress email to validate
     * @return true if email address valid
     */
    public static boolean validateEmail(CharSequence emailAddress) {
        return !TextUtils.isEmpty(emailAddress) && android.util.Patterns.EMAIL_ADDRESS.matcher(
                emailAddress).matches();
    }

    /**
     * algorithm Luhn
     *
     * @param cardNumber card number
     * @return true if card valid
     */
    public static boolean validateCardNumber(String cardNumber) {

        if (TextUtils.isEmpty(cardNumber)) {
            return false;
        } else {
            cardNumber = cardNumber.replaceAll("\\D", "");
        }

        int len = cardNumber.length() - 1;
        if (len < 15) {
            return false;
        }
        int sum = 0;
        int value;
        StringBuilder sb = new StringBuilder(cardNumber.substring(0, len));
        for (int i = len - 1; i >= 0; i -= 2) {
            sb.replace(i, i + 1, String.valueOf((sb.charAt(i) - '0') * 2));
        }
        for (int i = 0; i < sb.length(); i++) {
            sum += sb.charAt(i) - '0';
        }
        if (sum % 10 == 0) {
            value = 0;
        } else {
            value = (sum / 10 + 1) * 10 - sum;
        }

        return value == cardNumber.charAt(len) - '0';
    }

    /**
     * Validate Card expire date
     * need string like 11/11
     * @param date
     * @return
     */
    public static boolean validateCardExpireDate(String date) {
        if (date.length() == 5) {
            DateFormat df = new SimpleDateFormat("MM/yy", Locale.getDefault());
            try {
                Calendar checkDate = Calendar.getInstance();
                checkDate.setTime(df.parse(date.toString()));
                Calendar now = Calendar.getInstance();
                if (checkDate.get(Calendar.YEAR) < now.get(Calendar.YEAR)) {
                    return false;
                }
                if (checkDate.get(Calendar.YEAR) == now.get(Calendar.YEAR) && checkDate.get(Calendar.MONTH) >= now
                        .get(Calendar.MONTH)) {
                    return true;
                }
                if (checkDate.get(Calendar.YEAR) > now.get(Calendar.YEAR)) {
                    return true;
                }
                return false;
            } catch (ParseException e) {

            }
        }
        return false;
    }

}
