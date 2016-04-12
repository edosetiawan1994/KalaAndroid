package com.kala.kala.Util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.kala.kala.ServerModel.Contact;
import com.kala.kala.ServerModel.GroupModel;
import com.kala.kala.ServerModel.ReminderModel;
import com.kala.kala.ServerModel.Schema.User.User;
import com.kala.kala.ServerModel.UserModel;

import java.util.Map;

/**
 * Created by Kala on 2/28/16.
 */
public class ModelManager {
    private String FIREBASE_URL = "https://semutdev2.firebaseio.com";
    private Firebase ref;
    private static ModelManager instance;
    private AWSManager awsManager;
    private UserModel userModel;
    private ReminderModel reminderModel;
    private String authUID;
    private GroupModel groupModel;

    public ModelManager(Context context) {
        Firebase.setAndroidContext(context);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        ref = new Firebase(FIREBASE_URL);
        awsManager = new AWSManager(context);
        Contact.setInstance(context);
    }

    public static ModelManager getInstance(Context context) {
        if (instance == null) {
            instance = new ModelManager(context.getApplicationContext());
        }
        return instance;
    }

    public AWSManager getAwsManager() {
        return awsManager;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public ReminderModel getReminderModel() {
        return reminderModel;
    }

    public GroupModel getGroupModel() {
        return groupModel;
    }

    public void login(final String username, final String password, final Callback<String> callback) {
        new Thread() {
            @Override
            public void run() {
                ref.authWithPassword(username, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(final AuthData authData) {
                        new Thread() {
                            @Override
                            public void run() {
                                Log.d("ModelManager.login", "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                                initiateWithAuth(authData);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.callback(null, authData.getUid());
                                    }
                                });
                            }
                        }.start();

                    }

                    @Override
                    public void onAuthenticationError(final FirebaseError firebaseError) {
                        new Thread() {
                            @Override
                            public void run() {
                                Log.e("ModelManager.login", firebaseError.getMessage());
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.callback(new Error(firebaseError.getMessage()), null);
                                    }
                                });
                            }
                        }.start();

                    }
                });
            }
        }.start();

    }

    public void register(final String email, final String password, final Callback<String> callback) {

        new Thread() {
            @Override
            public void run() {
                ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(final Map<String, Object> result) {
                        new Thread() {
                            @Override
                            public void run() {
                                final String userId = (String) result.get("uid");
                                User user = new User();
                                user.setEmail(email);
                                user.setCreated_time(ServerValue.TIMESTAMP);
                                Firebase myRef = ref.child("users").child(userId);
                                myRef.setValue(user, new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(final FirebaseError firebaseError, final Firebase firebase) {
                                        new Thread() {

                                            @Override
                                            public void run() {
                                                if (firebaseError != null) {
                                                    Log.e("ModelManager.register()", firebaseError.getMessage());
                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            callback.callback(new Error(firebaseError.getMessage()), null);
                                                        }
                                                    });
                                                } else {
                                                    Log.d("ModelManager.register()", "Successfully created user account with uid: " + result.get("uid"));
                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            callback.callback(null, userId);
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

                    @Override
                    public void onError(final FirebaseError firebaseError) {
                        new Thread() {
                            @Override
                            public void run() {
                                Log.e("ModelManager.login()", firebaseError.getMessage());
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        callback.callback(new Error(firebaseError.getMessage()), null);
                                    }
                                });
                            }
                        }.start();
                    }
                });
            }
        }.start();
    }

    private void initiateWithAuth(AuthData authData) {
        authUID = authData.getUid();
        userModel = new UserModel(ref, authUID, awsManager);
        reminderModel = new ReminderModel(ref, authUID, awsManager);
        groupModel = new GroupModel(ref, authUID, awsManager);
    }

    //Careful with autoLogin because it will still listen auth state
    public void autoLogin(final Callback<String> callback) {
        new Thread() {
            @Override
            public void run() {
                ref.addAuthStateListener(new Firebase.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(AuthData authData) {
                        if (authData != null) {
                            Log.d("ModelManager.autologin", "success user id " + authData.getUid());
                            initiateWithAuth(authData);
                            callback.callback(null, authData.getUid());
                        } else {
                            Log.e("ModelManager.autologin", "failed");
                            callback.callback(new Error("autologin failed"), null);
                        }
                    }
                });
            }
        }.start();

    }

    public void logout(Callback<Boolean> callback) {
        ref.unauth();
        authUID = null;
        userModel = null;
        reminderModel = null;
        groupModel = null;
        callback.callback(null, true);
    }
}
