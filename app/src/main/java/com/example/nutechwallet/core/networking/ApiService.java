package com.example.nutechwallet.core.networking;

import com.example.nutechwallet.model.NutechResponse;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserRegistered;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("registration")
    Call<NutechResponse<UserRegistered>> doRegistration(@Body UserBody user);
}
