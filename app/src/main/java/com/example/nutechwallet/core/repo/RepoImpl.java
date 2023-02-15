package com.example.nutechwallet.core.repo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nutechwallet.core.networking.ApiService;
import com.example.nutechwallet.core.networking.RetrofitHttpsCall;
import com.example.nutechwallet.model.BaseResponse;
import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.util.Const;
import com.example.nutechwallet.util.mvp.PostCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoImpl implements Repo {
    private final Context mContext;
    private final ApiService apiService;

    public RepoImpl(Context mContext) {
        this.mContext = mContext;
        this.apiService = RetrofitHttpsCall.getInstance(mContext).create(ApiService.class);
    }

    @Override
    public void doRegistration(UserBody user, PostCallback callback) {
        Call<BaseResponse<UserResponse>> request = apiService.doRegistration(user);
        request.enqueue(new Callback<BaseResponse<UserResponse>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<UserResponse>> call,
                                   @NonNull Response<BaseResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        BaseResponse<UserResponse> wrapper = response.body();
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
            public void onFailure(@NonNull Call<BaseResponse<UserResponse>> call,
                                  @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });

    }

    @Override
    public void doLogin(LoginBody login, PostCallback callback) {
        Call<BaseResponse<LoginResponse>> responseCall = apiService.doLogin(login);
        responseCall.enqueue(new Callback<BaseResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<LoginResponse>> call,
                                   @NonNull Response<BaseResponse<LoginResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        BaseResponse<LoginResponse> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onEntityPosted(wrapper.getData());
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
            public void onFailure(@NonNull Call<BaseResponse<LoginResponse>> call,
                                  @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

}
