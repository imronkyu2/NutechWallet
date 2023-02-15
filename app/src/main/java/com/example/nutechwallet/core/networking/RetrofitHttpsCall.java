package com.example.nutechwallet.core.networking;

import android.content.Context;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHttpsCall {

    private static final String BASE_URL = "https://tht-api.nutech-integrasi.app/";
    private static RetrofitHttpsCall instanceHttpsCall = null;
    protected Retrofit clientHttpsCall;

    public static RetrofitHttpsCall getInstance(Context context) {
        if (instanceHttpsCall == null) {
            instanceHttpsCall = new RetrofitHttpsCall(context, BASE_URL);
        }

        return instanceHttpsCall;
    }

    public RetrofitHttpsCall(Context context, String baseUrl) {
        OkHttpClient.Builder httpClientHttpsCall = new OkHttpClient.Builder();

        httpClientHttpsCall.addInterceptor(chain -> {
            Request originalHttpsCall = chain.request();
            Request.Builder requestBuilder = originalHttpsCall.newBuilder()
                    .header("Cache-Control", "no-cache"); // <-- this is the important line
            Request requestHttpsCall = requestBuilder.build();
            return chain.proceed(requestHttpsCall);
        });


        httpClientHttpsCall.readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(60, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = httpClientHttpsCall.build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        clientHttpsCall = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addConverterFactory(new Converter.Factory() {
                    @NonNull
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
                        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
                        return (Converter<ResponseBody, Object>) body -> {
                            if (body.contentLength() == 0) return null;
                            return delegate.convert(body);
                        };
                    }
                })
                .client(okHttpClient)
                .build();

        clientHttpsCall.callbackExecutor();
    }

    public Retrofit getClientHttpsCall() {
        return clientHttpsCall;
    }

    public <T> T create(final Class<T> serviceHttpsCall) {
        return clientHttpsCall.create(serviceHttpsCall);
    }
}
