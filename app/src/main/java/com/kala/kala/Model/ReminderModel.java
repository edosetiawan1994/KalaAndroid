package com.kala.kala.Model;

import com.firebase.client.Firebase;
import com.kala.kala.AWSManager;

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
