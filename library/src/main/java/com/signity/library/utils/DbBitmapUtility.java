package com.signity.library.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by signity.
 */

public class DbBitmapUtility {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = null;
        try {

            int bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmap);
            Log.e("DbBitmapUtility=" + getFileSize(bitmapByteCount), "bitmapByteCount: " + bitmapByteCount);

            stream = new ByteArrayOutputStream();
            if (bitmapByteCount > 4000000) { //4.0 mb
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
            options.inPurgeable = true; // inPurgeable is used to free up memory while required

            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, options);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    public static Bitmap compressImage(String filePath) {
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        //by setting this field as true, the actual bitmap pixels are not loaded in the memory.
        // Just the bounds are loaded.
        // If you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        //max Height and width values of the compressed image is taken as 816x612
//        float maxHeight = maxHt;
//        float maxWidth = maxWt;
        float maxWidth = 480.0f;
        float maxHeight = 800.0f;
        if (actualWidth > 0 && actualWidth < 1200) {
            maxWidth = 480.0f;
            maxHeight = 800.0f;
        }else if (actualWidth>=1200&&actualWidth<2500){
            maxWidth = 720.0f;
            maxHeight = 1280.0f;
        }else if (actualWidth>=2500){
            maxWidth = 1080.0f;
            maxHeight = 1920.0f;
        }
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
        //width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        /*https://stackoverflow.com/questions/28424942/decrease-image-size-without-losing-its-quality-in-android*/


        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
        //this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            //load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        //check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        FileOutputStream out = null;
//        String filename = getFilename();
//        try {
//            out = new FileOutputStream(filename);
//            //write the compressed bitmap at the destination specified by filename.
//            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        return scaledBitmap;
//        return "";
    }

    public static String getRealPathFromURI(Context context, String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        return getRealPath(context, contentUri);
    }

    public static String getRealPath(Context context, Uri contentUri) {
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

}
