package com.kala.kalamodel_android_library.Model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.kala.kalamodel_android_library.AWSManager;
import com.kala.kalamodel_android_library.Callback;
import com.kala.kalamodel_android_library.Model.Schema.Group.Group;
import com.kala.kalamodel_android_library.Model.Schema.Group.GroupUser;
import com.kala.kalamodel_android_library.Model.Schema.Group.UserGroup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kala on 2/28/16.
 */
public class GroupModel {
    private String authUID;
    private AWSManager awsManager;
    private String PROFILE_PICTURE_PATH = "groups/";
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
    public void createMyGroup(final Group group,  final List<String> userIdList, final Callback<String> callback){
        createGroup(group, new Callback<String>() {
            @Override
            public void callback(final Error error, final String groupId) {
                new Thread() {
                    @Override
                    public void run() {
                        if (error != null) {
                            Log.e("GM.createMyGroup", "createGroup error");
                        } else {
                            boolean isMe = false;
                            for (String userId : userIdList) {
                                if (userId.equals(authUID)) {
                                    isMe = true;
                                    break;
                                }
                            }
                            if (!isMe) {
                                userIdList.add(authUID);
                            }
                            addUsersByGroupId(groupId, userIdList, new Callback<String>() {
                                @Override
                                public void callback(final Error error, String result) {
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            if (error != null) {
                                                Log.e("GM.createMyGroup", "addUserByGroupId error");
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        callback.callback(error, null);
                                                    }
                                                });
                                            } else {
                                                Log.d("GM.createMyGroup", groupId);
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        callback.callback(null, groupId);
                                                    }
                                                });
                                            }
                                        }
                                    }.start();
                                }
                            });
                        }
                    }
                }.start();

            }
        });
    }
    public void createGroup(final Group group, final Callback<String> callback){
        new Thread(){
            @Override
            public void run() {
                group.setCreated_time(ServerValue.TIMESTAMP);
                final Firebase groupRef = groupsRef.push();
                final String groupId = groupRef.getKey();
                if(group.getProfile_picture() != null){
                    final File profPict = (File) group.getProfile_picture();
                    String fileExt = profPict.getName().substring(profPict.getName().lastIndexOf(".") + 1);
                    String fileName = groupId + "." + fileExt;
                    String filePath = PROFILE_PICTURE_PATH + fileName;
                    group.setProfile_picture(fileName);
                    awsManager.upload(profPict, filePath, new Callback<String>() {
                        @Override
                        public void callback(final Error error, String result) {
                            if(error != null){
                                Log.e("GM.createGroup", error.getMessage());
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        callback.callback(error, null);
                                    }
                                });
                            } else if(result.equals("COMPLETED")) {
                                groupRef.setValue(group, new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(final FirebaseError firebaseError, Firebase firebase) {
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                if (firebaseError != null) {
                                                    Log.e("GM.createGroup", firebaseError.getMessage());
                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            callback.callback(new Error(firebaseError.getMessage()), null);
                                                        }
                                                    });
                                                } else {
                                                    Log.d("GM.createGroup",String.format("success groupId %s", groupId));
                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            callback.callback(null,groupId);
                                                        }
                                                    });
                                                }
                                            }
                                        }.start();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }.start();
    }
    //this function need concurrent control and error callback control
    public void addUsersByGroupId(final String groupId, final List<String> userIdList, final Callback<String> callback){
        new Thread(){
            @Override
            public void run() {
                Firebase groupRef = groupUsersRef.child(groupId);
                Map<String, Object> usersMap = new HashMap<String, Object>();
                UserGroup adminGroup = new UserGroup(0,ServerValue.TIMESTAMP, null);
                UserGroup userGroup = new UserGroup(1,ServerValue.TIMESTAMP, null);
                GroupUser groupAdmin = new GroupUser(0,ServerValue.TIMESTAMP, null);
                GroupUser groupUser = new GroupUser(1, ServerValue.TIMESTAMP, null);
                for (final String userId : userIdList) {
                    Firebase userGroupRef = userGroupsRef.child(userId).child(groupId);
                    if (userId.equals(authUID)) {
                        userGroupRef.setValue(adminGroup, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(final FirebaseError firebaseError, Firebase firebase) {
                                new Thread(){
                                    @Override
                                    public void run() {
                                        if (firebaseError != null) {
                                            Log.e("GM.addUsersByGroupId", String.format("userGroup add admin %s to group %s failed %s", userId, groupId, firebaseError.getMessage()));
                                        } else {
                                            Log.d("GM.addUsersByGroupId", String.format("userGroup add admin %s to group %s success", userId, groupId));
                                        }
                                    }
                                }.start();

                            }
                        });
                        usersMap.put(userId,groupAdmin.toMap());
                    } else {
                        userGroupRef.setValue(userGroup, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                if (firebaseError != null) {
                                    Log.e("GM.addUsersByGroupId", String.format("userGroup add user %s to group %s failed %s", userId, groupId, firebaseError.getMessage()));
                                } else {
                                    Log.d("GM.addUsersByGroupId", String.format("userGroup add user %s to group %s success", userId, groupId));
                                }
                            }
                        });
                        usersMap.put(userId, groupUser.toMap());
                    }

                }
                groupRef.setValue(usersMap, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(final FirebaseError firebaseError, final Firebase firebase) {
                        new Thread(){
                            @Override
                            public void run() {
                                if (firebaseError != null) {
                                    Log.e("GM.addUsersById", firebaseError.getMessage());
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.callback(new Error(firebaseError.getMessage()), null);
                                        }
                                    });
                                } else {
                                    Log.d("GM.addUsersById", String.format("groupId %s userIds %s",groupId,userIdList.toString()));
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.callback(null,"success");
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
