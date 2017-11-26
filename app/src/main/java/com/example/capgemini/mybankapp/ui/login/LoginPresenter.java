package com.example.capgemini.mybankapp.ui.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.capgemini.mybankapp.data.datamanager.DataManager;
import com.example.capgemini.mybankapp.data.model.login.Login;
import com.example.capgemini.mybankapp.ui.base.BasePresenter;
import com.example.capgemini.mybankapp.util.Constants;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;
    private Context context;

    LoginPresenter(Context context) {
        this.context = context;
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(LoginView mvpView) {
        super.attachView(mvpView);
        if (disposables == null)
            disposables = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposables != null)
            disposables.clear();
    }

    void callServiceLogin(Login login) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callLogin(context, login).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(loginResponse -> {
                    getMvpView().hideProgressDialog();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString((login.getCustomerId()), "null").apply();
                    sharedPreferences.edit().putString(getSimpleAuthorization(loginResponse.headers().get(Constants.AUTHORIZATION_HEADER)), "null").apply();
                    getMvpView().sendToMenu();
                }, throwable -> getMvpView().hideProgressDialog()));
    }

    void callCustomerInfo() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callCustomerById(context).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(signUpResponse -> {
                    getMvpView().hideProgressDialog();
                    SharedPreferences.Editor sharedPreferencesEditor = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
                    sharedPreferencesEditor.putString(Constants.NAME, signUpResponse.body().getName());
                    sharedPreferencesEditor.putString(Constants.SURNAME, signUpResponse.body().getSurname());
                    sharedPreferencesEditor.putString(Constants.EMAIL, signUpResponse.body().getEmail());
                    sharedPreferencesEditor.putString(Constants.MOBILE, signUpResponse.body().getMobile());
                    sharedPreferencesEditor.apply();

                    //TODO toda la data de SharedPreference debe ser eliminada al regresar al login

                }, throwable -> getMvpView().hideProgressDialog()));
    }

    private String getSimpleAuthorization(String authorization) {
        return authorization.replace("Bearer", "").trim();
    }
}
