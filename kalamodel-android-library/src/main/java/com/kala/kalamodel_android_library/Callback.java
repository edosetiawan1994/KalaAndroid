package com.kala.kalamodel_android_library;

import android.content.Context;

/**
 * Created by Kala on 2/29/16.
 */
public interface Callback<T> {
    public void callback(Error error, T result);
}
