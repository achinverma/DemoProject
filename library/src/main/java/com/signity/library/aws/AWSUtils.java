package com.signity.library.aws;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by signity.
 */
public final class AWSUtils {

    private  AmazonS3Client sS3Client;
    private  TransferUtility sTransferUtility;
    String aws_access_key,  aws_acces_secret_key;

    public AWSUtils(String AWS_ACCESS_KEY, String AWS_SECRET_KEY){
        this.aws_access_key = AWS_ACCESS_KEY;
        this.aws_acces_secret_key = AWS_SECRET_KEY;
    }

    public  AmazonS3Client getS3Client(Context context) {
        if (sS3Client == null) {
            ClientConfiguration cc = new ClientConfiguration();
            cc.setSocketTimeout(180000);
            cc.setConnectionTimeout(120000); // default is 10 secs
            sS3Client = new AmazonS3Client(new BasicAWSCredentials(aws_access_key, aws_acces_secret_key),cc);
            sS3Client.setRegion(Region.getRegion(Regions.US_EAST_1));
        }
        return sS3Client;
    }

    public  TransferUtility getTransferUtility(Context context) {
        if (sTransferUtility == null)
            sTransferUtility = TransferUtility.builder().s3Client(getS3Client(context)).context(context).build();
        return sTransferUtility;
    }
}
