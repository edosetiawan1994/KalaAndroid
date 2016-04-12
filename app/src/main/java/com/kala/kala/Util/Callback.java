package com.kala.kala.Util;

/**
 * Created by Kala on 2/29/16.
 */
public interface Callback<T> {
    public void callback(Error error, T result);
}
