package com.signity.library.aws;

/**
 * Created by signity.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.signity.library.R;
import com.signity.library.utils.FileUtils;
import java.io.File;


/**
 *  This class is used to upload the files on aws s3 server
 *
 */
public class AwsFileUpload  extends AsyncTask<String, Void, Void> {

    private Context context;
    private OnFileUploadedCallback callback;
    private ProgressDialog progressDialog;
    AWSUtils awsUtils;
    String aws_bucket_name;


    /**
     * Interface - This is used to communicate with activity/fragment to return the result after file uploading
     *
     * @param
     */
    public interface OnFileUploadedCallback {
        void onFileUploaded(String url);
        void onError(Exception ex);
        void onProgressChanged(int id, long bytesCurrent, long bytesTotal);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog(context.getString(R.string.processing));
    }

    /**
     * used to upload the files on aws s3 server and need these parameters
     *
     * @param context : your current view context
     * @param : AWS_ACCESS_KEY
     * @param : AWS_SECRET_KEY
     * @param : aws_bucket_name
     * @param : callback this callback will gives you the result after file uploading
     *
     */
    public AwsFileUpload(Context context,String AWS_ACCESS_KEY, String AWS_SECRET_KEY,String aws_bucket_name ,OnFileUploadedCallback callback) {
        this.context = context;
        this.callback = callback;
        this.aws_bucket_name = aws_bucket_name;
        awsUtils = new AWSUtils(AWS_ACCESS_KEY,AWS_SECRET_KEY);
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            String filePath = strings[0];
            Uri source = Uri.parse(filePath);
            final File file = new File(FileUtils.getPath(context, source));
            //Log.e("AWS file.length=", "" + file.length());
            //final File file = new File(strings[0]);
            AmazonS3Client client = awsUtils.getS3Client(context);
            client.setEndpoint("https://s3.us-east-1.amazonaws.com/");
//        client.setEndpoint(Constants.AWS_ENDPOINT);
            if (!client.doesBucketExist(aws_bucket_name))
                client.createBucket(aws_bucket_name);
            TransferUtility utility = TransferUtility.builder().s3Client(client).context(context).build();
            TransferObserver observer = utility.upload(aws_bucket_name, aws_bucket_name+"/" + file.getName(), file, CannedAccessControlList.PublicRead);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    try {
                        if (state == TransferState.COMPLETED) {
                            String url = String.valueOf(awsUtils.getS3Client(context).getUrl(aws_bucket_name, aws_bucket_name+"/" + file.getName()));
                            if (callback != null)
                                callback.onFileUploaded(url);
                            hideDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    callback.onProgressChanged(id,bytesCurrent,bytesTotal);
                }

                @Override
                public void onError(int id, Exception ex) {
                    callback.onError(ex);
                    hideDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            hideDialog();
        }
        return null;
    }

    private void showProgressDialog(final String msg) {
        try {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage(msg);
                    progressDialog.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Hide loading dialog
     */
    public void hideDialog() {
        try {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}