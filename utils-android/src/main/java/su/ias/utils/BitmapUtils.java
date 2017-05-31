package su.ias.utils;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.support.v4.content.ContextCompat;

/**
 * различные преобразования над картинками
 */

public final class BitmapUtils {

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
     * @param bm bitmap
     * @param newWidth новая ширина изображения, в пикселях
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
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static Bitmap setBitmapTint(Bitmap bitmap, @ColorInt int color) {
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        Bitmap bitmapResult = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapResult);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmapResult;
    }

    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

}
