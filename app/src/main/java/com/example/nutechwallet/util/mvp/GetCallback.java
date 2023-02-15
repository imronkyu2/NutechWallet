package com.example.nutechwallet.util.mvp;

public interface GetCallback<T> extends ErrorCallback {
    void onEntityLoaded(T var1);

    void onDataNotAvailable();
}
