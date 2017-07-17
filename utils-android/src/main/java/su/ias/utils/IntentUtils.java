package su.ias.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

@SuppressWarnings("unused")
public class IntentUtils {

    public IntentUtils() {
        throw new AssertionError();
    }

    /**
     * Открыть страницу приложения в GooglePlay
     *
     * @param context
     */
    public static void openAppInMarket(Context context) {
        final String appPackageName = BuildConfig.APPLICATION_ID;
        openAppInMarket(context, appPackageName);
    }

    /**
     * Открыть страницу заданного приложения в Google Play
     *
     * @param context        контекст
     * @param appPackageName application id необходимого приложения
     */
    public static void openAppInMarket(Context context, @Nullable String appPackageName) {
        if (!TextUtils.isEmpty(appPackageName)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                                           Uri.parse("market://details?id=" + appPackageName));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException anfe) {
                openUrl(context, "https://play.google.com/store/apps/details?id=" + appPackageName);
            }
        }
    }

    /**
     * сделать вызов по телефону
     * необходимо разрешение на звонки на Android 6+!
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
                Toast.makeText(context, R.string.error_dont_have_call_app, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * открыть звонилку с набранным телефонным номером
     *
     * @param context       Контекст
     * @param phoneNumberId id строки с номером телефона
     */
    public static void dial(Context context, @StringRes int phoneNumberId) {
        try {
            String phoneNumber = context.getString(phoneNumberId);
            dial(context, phoneNumber);
        } catch (Resources.NotFoundException e) {
            return;
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
                Toast.makeText(context, R.string.error_dont_have_call_app, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Открыть адрес для просмотра. При неудаче просмотра первого адреса просматривается второй
     *
     * @param context контекст
     * @param urlId   id строки первого адреса
     * @param urlId2  id строки второго адреса
     */
    public static void openUrl(final Context context,
                               @StringRes final int urlId,
                               @StringRes final int urlId2) {
        try {
            final String url = context.getString(urlId);
            final String url2 = context.getString(urlId2);
            openUrl(context, url, new IntentNotFoundCallback() {
                @Override
                public void onNotFound() {
                    openUrl(context, url2);
                }
            });
        } catch (Resources.NotFoundException e) {
            return;
        }
    }

    /**
     * Открыть адрес для просмотра
     *
     * @param context контекст
     * @param urlId   id строки с адресом для просмотра
     */
    public static void openUrl(Context context, @StringRes int urlId) {
        try {
            String url = context.getString(urlId);
            openUrl(context, url);
        } catch (Resources.NotFoundException e) {
            return;
        }
    }

    /**
     * Открыть адрес для просмотра
     *
     * @param context контекст
     * @param url     адрес для просмотра
     */
    public static void openUrl(@Nullable Context context, @Nullable String url) {
        openUrl(context, url, null);
    }

    /**
     * Открыть адрес для просмотра
     *
     * @param context  контекст
     * @param url      адрес для просмотра
     * @param callback коллбек при ненахождении приложения для просмотра адреса
     */
    public static void openUrl(@Nullable Context context,
                               @Nullable String url,
                               @Nullable IntentNotFoundCallback callback) {
        if (context != null && !TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                if (callback != null) {
                    callback.onNotFound();
                } else {
                    Toast.makeText(context, R.string.error_dont_have_needed_app, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    /**
     * Отправить email
     *
     * @param context   контекст
     * @param addresses адреса эл. почты отправителей
     * @param subject   тема письма
     * @param text      текст
     */
    public static void sendEmail(@Nullable Context context,
                                 @Nullable String[] addresses,
                                 @Nullable String subject,
                                 @Nullable CharSequence text) {
        if (context != null) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (addresses != null && addresses.length > 0) {
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            }
            if (!TextUtils.isEmpty(subject)) {
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            }
            if (!TextUtils.isEmpty(text)) {
                intent.putExtra(Intent.EXTRA_TEXT, text);
            }
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.error_dont_have_email_app, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Отправить СМС
     *
     * @param context   контекст
     * @param smsNumber номер, на который отправляется СМС
     * @param smsText   текст СМС
     */
    public static void sendSms(Context context,
                               @Nullable String smsNumber,
                               @Nullable String smsText) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(smsNumber) ? "" : smsNumber));
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(smsText)) {
            intent.putExtra("sms_body", smsText);
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, R.string.error_dont_have_sms_app, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Попытка позвонить по скайпу с заданным skype ID.
     * Если приложения нет, вызывается страница установки приложения Skype.
     *
     * @param context контекст
     * @param skypeId идентификатор пользователя в Skype
     */
    public static void callSkype(Context context, @Nullable String skypeId) {
        if (!TextUtils.isEmpty(skypeId)) {
            boolean installed;
            PackageManager myPackageMgr = context.getPackageManager();
            String skypePackage = "com.skype.raider";
            try {
                myPackageMgr.getPackageInfo(skypePackage, PackageManager.GET_ACTIVITIES);
                installed = true;
            } catch (PackageManager.NameNotFoundException e) {
                installed = false;
            }
            if (installed) {
                Uri skypeUri = Uri.parse("skype:" + skypeId + "?call");
                Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
                myIntent.setComponent(new ComponentName(skypePackage, skypePackage + ".Main"));
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);
            } else {
                openAppInMarket(context, skypePackage);
            }
        }
    }

    /**
     * Открыть настройки приложения (экрен "Информация о приложении")
     * @param context контекст
     */
    public static void openAppDetailsActivity(final Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (i.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(i);
        } else {
            Toast.makeText(context, R.string.error_cant_open_app_settings, Toast.LENGTH_SHORT).show();
        }
    }

    public interface IntentNotFoundCallback {
        void onNotFound();
    }
}
