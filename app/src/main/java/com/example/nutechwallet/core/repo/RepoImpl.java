package com.example.nutechwallet.core.repo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nutechwallet.core.networking.ApiService;
import com.example.nutechwallet.core.networking.RetrofitHttpsCall;
import com.example.nutechwallet.model.NutechResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserRegistered;
import com.example.nutechwallet.util.Const;
import com.example.nutechwallet.util.mvp.PostCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoImpl implements Repo{
    private final Context mContext;
    private final ApiService apiService;

    public RepoImpl(Context mContext) {
        this.mContext = mContext;
        this.apiService = RetrofitHttpsCall.getInstance(mContext).create(ApiService.class);
    }

     @Override
    public void doRegistration(UserBody user, PostCallback callback) {
        Call<NutechResponse<UserRegistered>> request = apiService.doRegistration(user);
        request.enqueue(new Callback<NutechResponse<UserRegistered>>() {
            @Override
            public void onResponse(@NonNull Call<NutechResponse<UserRegistered>> call, @NonNull Response<NutechResponse<UserRegistered>> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechResponse<UserRegistered> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onEntityPosted(wrapper);
                        } else {
                            callback.onErrorRequest(new Exception(wrapper.getMessage()));
                        }
                    } catch (RuntimeException ex) {
                        Log.e("RuntimeException", ex.getMessage());
                        callback.onErrorRequest(new Exception(ex.getMessage()));
                    }
                } else {
                    callback.onErrorRequest(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<NutechResponse<UserRegistered>> call, @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });

    }

}
