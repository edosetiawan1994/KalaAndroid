package com.kala.kala.ServerModel;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.kala.kala.ServerModel.Schema.Group.Group;
import com.kala.kala.ServerModel.Schema.Group.GroupUser;
import com.kala.kala.Util.AWSManager;
import com.kala.kala.Util.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kala on 2/28/16.
 */
public class GroupModel {
    private String authUID;
    private AWSManager awsManager;
    private Firebase groupsRef;
    private Firebase userGroupsRef;
    private Firebase groupUsersRef;
    private Firebase myGroupsRef;

    public GroupModel(Firebase firebaseRef, String authUID, AWSManager awsManager) {
        this.authUID = authUID;
        this.awsManager = awsManager;
        groupsRef = firebaseRef.child("groups");
        userGroupsRef = firebaseRef.child("user_groups");
        groupUsersRef = firebaseRef.child("group_users");
        myGroupsRef = userGroupsRef.child(authUID);
    }
    public void createMyGroup(final Group group,  final List<String> users, final Callback<String> callback){
        new Thread(){
            @Override
            public void run() {
                group.setCreated_time(ServerValue.TIMESTAMP);
                Firebase groupRef = groupsRef.push();
                final String groupId = groupRef.getKey();
                groupRef.setValue(group, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(final FirebaseError firebaseError, Firebase firebase) {
                        new Thread(){
                            @Override
                            public void run() {
                                if(firebaseError != null){
                                    Log.e("GM.createMyGroup",firebaseError.getMessage());
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.callback(new Error(firebaseError.getMessage()), null);
                                        }
                                    });
                                } else {
                                    Firebase groupRef = groupUsersRef.child(groupId);
                                    Map<String, Object> usersMap = new HashMap<String, Object>();
                                    for( String user : users){
                                        GroupUser groupUser = new GroupUser(null,ServerValue.TIMESTAMP, null);
                                        if(user.equals(authUID)){
                                            groupUser.setType(0);

                                        } else {
                                            groupUser.setType(1);
                                        }
                                        usersMap.put(user,groupUser.toMap());
                                    }
                                    groupRef.setValue(usersMap, new Firebase.CompletionListener() {
                                        @Override
                                        public void onComplete(final FirebaseError firebaseError, final Firebase firebase) {
                                            if( firebaseError != null){
                                                Log.e("GM.createMyGroup", firebaseError.getMessage());
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        callback.callback(new Error(firebaseError.getMessage()),null);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        }.start();
                    }
                });
            }
        }.start();
    }

}
