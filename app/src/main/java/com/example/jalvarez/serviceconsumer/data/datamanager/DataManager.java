package com.example.jalvarez.serviceconsumer.data.datamanager;

import com.example.jalvarez.serviceconsumer.data.model.login.Login;
import com.example.jalvarez.serviceconsumer.data.model.signup.SignUpRequest;
import com.example.jalvarez.serviceconsumer.data.model.signup.SignUpResponse;
import com.example.jalvarez.serviceconsumer.data.remote.TransactionalService;

import io.reactivex.Observable;
import retrofit2.Response;

public class DataManager {

    public Observable<Response<Login>> callLogin(Login login) {
        return TransactionalService.newTransactionalService().loginCall(login);
    }

    public Observable<Response<SignUpResponse>> callRegister(SignUpRequest signUpRequest) {
        return TransactionalService.newTransactionalService().signUpCall(signUpRequest);
    }

}
