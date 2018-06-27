package com.signity.library.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.signity.library.activity.BaseActivity;


/**
 * Created by signity .
 */

public abstract class BaseFragment extends Fragment {

    protected int mScreenwidth, mScreenheight;
    protected BaseActivity mActivity;
    /**
     * Used in replacement for getActivity();
     * Since getActivity() may leads to null; if fragment is detached, thereby used in onAttach and onDetach methods
     * Do check null before using every time.
     */
    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * Assuming each activity will be extended by BaseActivity
         */
        mActivity = (BaseActivity) this.getActivity();
        getScreenDimens();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initListeners();
        onCreateView(savedInstanceState);
    }

    /**
     * replacement of findViewByID; will help eliminating the view.findViewByID in fragment
     * makes it generally same like done in Activity
     *
     * @param resId
     * @return
     */
    public <T extends View> T findViewById(@IdRes int resId) {
        return getView().findViewById(resId);
    }

    /**
     * resource id of layout like R.id..
     *
     * @return id of the resource mentioned
     */
    @LayoutRes
    public abstract int getLayoutResID();

    /**
     * Can be used same like onCreate(@Nullable Bundle savedInstanceState)
     * also for onViewCreated
     *
     * @param savedInstanceState
     */
    public abstract void onCreateView(@Nullable Bundle savedInstanceState);

    /**
     * initiate the UI elements here
     */
    public abstract void initUI();

    /**
     * initialize listeners like onClick, onItemClick, etc here
     */
    public abstract void initListeners();

    /**
     * Screen dimensions in pixels;
     */
    void getScreenDimens() {
       mScreenwidth= mActivity.mScreenwidth;
       mScreenheight= mActivity.mScreenheight;
    }


    /**
     * If internet is not present show error dialog here
     */

    public void showNoInternetDialog() {
        mActivity.showNoInternetDialog();
    }

    /**
     * Show loader when required
     */

    public void showLoadingDialog() {
        mActivity.showLoadingDialog();
    }
    /**
     * Show loader when required with custom message
     */

    public void showLoadingDialog(String msg) {
        mActivity.showLoadingDialog(msg);
    }


    /**
     * Hide loading dialog
     */

    public void hideLoadingDialog() {
        mActivity.hideLoadingDialog();
    }

    /**
     * To show toast messages in app
     *
     * @param msg : hold message info
     */

    public void showToast(String msg) {
        mActivity.showToast(msg);
    }






    /**
     * Use to transfer user to next screen
     *
     * @param current: current activity
     * @param next     : next activity
     */
    public void navigateToNextScreen(Context current, Class<?> next, boolean isFinish) {
        mActivity.navigateToNextScreen(current, next,isFinish);
    }





    /**
     * Use to show info message as a pop up
     *
     * @param message : hold info to display
     */
    public void infoAlertDialog(String message) {
        mActivity.infoAlertDialog(message);
    }

    /**
     * Use to show info message as a pop up
     *
     * @param message : hold info to display
     * @param title   :to display requied title
     */
    public void infoAlertDialog(String title, String message) {
        mActivity.infoAlertDialog(title, message);
    }

    /**
     * To show message and ask for user confirmation
     *
     * @param message  :hold info message text
     * @param positive :to show positive button text on dialog
     * @param negative :to show negative button text on dialog
     */
    public void confirmationAlertDialog(String message, String positive, String negative) {
        mActivity.confirmationAlertDialog(message, positive, negative);
    }




}
