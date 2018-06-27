package com.signity.library.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by signity.
 */

public class Utils {

    public static Bitmap mbitmap = null;

    public static Date StringToDate(String mdate) {
        String dtStart = mdate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(dtStart);
            //System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public static void showToast(Context context, String msg) {
        try {
            if (context != null) {
                Toast.makeText(context, "" + msg, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int dpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics());
    }

    public static String getUnix() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return "" + unixTime;
    }

    public static String getUnixStamp() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return "" + unixTime;
    }

    public static String getUnixTimeStamp() {
        long unixTime = System.currentTimeMillis();
        return "" + unixTime;
    }

    public static String decodeBase64(String coded) {
        byte[] valueDecoded = new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
        }
        return new String(valueDecoded);
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static int device_width(Context context) {

        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        return metrics.widthPixels;
    }

    public static int device_height(Context context) {
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        return metrics.heightPixels;
    }



    public static String convertDate(String inputDate) {
        try { //04/28/1990
            DateFormat inFormater = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat outFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }



    public static String convertMsgDate(String inputDate) {
        try {
            DateFormat inFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outFormatter = new SimpleDateFormat("hh:mm aa");
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }


    public static String convertMsgDate1(String inputDate) {
        try {
            DateFormat inFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outFormatter = new SimpleDateFormat("EEEE hh:mm aa");
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    public static String convertMsgDate2(String inputDate) {
        try {
            DateFormat inFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outFormatter = new SimpleDateFormat("EEE, MMM d, hh:mm aa");
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    public static String convertMsgDate3(String inputDate) {
        try {
            DateFormat inFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outFormatter = new SimpleDateFormat("EEE, MMM d, yyyy, hh:mm aa");//Mon, Dec 12, 2016, 6:59 PM
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    public static String convertMsgDate4(String inputDate) {
        try {
            DateFormat inFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outFormatter = new SimpleDateFormat("EEE, MMM, d yyyy, hh:mm aa");//Day, Month, dd yyyy, hhmm am/pm
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }


    public static String convertDateformat(String inputDate) {

        try {
            DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = theDateFormat.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            theDateFormat = new SimpleDateFormat("hh:mm a dd/MM/yyyy");

            return theDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }


    @SuppressLint("NewApi")
    public static void setDrawable(Context context, ImageView view, int resource) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(context.getResources().getDrawable(resource));
        } else {
            view.setBackground(context.getResources().getDrawable(resource));
        }
    }


    public static String convertTime(String inputDate) {
        //2017-10-24 06:26:33
        String datetime = "";
        try {
            DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = theDateFormat.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            theDateFormat = new SimpleDateFormat("HH:mm a");


            String currentDate = CurrentTime();

            //Log.e("inputDate="+inputDate,""+currentDate);


            datetime = theDateFormat.format(date);

            /*if(inputDate.equalsIgnoreCase(currentDate)){
                Log.e("inputDate.equalsIgnoreCase(currentDate)=","inputDate.equalsIgnoreCase(currentDate)");
            }*/


            return datetime;
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    // Serialize a single object.
    public static <T> String serializeToJson(T myClass) {
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        String json = gson.toJson(myClass, type);
        return json;
    }

    // Deserialize to single object.
    public static <T> T deserializeFromJson(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        T myClass = gson.fromJson(jsonString, type);
        return myClass;
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public static void showSnackBar(Activity context, String message) {
        try {
            Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(4);  // show multiple line
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            Log.e("showSnackBar", "" + e.toString());
        }
    }

    public static void showSnackBarButton(final Activity context, String message) {
        try {
            Snackbar snackbar = Snackbar
                    .make(context.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Check", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(4);  // show multiple line
            textView.setTextColor(Color.WHITE);
            snackbar.show();

        } catch (Exception e) {
            Log.e("showSnackBar", "" + e.toString());
        }
    }

    public static void SnackBarButton(final Activity context, String message) {
        try {
            Snackbar snackbar = Snackbar
                    .make(context.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Set Default", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(4);  // show multiple line
            textView.setTextColor(Color.WHITE);
            snackbar.show();

        } catch (Exception e) {
            Log.e("showSnackBar", "" + e.toString());
        }
    }

    public static void showFragmentSnackBar(CoordinatorLayout coordinatorLayout, String message) {
        try {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(4);  // show multiple line
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            Log.e("showSnackBar", "" + e.toString());
        }
    }


    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static float calcLocationDistanceInKm(double initialLat, double initialLong, double finalLat, double finalLong) {

        Location locationA = new Location("point A");
        locationA.setLatitude(initialLat);
        locationA.setLongitude(initialLong);
        Location locationB = new Location("point B");
        locationB.setLatitude(finalLat);
        locationB.setLongitude(finalLong);

        return Math.round(locationA.distanceTo(locationB) / 1000);

    }


    //Utils.Share(Utils.getShareApplication(),"Find us on The Groove App "+"\nhttp://gr8.es/app",getActivity());
    public static void Share(List<String> PackageName, String Text, Context context) {
        try {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(Intent.ACTION_SEND);
                    targetedShare.setType("text/plain"); // put here your mime type
                    if (!PackageName.contains(info.activityInfo.packageName.toLowerCase())) {
                        targetedShare.putExtra(Intent.EXTRA_TEXT, Text);
                        targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                        targetedShareIntents.add(targetedShare);
                    }
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Share..");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooserIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getShareApplication() {
        List<String> mList = new ArrayList<String>();
       /*mList.add("com.google.android.apps.plus");*/
        //mList.add("com.facebook.katana");

        return mList;
    }

    public static String GetMimeType(Context context, Uri uriImage) {
        String strMimeType = null;
        Cursor cursor = context.getContentResolver().query(uriImage,
                new String[]{MediaStore.MediaColumns.MIME_TYPE}, null, null, null);

        if (cursor != null && cursor.moveToNext()) {
            strMimeType = cursor.getString(0);
        }
        return strMimeType.trim();
    }

    public static String CurrentDate() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        //System.out.println("get_CurrentDate_time => "+formattedDate);
        return formattedDate;
    }

    public static String CurrentTime() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        //System.out.println("get_CurrentDate_time => "+formattedDate);

        return formattedDate;
    }


    /**
     * This method will hide virtual keyboard if opened
     *
     * @param mContext contains context of application
     */
    public static void hideVirtualKeyboard(Context mContext) {
        try {
            View view = ((Activity) mContext).getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String convertDatetime(String inputDate) {
        try {
            DateFormat inFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outFormatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    public static String convertsDate(String inputDate) {
        try { //04/28/1990
            DateFormat inFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outFormatter = new SimpleDateFormat("hh:mm a");
            Date date = null;
            try {
                date = inFormater.parse(inputDate);
            } catch (Exception parseException) {
                // Date is invalid. Do what you want.
            }
            return outFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate;
        }
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        //System.out.println("get_CurrentDate_time => "+formattedDate);

        return formattedDate;
    }


    /**
     * It return true if enddate is after start date.
     *
     */
    public static boolean isDateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "yyyy-MM-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate)||date1.equals(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * It return true if enddatetime is after start datetime.
     *
     */
    public static boolean DateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "yyyy-MM-dd HH:mm"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * It return true if enddatetime is after start datetime.
     *
     */
    public static boolean isTimeAfter(String startDate, String endDate) {
        try {
            String myFormatString = "HH:mm"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * It return true if date is Equals.
     *
     */
    public static boolean isDateEquals(String startDate, String endDate) {
        try {
            String myFormatString = "yyyy-MM-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.equals(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }


}
