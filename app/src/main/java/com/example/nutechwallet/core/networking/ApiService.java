package com.example.nutechwallet.core.networking;

import com.example.nutechwallet.model.BaseResponse;
import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.model.updateprofile.UpdateProfileBody;
import com.example.nutechwallet.model.updateprofile.UpdateProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("registration")
    Call<BaseResponse<UserResponse>> doRegistration(@Body UserBody user);

    @POST("login")
    Call<BaseResponse<LoginResponse>> doLogin(@Body LoginBody loginBody);

    @POST("updateProfile")
    Call<BaseResponse<UpdateProfileResponse>> doUpdateProfile(@Body UpdateProfileBody updateProfileBody, @Header("Authorization") String authHeader);
}
