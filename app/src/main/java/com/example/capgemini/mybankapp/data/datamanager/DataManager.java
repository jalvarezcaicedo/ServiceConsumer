package com.example.capgemini.mybankapp.data.datamanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.capgemini.mybankapp.data.model.customerinfo.CustomerInformationResponse;
import com.example.capgemini.mybankapp.data.model.customerinfo.CustomerProductResponse;
import com.example.capgemini.mybankapp.data.model.login.Login;
import com.example.capgemini.mybankapp.data.model.signup.SignUpRequest;
import com.example.capgemini.mybankapp.data.model.signup.SignUpResponse;
import com.example.capgemini.mybankapp.data.model.transaction.TransactionResponse;
import com.example.capgemini.mybankapp.data.remote.TransactionalService;
import com.example.capgemini.mybankapp.util.Constants;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;

public class DataManager {

    public Observable<Response<Login>> callLogin(Context context, Login login) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return TransactionalService.newTransactionalService(context).loginCall(login);
    }

    public Observable<Response<SignUpResponse>> callRegister(Context context, SignUpRequest signUpRequest) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return TransactionalService.newTransactionalService(context).signUpCall(signUpRequest);
    }

    public Observable<Response<CustomerInformationResponse>> callCustomerById(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        String url = "/api/1.0/customer/" + sharedPreferences.getString(Constants.CUSTOMER_ID, "") + "/get";
        String authorization = sharedPreferences.getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService(context).customerByIdCall(url, authorization);
    }

    public Observable<Response<CustomerProductResponse>> callCustomerProduct(Context context, String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        String url = "/api/1.0/product/" + sharedPreferences.getString(Constants.CUSTOMER_ID, "") + "/" + productNumber + "/get";
        String authorization = sharedPreferences.getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService(context).customerProductCall(url, authorization);
    }

    public Observable<Response<List<TransactionResponse>>> callTransaction(Context context, String limit, String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        String url = "/api/1.0/transaction/" + limit + "/" + sharedPreferences.getString(Constants.CUSTOMER_ID, "") + "/" + productNumber;
        String authorization = sharedPreferences.getString(Constants.AUTHORIZATION_HEADER, "");

        return TransactionalService.newTransactionalService(context).lastTransactionsCall(url, authorization);
    }

}
