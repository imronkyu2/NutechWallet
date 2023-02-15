package com.example.nutechwallet.core.networking;

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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("registration")
    Call<NutechBaseResponse<UserResponse>> doRegistration(@Body UserBody user);

    @POST("login")
    Call<NutechBaseResponse<LoginResponse>> doLogin(@Body LoginBody loginBody);

    @POST("updateProfile")
    Call<NutechBaseResponse<UpdateProfileResponse>> doUpdateProfile(@Body UpdateProfileBody updateProfileBody, @Header("Authorization") String authHeader);

    @GET("getProfile")
    Call<NutechBaseResponse<UserResponse>> doGetUser(@Header("Authorization") String authHeader);

    @POST("topup")
    Call<NutechBaseResponse> doTopUp(@Body TopUpAndTransferBody topUpBody, @Header("Authorization") String authHeader);

    @POST("transfer")
    Call<NutechBaseResponse> doTransfer(@Body TopUpAndTransferBody transferBody, @Header("Authorization") String authHeader);

    @GET("transactionHistory")
    Call<ListBaseResponse<TransactionHistoryResponse>> doGetTransactionHistory(@Header("Authorization") String authHeader);

    @GET("balance")
    Call<NutechBaseResponse<BalanceResponse>> doGetBalance(@Header("Authorization") String authHeader);
}
