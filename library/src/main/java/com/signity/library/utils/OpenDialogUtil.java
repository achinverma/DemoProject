package com.signity.library.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by signity.
 */


public class OpenDialogUtil {

    public interface OnOpenDialogImageHandleListener {
        void onOpenCamera();

        void onOpenGallery();

    }

    /**
     * Common dialog to show Camera,Gallery and Cancel options
     *
     * @param listener: pass call back here
     */
    public static void openDailogForImagePickOption(Context context,final OnOpenDialogImageHandleListener listener) {
        final String[] picMode = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(picMode, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (picMode[which].equalsIgnoreCase(picMode[0])) {
                    dialog.dismiss();
                    listener.onOpenCamera();
                } else if (picMode[which].equalsIgnoreCase(picMode[1])) {
                    dialog.dismiss();
                    listener.onOpenGallery();
                } else {
                    dialog.dismiss();
                }
//                actionProfilePic(picMode[which]);
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
