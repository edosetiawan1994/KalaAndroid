package com.kala.kala.Model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.kala.kala.AWSManager;
import com.kala.kala.Callback;
import com.kala.kala.Model.Schema.User.Phone;
import com.kala.kala.Model.Schema.User.User;
import com.kala.kala.Model.Schema.User.UserFriend;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kala on 2/28/16.
 */
public class UserModel {
    private String authUID;
    private String PROFILE_PICTURE_PATH = "users/";
    private Firebase userRef;
    private Firebase friendsRef;
    private Firebase myRef;
    private Firebase myFriendsRef;
    private Firebase phonesRef;
    private AWSManager awsManager;

    public UserModel(Firebase firebaseRef, String authUID, AWSManager awsManager) {
        this.authUID = authUID;
        this.awsManager = awsManager;
        userRef = firebaseRef.child("users");
        myRef = userRef.child(authUID);
        friendsRef = firebaseRef.child("user_friends");
        myFriendsRef = friendsRef.child(authUID);
        phonesRef = firebaseRef.child("phones");
    }

    public void updateMyUser(final User user, final Callback<String> callback) {
        new Thread() {
            @Override
            public void run() {
                user.setUpdated_time(ServerValue.TIMESTAMP);
                if (user.getPhone() != null) {
                    String phone = user.getPhone();
                    phone = Contact.sanitizePhoneNumber(phone);
                    user.setPhone(phone);
                }
                if (user.getProfile_picture() != null) {
                    final File profPict = (File) user.getProfile_picture();
                    String fileExt = profPict.getName().substring(profPict.getName().lastIndexOf(".") + 1);
                    String fileName = authUID + "." + fileExt;
                    String filePath = PROFILE_PICTURE_PATH + fileName;
                    user.setProfile_picture(fileName);
                    awsManager.upload(profPict, filePath, new Callback<String>() {
                        @Override
                        public void callback(final Error error, String result) {
                            if (error != null) {
                                Log.e("UserModel.updateMyUser", error.getMessage());
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        callback.callback(new Error(error.getMessage()), null);
                                    }
                                });
                            } else if (result.equals("COMPLETED")) {
                                Log.d("UserModel.updateMyUser", "upload profPict success");
                                myRef.updateChildren(user.toMap(), new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(final FirebaseError firebaseError, Firebase firebase) {
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                if (firebaseError != null) {
                                                    Log.e("UserModel.updateMyUser", firebaseError.getMessage());
                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            callback.callback(new Error(firebaseError.getMessage()), null);
                                                        }
                                                    });
                                                } else {
                                                    if (user.getPhone() != null) {
                                                        Log.d("UserModel.updateMyUser", "update my user(phone pending) success");
                                                        Phone phone = new Phone(authUID, ServerValue.TIMESTAMP);
                                                        Firebase phoneRef = phonesRef.child(user.getPhone());
                                                        phoneRef.setValue(phone, new Firebase.CompletionListener() {
                                                            @Override
                                                            public void onComplete(final FirebaseError firebaseError, Firebase firebase) {
                                                                if (firebaseError != null) {
                                                                    Log.e("UserModel.updateMyUser", firebaseError.getMessage());
                                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            callback.callback(new Error(firebaseError.getMessage()), null);
                                                                        }
                                                                    });
                                                                } else {
                                                                    Log.d("UserModel.updateMyUser", "update my user with phone success");
                                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            callback.callback(null, authUID);
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });

                                                    } else {
                                                        Log.d("UserModel.updateMyUser", "update my user without phone success");
                                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                callback.callback(null, authUID);
                                                            }
                                                        });
                                                    }

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

    public void getMyUser(final Callback<User> callback) {
        Log.d("UserModel.getMyUser", "Calling getUserById");
        getUserById(authUID, callback);
    }

    public void getUserById(final String userId, final Callback<User> callback) {
        new Thread() {
            @Override
            public void run() {
                userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        new Thread() {
                            @Override
                            public void run() {
                                final User user = dataSnapshot.getValue(User.class);
                                Log.d("UserModel.getUserById", String.format("get user by id %s success", userId));
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.callback(null, user);
                                    }
                                });
                            }
                        }.start();
                    }

