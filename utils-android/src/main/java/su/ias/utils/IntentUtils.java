package su.ias.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

public class IntentUtils {

    /**
     * открыть страницу приложения в плеймаркете
     *
     * @param context - Контекст
     */
    public static void openPlayMarket(Context context) {
        final String appPackageName =
                context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                             Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                             Uri.parse(
                                                     "https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * сделать вызов по телефону
     * ВЖАНО ПОМНИТЬ ПРО РАЗРЕШЕНИЯ!
     *
     * @param context     Контекст
     * @param phoneNumber номер телефона
     */
    @SuppressWarnings("MissingPermission")
    public static void call(Context context, @Nullable String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.error_dont_have_call_app, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    /**
     * открыть звонилку с набранным телефонным номером
     *
     * @param context     Контекст
     * @param phoneNumber номер телефона
     */
    public static void dial(Context context, @Nullable String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.error_dont_have_call_app, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    /**
     * Открыть ссылку в браузере
     *
     * @param context   Конеткст
     * @param launchURL Ссылка которую нужно открыть
     */
    public static void openWebPage(Context context, String launchURL) {
        Uri webpage = Uri.parse(launchURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.error_dont_have_needed_app, Toast.LENGTH_LONG).show();
        }
    }
}
