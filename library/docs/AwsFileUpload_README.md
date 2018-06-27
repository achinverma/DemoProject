AwsFileUpload
===============

A java class/AsyncTask which is used to upload files on s3 server bucket using AWS sdk. 


Usage
-----
```in java code
try {
            File photo = new File("image.png");
            
            //Log.e("=getAbsolutePath=",""+photo.getAbsolutePath());

            Uri source = Uri.fromFile(photo);
            Uri uri = Uri.parse(source.toString());

            AwsFileUpload awsFileUpload = new AwsFileUpload(Your_Activity_Name.this, AWS_ACCESS_KEY,AWS_SECRET_KEY, aws_bucket_name
            new AwsFileUpload.OnFileUploadedCallback() {
                @Override
                public void onFileUploaded(String url) {
                    Log.e("=url=",""+url);
                    

                }

                @Override
                public void onError(Exception ex) {
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                }
            });
            
            awsFileUpload.execute(uri.toString());
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        and this <service must comes inside the <application>
        <service
                android:name="com.amazonaws.mobileconnectors.s3.transferutility.TransferService"
                android:enabled="true" />
        

```

Limitations
-----------