                    @Override
                    public void onCancelled(final FirebaseError firebaseError) {
                        new Thread() {
                            @Override
                            public void run() {
                                Log.e("UserModel.getUserById", firebaseError.getMessage());
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

    public void getUserProfilePictureById(final String userId, final Callback<File> callback) {
        new Thread() {
            @Override
            public void run() {
                getUserById(userId, new Callback<User>() {
                    @Override
                    public void callback(final Error error, final User result) {
                        if (error != null) {
                            Log.e("UM.getUserProfPicById", String.format("getUserById failed userId %s", userId));
                        } else {
                            new Thread() {
                                @Override
                                public void run() {
                                    final String pictPath = PROFILE_PICTURE_PATH + result.getProfile_picture();
                                    awsManager.download(pictPath, new Callback<File>() {
                                        @Override
                                        public void callback(final Error error, final File result) {
                                            if (error != null) {
                                                Log.e("UM.getUserProfPicById", String.format("Download profile picture failed. Path : %s", pictPath));
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        callback.callback(error, null);
                                                    }
                                                });
                                            } else {
                                                Log.d("UM.getUserProfileById", String.format("Download profile picture success. Path : %s", pictPath));
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        callback.callback(null, result);
                                                    }
                                                });
                                            }
                                        }
                                    });

                                }
                            }.start();
                        }
                    }
                });
            }
        }.start();
    }

    public void getMyUserProfilePicture(final Callback<File> callback) {
        Log.d("UM.getMyUserProfPic", "reuse getUserProfilePictureById");
        getUserProfilePictureById(authUID, callback);
    }

    public void addMyFriendsByIds(final List<String> friendList, final Integer type, final Callback<String> callback) {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> friendMap = new UserFriend(type, ServerValue.TIMESTAMP).toMap();
                Map<String, Object> friends = new HashMap<String, Object>();
                for (String friend : friendList) {
                    friends.put(friend, friendMap);
                }

                myFriendsRef.updateChildren(friends, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(final FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e("UM.addMyFriendsByIds", firebaseError.getMessage());
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.callback(new Error(firebaseError.getMessage()), null);
                                }
                            });
                        } else {
                            Log.d("UM.addMyFriendsByIds", "success");
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.callback(null, "SUCCESS");
                                }
                            });
                        }
                    }
                });
            }
        }.start();
    }

    public void getMyFriends(final Callback<Map<String, UserFriend>> callback) {
        new Thread() {
            @Override
            public void run() {
                myFriendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("UM.getMyFriendsByIds", "success");
                        final Map<String, UserFriend> friends = new HashMap<String, UserFriend>();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            friends.put(child.getKey(), child.getValue(UserFriend.class));
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(null, friends);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(final FirebaseError firebaseError) {
                        Log.e("UM.getMyFriendsByIds", firebaseError.getMessage());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(new Error(firebaseError.getMessage()), null);
                            }
                        });
                    }
                });
            }
        }.start();
    }

    public void getUserIdByPhone(final String phoneUnsanitized, final Callback<String> callback) {
        new Thread() {
            @Override
            public void run() {
                final String phoneSanitized = Contact.sanitizePhoneNumber(phoneUnsanitized);
                Firebase phoneRef = phonesRef.child(phoneSanitized);
                phoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userId = null;
                        if (dataSnapshot.exists()) {
                            Phone phone = dataSnapshot.getValue(Phone.class);
                            Log.d("UM.getUserIdByPhone", String.format("Phone: %s UserId: %s", phoneSanitized, phone.getUser_id()));
                            userId = phone.getUser_id();
                        } else {
                            Log.d("UM.getUserIdByPhone", String.format("Phone: %s UserId not exist", phoneSanitized));
                        }
                        final String userIdFinal = userId;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(null, userIdFinal);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.e("UM.getUserIdByPhone", firebaseError.getMessage());
                        callback.callback(new Error(firebaseError.getMessage()), null);
                    }
                });
            }
        }.start();
    }

    public void isExistsMyFriendById(final String userId, final Callback<Boolean> callback) {
        new Thread() {
            @Override
            public void run() {
                Firebase myFriend = myFriendsRef.child(userId);
                myFriend.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d("UM.isExistsMyFriend", String.format("My Friend %s is exists", userId));
                        } else {
                            Log.d("UM.isExistsMyFriend", String.format("My Friend %s is not exists", userId));
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(null, dataSnapshot.exists());
                            }
                        });
                    }

                    @Override
                    public void onCancelled(final FirebaseError firebaseError) {
                        Log.e("UM.isExistsMyFriend", firebaseError.getMessage());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.callback(new Error(firebaseError.getMessage()), null);
                            }
                        });
                    }
                });
            }
        }.start();
    }

    public void addMyFriendsByContacts(final Callback<String> callback) {
        new Thread() {
            @Override
            public void run() {
                List<String> phoneList = Contact.getInstance().getAllPhoneNumber();
                for (final String phone : phoneList) {
                    getUserIdByPhone(phone, new Callback<String>() {
                        @Override
                        public void callback(final Error error, final String userId) {
                            new Thread() {
                                @Override
                                public void run() {
                                    if (error != null || userId == null) {
                                        if (error != null) {
                                            Log.e("UM.addFriendsByContacts", "error when getUserIdByPhone");

                                        } else {
                                            Log.d("UM.addFriendsByContacts", String.format("no userId with phone : %s", phone));
                                        }
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                callback.callback(error, null);
                                            }
                                        });
                                    } else {
                                        isExistsMyFriendById(userId, new Callback<Boolean>() {
                                            @Override
                                            public void callback(final Error error, final Boolean isExists) {
                                                new Thread() {
                                                    @Override
                                                    public void run() {
                                                        if (error != null || isExists) {
                                                            if (error != null) {
                                                                Log.e("UM.addFriendsByContacts", "error when isExistsMyFriendById");
                                                            } else {
                                                                Log.d("UM.addFriendsByContacts", String.format("My friend %s is already exists", userId));
                                                            }
                                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    callback.callback(error, null);
                                                                }
                                                            });
                                                        } else {
                                                            addMyFriendsByIds(Collections.singletonList(userId), 0, new Callback<String>() {
                                                                @Override
                                                                public void callback(final Error error, String result) {
                                                                    if (error != null) {
                                                                        Log.e("UM.addFriendsByContacts", "error when addMyFriendsByIds");
                                                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                callback.callback(error, null);
                                                                            }
                                                                        });
                                                                    } else {
                                                                        Log.d("UM.addFriendsByContacts", String.format("My Friend %s is added", userId));
                                                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                callback.callback(null, userId);
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
                                }
                            }.start();

                        }
                    });
                }
            }
        }.start();
    }
}
