package com.kala.kala.ServerModel;

import com.firebase.client.Firebase;
import com.kala.kala.Util.AWSManager;

/**
 * Created by Kala on 2/28/16.
 */
public class ReminderModel {
    private String authUID;
    private AWSManager awsManager;

    public ReminderModel(Firebase firebaseRef, String authUID, AWSManager awsManager) {
        this.authUID = authUID;
        this.awsManager = awsManager;
    }
}
