package com.kala.kala.Util;


import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

/**
 * Created by Kala on 2/28/16.
 */
public class AWSManager {

    private Context context;
    private String POOL_ID = "us-east-1:5905996f-74d1-4da7-99fe-094bdea814b6";
    private Regions REGION = Regions.US_EAST_1;
    private String BUCKET = "kalaus";
    private TransferUtility transferUtility;
    private String CACHE_DIR;

    public AWSManager(Context context) {
        this.context = context;
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,    /* get the context for the application */
                POOL_ID,    /* Identity Pool ID */
                REGION          /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        );
        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, context);
        CACHE_DIR = context.getCacheDir().toString() + "/";
    }

    public void upload(File file, String keyPath, final Callback<String> callback) {
        TransferObserver transferObserver = transferUtility.upload(BUCKET, keyPath, file);
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d("AWSManager.upload()", id + " " + state.toString());
                if (state.toString().equals("COMPLETED")) {
                    callback.callback(null, state.toString());
                }
                //callback.callback(null,);
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                int percentage = (int) (bytesCurrent / bytesTotal * 100);
                Log.d("AWSManager.upload()", String.valueOf(percentage));
                //callback.callback(null,String.valueOf(percentage));
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("AWSManager.upload()", ex.getMessage());
                callback.callback(new Error(ex.getMessage()), null);
            }
        });
    }

    public void download(String keyPath, final Callback<File> callback) {
        //int indexFileName = keyPath.lastIndexOf('/')+1;
        //int indexFileType = keyPath.lastIndexOf('.')+1;
        //String filePath = keyPath.substring(0,indexFileName);
        //String fileName = keyPath.substring(indexFileName,indexFileType);
        //String fileType = keyPath.substring(indexFileType);
        final File file = new File(CACHE_DIR + keyPath);
        TransferObserver transferObserver = transferUtility.download(BUCKET, keyPath, file);
        transferObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d("AWSManager.download()", id + " " + state.toString());
                if (state.toString() == "COMPLETED") {
                    callback.callback(null, file);
                }
                //callback.callback(null,);
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                int percentage = (int) (bytesCurrent / bytesTotal * 100);
                Log.d("AWSManager.download()", String.valueOf(percentage));
                //callback.callback(null,String.valueOf(percentage));
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("AWSManager.download()", ex.getMessage());
                callback.callback(new Error(ex.getMessage()), null);
            }

        });
    }
}
