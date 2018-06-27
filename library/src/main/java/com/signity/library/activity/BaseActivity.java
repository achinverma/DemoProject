package com.signity.library.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.signity.library.R;
import com.signity.library.utils.SecurePrefManager;


/**
 * Created by signity.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String CHECK_INTERNET = "Please check your internet connection";
    public static Context context;
    public int mScreenwidth, mScreenheight;
    private ClickHandler clickHandler;
    protected AlertDialog alertDialog, infoAlertDialog;
    private ProgressDialog progressDialog;
    public SecurePrefManager securePrefManager;
    private String TAG = BaseActivity.class.getCanonicalName();

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Overriden method of Activity; use in implemented class as a substitute of onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        securePrefManager = new SecurePrefManager(this);
        setContentView(getLayoutResID());
//        getScreenDimens();
        initUI();
        initListeners();
    }

    /**
     * Screen dimensions in pixels;
     */
    public void getScreenDimens() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenwidth = displayMetrics.widthPixels;
        mScreenheight = displayMetrics.heightPixels;
    }

    /**
     * resource id of layout like R.id..
     *
     * @return id of the resource mentioned
     */
    protected abstract int getLayoutResID();

    /**
     * initiate the UI elements here
     */
    protected abstract void initUI();

    /**
     * initialize listeners like onClick, onItemClick, etc here
     */
    protected abstract void initListeners();

//    protected abstract void serverCall(int index);


    /**
     * Use to transfer user to next screen
     *
     * @param current: current activity
     * @param next     : next activity
     */
    public void navigateToNextScreen(Context current, Class<?> next, boolean isFinish) {
        Intent intent = new Intent(current, next);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * If internet is not present show error dialog here
     */
    public void showNoInternetDialog() {
        infoAlertDialog(CHECK_INTERNET);
    }

    /**
     * Show loader when required
     */
    public void showLoadingDialog() {

        if (progressDialog == null) {
            setProgressDialog(getString(R.string.processing));
        }
        try {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show loader when required with custom message
     */

    public void showLoadingDialog(String msg) {

        if (progressDialog == null) {
            setProgressDialog(msg);
        }
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * initialise progress dialog
     *
     * @param msg :hold message to show
     */
    private void setProgressDialog(String msg) {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hide loading dialog
     */
    public void hideLoadingDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Use to show info message as a pop up
     *
     * @param message : hold info to display
     */
    public void infoAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(R.string.app_name);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (clickHandler != null) clickHandler.onPositiveClick();
                dialog.dismiss();
            }
        });
        if (infoAlertDialog != null) {
            if (infoAlertDialog.isShowing())
                infoAlertDialog.dismiss();
        }
        infoAlertDialog = builder.create();
        infoAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setDialogButtonTextColor(infoAlertDialog);
        infoAlertDialog.show();
    }

    /**
     * Use to show custum view on dialog
     *
     * @param message : hold info to display
     * @param title   : to display required title
     * @param layout
     */


    /**
     * Use to set dialog button text color to required color
     *
     * @param alertDialog
     */
    void setDialogButtonTextColor(final AlertDialog alertDialog) {
        //  setup to change color of the button
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.appColor));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.appColor));
            }
        });
    }

    /**
     * Use to show info message as a pop up
     *
     * @param message : hold info to display
     * @param title   : to display required title
     */
    public void infoAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (clickHandler != null) clickHandler.onPositiveClick();
                dialog.dismiss();
            }
        });
        if (infoAlertDialog != null) {
            if (infoAlertDialog.isShowing())
                infoAlertDialog.dismiss();
        }
        infoAlertDialog = builder.create();
        infoAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setDialogButtonTextColor(infoAlertDialog);
        infoAlertDialog.show();
    }

    /**
     * To show message and ask for user confirmation
     *
     * @param message  :hold info message text
     * @param positive :to show positive button text on dialog
     * @param negative :to show negative button text on dialog
     */
    public void confirmationAlertDialog(String message, String positive, String negative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton(positive
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (clickHandler != null) clickHandler.onPositiveClick();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (clickHandler != null) clickHandler.onNegativeClick();
                dialog.dismiss();
            }
        });
        if (alertDialog != null) {
            if (alertDialog.isShowing())
                alertDialog.dismiss();
        }
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setDialogButtonTextColor(alertDialog);
        alertDialog.show();
    }

    /**
     * To show message and ask for user confirmation
     *
     * @param message  :hold info message text
     * @param positive :to show positive button text on dialog
     * @param negative :to show negative button text on dialog
     * @param title    :to display requied title
     */
    public void confirmationAlertDialog(String title, String message, String positive, String negative, final ClickHandler clickHandler) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(title);

        builder.setPositiveButton(positive
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (clickHandler != null) clickHandler.onPositiveClick();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (clickHandler != null) clickHandler.onNegativeClick();
                dialog.dismiss();
            }
        });
        if (alertDialog != null) {
            if (alertDialog.isShowing())
                alertDialog.dismiss();
        }
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setDialogButtonTextColor(alertDialog);
        alertDialog.show();
    }

    /**
     * use to set click handler on dialog button
     *
     * @param click : interface reference that implemented by any class(Activity/Fragment)
     */
    public void setDialogClick(ClickHandler click) {
        clickHandler = click;
    }

    /**
     * To show toast messages in app
     *
     * @param msg : hold message info
     */

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    /**
     * its use to handle click on dialog button
     */
    public interface ClickHandler {
        /**
         * will work for positive(ok/yes) click
         */
        void onPositiveClick();

        /**
         * will work for negative(no/cancel) click
         */
        void onNegativeClick();
    }


    public void setListFragment(Fragment mFragment, boolean addtostack) {
        Bundle bundle = new Bundle();
        mFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, mFragment, "" + mFragment.getClass().getSimpleName());
        if (addtostack) {
            ft.addToBackStack("" + mFragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public void setFragment(Fragment mFragment, boolean addtostack) {
        /*Bundle bundle = new Bundle();
        bundle.putBoolean(ROLE, isTeacher);
        mFragment.setArguments(bundle);*/
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, mFragment, "" + mFragment.getClass().getSimpleName());
        if (addtostack) {
            ft.addToBackStack("" + mFragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public void addFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "" + fragment.getClass().getSimpleName())
                .addToBackStack("" + fragment.getClass().getSimpleName())
                .commit();
    }


    public void loge(String msg) {
        Log.e(TAG, msg);
    }

    public void logd(String msg) {
        Log.d(TAG, msg);
    }

    public void logWtf(String msg) {
        Log.wtf(TAG, msg);
    }

}