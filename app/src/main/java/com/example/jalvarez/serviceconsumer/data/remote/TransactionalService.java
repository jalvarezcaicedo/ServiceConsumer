package com.example.jalvarez.serviceconsumer.data.remote;

import com.example.jalvarez.serviceconsumer.data.model.login.Login;
import com.example.jalvarez.serviceconsumer.data.model.signup.SignUpRequest;
import com.example.jalvarez.serviceconsumer.data.model.signup.SignUpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class TransactionalService {

    public static RetrofitInterface newTransactionalService() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlConnection.TRANSACTIONAL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS).build())
                .build();
        return retrofit.create(RetrofitInterface.class);

    }

    public interface RetrofitInterface {
        @POST("/login")
        Observable<Response<Login>> loginCall(@Body Login login);

        @POST("/api/1.0/customer/sign-up")
        Observable<Response<SignUpResponse>> signUpCall(@Body SignUpRequest signUpRequest);
    }
}
