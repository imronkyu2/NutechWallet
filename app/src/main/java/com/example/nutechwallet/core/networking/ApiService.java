package com.example.nutechwallet.core.networking;

import com.example.nutechwallet.model.BaseResponse;
import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("registration")
    Call<BaseResponse<UserResponse>> doRegistration(@Body UserBody user);


    @POST("login")
    Call<BaseResponse<LoginResponse>> doLogin(@Body LoginBody loginBody);
}
