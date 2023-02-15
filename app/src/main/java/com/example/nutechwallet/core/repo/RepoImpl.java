package com.example.nutechwallet.core.repo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nutechwallet.core.networking.ApiService;
import com.example.nutechwallet.core.networking.RetrofitHttpsCall;
import com.example.nutechwallet.core.singleton.SingletonLikeApp;
import com.example.nutechwallet.model.ListBaseResponse;
import com.example.nutechwallet.model.NutechBaseResponse;
import com.example.nutechwallet.model.balance.BalanceResponse;
import com.example.nutechwallet.model.history.TransactionHistoryResponse;
import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.model.toupandtransfer.TopUpAndTransferBody;
import com.example.nutechwallet.model.updateprofile.UpdateProfileBody;
import com.example.nutechwallet.model.updateprofile.UpdateProfileResponse;
import com.example.nutechwallet.util.Const;
import com.example.nutechwallet.util.mvp.LoadCallback;
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
        Call<NutechBaseResponse<UserResponse>> request = apiService.doRegistration(user);
        request.enqueue(new Callback<NutechBaseResponse<UserResponse>>() {
            @Override
            public void onResponse(@NonNull Call<NutechBaseResponse<UserResponse>> call,
                                   @NonNull Response<NutechBaseResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechBaseResponse<UserResponse> wrapper = response.body();
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
            public void onFailure(@NonNull Call<NutechBaseResponse<UserResponse>> call,
                                  @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });

    }

    @Override
    public void doLogin(LoginBody login, PostCallback callback) {
        Call<NutechBaseResponse<LoginResponse>> responseCall = apiService.doLogin(login);
        responseCall.enqueue(new Callback<NutechBaseResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<NutechBaseResponse<LoginResponse>> call,
                                   @NonNull Response<NutechBaseResponse<LoginResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechBaseResponse<LoginResponse> wrapper = response.body();
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
            public void onFailure(@NonNull Call<NutechBaseResponse<LoginResponse>> call,
                                  @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

    @Override
    public void doUpdateProfile(UpdateProfileBody updateProfileBody, PostCallback callback) {
        Call<NutechBaseResponse<UpdateProfileResponse>> responseCall = apiService.doUpdateProfile(updateProfileBody,
                SingletonLikeApp.getInstance().getSharedPreferences(mContext).getToken());
        responseCall.enqueue(new Callback<NutechBaseResponse<UpdateProfileResponse>>() {
            @Override
            public void onResponse(@NonNull Call<NutechBaseResponse<UpdateProfileResponse>> call,
                                   @NonNull Response<NutechBaseResponse<UpdateProfileResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechBaseResponse<UpdateProfileResponse> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onEntityPosted(wrapper.getData());
                        } else if (wrapper.getStatus() == Const.ResponseCodes.TOKEN_EXPIRED) {
                            callback.onErrorRequest(new Exception(wrapper.getMessage()));
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
            public void onFailure(@NonNull Call<NutechBaseResponse<UpdateProfileResponse>> call,
                                  @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

    @Override
    public void getProfile(LoadCallback<UserResponse> callback) {
        Call<NutechBaseResponse<UserResponse>> responseCall = apiService.doGetUser(
                SingletonLikeApp.getInstance().getSharedPreferences(mContext).getToken());
        responseCall.enqueue(new Callback<NutechBaseResponse<UserResponse>>() {
            @Override
            public void onResponse(@NonNull Call<NutechBaseResponse<UserResponse>> call,
                                   @NonNull Response<NutechBaseResponse<UserResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechBaseResponse<UserResponse> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onLoadedData(wrapper.getData());
                        } else if (wrapper.getStatus() == Const.ResponseCodes.TOKEN_EXPIRED) {
                            callback.onErrorRequest(new Exception(wrapper.getMessage()));
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
            public void onFailure(@NonNull Call<NutechBaseResponse<UserResponse>> call, @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

    @Override
    public void doTopUp(TopUpAndTransferBody topup, PostCallback callback) {
        Call<NutechBaseResponse> responseCall = apiService.doTopUp(topup,
                SingletonLikeApp.getInstance().getSharedPreferences(mContext).getToken());
        responseCall.enqueue(new Callback<NutechBaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<NutechBaseResponse> call, @NonNull Response<NutechBaseResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechBaseResponse<UserResponse> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onEntityPosted(wrapper);
                        } else if (wrapper.getStatus() == Const.ResponseCodes.TOKEN_EXPIRED) {
                            callback.onErrorRequest(new Exception(wrapper.getMessage()));
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
            public void onFailure(@NonNull Call<NutechBaseResponse> call, @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

    @Override
    public void doTransfer(TopUpAndTransferBody transfer, PostCallback callback) {
        Call<NutechBaseResponse> responseCall = apiService.doTransfer(transfer,
                SingletonLikeApp.getInstance().getSharedPreferences(mContext).getToken());
        responseCall.enqueue(new Callback<NutechBaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<NutechBaseResponse> call, @NonNull Response<NutechBaseResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechBaseResponse<UserResponse> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onEntityPosted(wrapper);
                        } else if (wrapper.getStatus() == Const.ResponseCodes.TOKEN_EXPIRED) {
                            callback.onErrorRequest(new Exception(wrapper.getMessage()));
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
            public void onFailure(@NonNull Call<NutechBaseResponse> call, @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

    @Override
    public void doGetTransactionHistory(LoadCallback<TransactionHistoryResponse> callback) {
        Call<ListBaseResponse<TransactionHistoryResponse>> responseCall = apiService.doGetTransactionHistory(
                SingletonLikeApp.getInstance().getSharedPreferences(mContext).getToken());
        responseCall.enqueue(new Callback<ListBaseResponse<TransactionHistoryResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ListBaseResponse<TransactionHistoryResponse>> call,
                                   @NonNull Response<ListBaseResponse<TransactionHistoryResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        ListBaseResponse<TransactionHistoryResponse> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onLoadedData(wrapper.getData());
                        } else if (wrapper.getStatus() == Const.ResponseCodes.TOKEN_EXPIRED) {
                            callback.onErrorRequest(new Exception(wrapper.getMessage()));
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
            public void onFailure(@NonNull Call<ListBaseResponse<TransactionHistoryResponse>> call,
                                  @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void doGetBalance(LoadCallback<UserResponse> callback) {
        Call<NutechBaseResponse<BalanceResponse>> responseCall = apiService.doGetBalance(
                SingletonLikeApp.getInstance().getSharedPreferences(mContext).getToken());
        responseCall.enqueue(new Callback<NutechBaseResponse<BalanceResponse>>() {
            @Override
            public void onResponse(@NonNull Call<NutechBaseResponse<BalanceResponse>> call,
                                   @NonNull Response<NutechBaseResponse<BalanceResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        NutechBaseResponse<BalanceResponse> wrapper = response.body();
                        assert wrapper != null;
                        if (wrapper.getStatus() == Const.ResponseCodes.SUCCESS) {
                            callback.onLoadedData(wrapper.getData());
                        } else if (wrapper.getStatus() == Const.ResponseCodes.TOKEN_EXPIRED) {
                            callback.onErrorRequest(new Exception(wrapper.getMessage()));
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
            public void onFailure(@NonNull Call<NutechBaseResponse<BalanceResponse>> call,
                                  @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }
}
