package su.ias.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.media.ExifInterface;
import android.support.v4.content.ContextCompat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * различные преобразования над картинками
 */

public final class BitmapUtils {

    public static final int DEFAULT_IMAGE_QUELITY = 90;

    public BitmapUtils() {
        throw new AssertionError();
    }

    /**
     * Комбинирует два изображения в одно. Рисует второе изображение поверх первого, центруя его
     *
     * @param firstImage  изображение 1
     * @param secondImage изображение 2
     * @return объединенное изображение, размером как изображение 1
     */
    public static Bitmap createSingleImageFromMultipleImages(Bitmap firstImage,
                                                             Bitmap secondImage) {
        int width = Math.max(firstImage.getWidth(), secondImage.getWidth());
        int height = Math.max(firstImage.getHeight(), secondImage.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, firstImage.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage,
                          (width - firstImage.getWidth()) / 2,
                          (height - firstImage.getHeight()) / 2,
                          null);
        canvas.drawBitmap(secondImage,
                          (width - secondImage.getWidth()) / 2,
                          (height - secondImage.getHeight()) / 2,
                          null);
        return result;
    }

    /**
     * Изменение размера изображения
     *
     * @param bm        bitmap
     * @param newWidth  новая ширина изображения, в пикселях
     * @param newHeight новая высота изображения, в пикселях
     * @return
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     * Отрисовывает изображение определенным цветом
     *
     * @param bitmap bitmap, над которым будет происходить цветовое отрисовывание
     * @param color  цвет отрисовки
     */
    public static Bitmap setBitmapTint(Bitmap bitmap, @ColorInt int color) {
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        Bitmap bitmapResult =
                Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapResult);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmapResult;
    }

    /**
     * Получение объекта Bitmap из Drawable из ресурсов
     */
    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                                                drawable.getIntrinsicHeight(),
                                                Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    /**
     * Bitmap to base64 string
     * @param bitmap image to encoded
     * @return base64 string
     */
    public static String encodeToBase64(Bitmap bitmap) {
       return encodeToBase64(bitmap, DEFAULT_IMAGE_QUELITY);
    }

    /**
     * Bitmap to base64 string
     * @param bitmap image to encoded
     * @param quality image quality
     * @return base64 string
     */
    public static String encodeToBase64(Bitmap bitmap, int quality){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    /**
     * Поворачивает изображение в зависимости от поворота, которое задано в EXIF тегах
     * @param fileName адрес файла изображения
     * @return Bitmap с повернутым в нужную сторону изображением
     */
    public static Bitmap rotateImage(String fileName) {
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        return rotateImage(bitmap, getImageRotationDegree(fileName));
    }

    /**
     * Поворачивает bitmap в зависимости от поворота, заданного в EXIF тегах
     * Использование: BitmapUtils.rotateImage(bitmap, BitmapUtils.getImageRotationDegree(fileName))
     */
    public static Bitmap rotateImage(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap,
                                                   0,
                                                   0,
                                                   bitmap.getWidth(),
                                                   bitmap.getHeight(),
                                                   matrix,
                                                   true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            return bitmap;
        }
    }

    /**
     * Получить поворот изображения из EXIF-тегов
     * @param fileName имя файла, где записано изображение
     * @return константа поворота из ExifInterface
     */
    public static int getImageRotationDegree(String fileName) {
        try {
            ExifInterface ei = new ExifInterface(fileName);

            return ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                      ExifInterface.ORIENTATION_UNDEFINED);

        } catch (Exception e) {
            return ExifInterface.ORIENTATION_NORMAL;
        }
    }

}
