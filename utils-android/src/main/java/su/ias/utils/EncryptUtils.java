package su.ias.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created on 10/6/17.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class EncryptUtils {

    private static final int AVATAR_QUALITY = 70;

    public EncryptUtils() {
        throw new UnsupportedOperationException("Oooops!");
    }

    /**
     * generate Guid
     *
     * @return string like 6F9619FF-8B86-D011-B42D-00CF4FC964FF
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * create md5 from string
     *
     * @param input string data
     * @return md5 hash
     */
    public static String MD5(String input) {
        return byteToString(hash(input.getBytes(), "MD5"));
    }

    /**
     * create sha1 from string
     *
     * @param input string data
     * @return sha1 hash
     */
    public static String SHA1(String input) {
        return byteToString(hash(input.getBytes(), "SHA1"));
    }

    /**
     * create base64 from string
     *
     * @param data string data
     * @return base64 hash
     */
    public static String EncodeBase64(String data){
        return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
    }

    /**
     * Bitmap to base64
     *
     * @param image bitmap
     * @return base64 string
     */
    public static String encodeToBase64(Bitmap image) {
        return encodeToBase64(image, AVATAR_QUALITY);
    }


    public static String encodeToBase64(Bitmap image, int quality) {
        if (quality < 0 || quality > 100) {
            quality = AVATAR_QUALITY;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }

    public static byte[] hash(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String byteToString(final byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte aData : data) {
            sb.append(Integer.toHexString((aData & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
}
