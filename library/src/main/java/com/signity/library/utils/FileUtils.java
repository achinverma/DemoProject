package com.signity.library.utils;

/**
 * Created by signity.
 */

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {

    public static final String LOG_TAG = FileUtils.class.getName();
    private final int MEDIA_TYPE_IMAGE = 1;

    public static final String FOLDER = "winter-sports";

    public static String getRealPathFromURI(Context context, Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor cursor = ((Activity) context).managedQuery(contentUri, proj, null, null, null);

        int column_index = cursor

                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public static File getFile(Context context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(new Date());
        File mediaFile;
        File mediaStorageDir;
        if (isExternalStorageWritable()) {
            mediaStorageDir = getAlbumStorageDir(context, FOLDER);
        } else {
            mediaStorageDir = getFileFromInternal(context);
        }

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        Log.e("----Path----", mediaFile.getAbsolutePath());
        return mediaFile;
    }

    public static File getFile(Context context, String folder) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(new Date());
        File mediaFile;
        File mediaStorageDir;
        if (isExternalStorageWritable()) {
            if (folder != null && !folder.isEmpty()) {
                mediaStorageDir = getAlbumStorageDir(context, FOLDER + "/" + folder);
            } else {
                mediaStorageDir = getAlbumStorageDir(context, FOLDER);
            }
        } else {
            mediaStorageDir = getFileFromInternal(context);
        }

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        Log.e("----Path----", mediaFile.getAbsolutePath());
        return mediaFile;
    }


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getFileFromInternal(Context context) {
        File file;
        file = new File(context.getFilesDir(), FOLDER);
        return file;
    }


    public static File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStorageDirectory(), albumName);
//        File file = new File(context.getExternalFilesDir(
//                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }

        return file;
    }


    public static Bitmap checkImageRotation(Bitmap bitMap, String path) {

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int orientation = 0;
        try {
            orientation = ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                bitMap = RotateBitmap(bitMap, 90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                bitMap = RotateBitmap(bitMap, 180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                bitMap = RotateBitmap(bitMap, 270);
                break;
        }

        return bitMap;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }


    @SuppressWarnings("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
