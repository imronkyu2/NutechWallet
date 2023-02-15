package com.example.nutechwallet.util.mvp;

public interface LoadCallback<T> extends ErrorCallback {
    void onLoadedData(Object var1);

    void onDataNotAvailable();
}