package com.example.capgemini.mybankapp.ui.register;

import android.content.Context;
import android.util.Log;

import com.example.capgemini.mybankapp.data.datamanager.DataManager;
import com.example.capgemini.mybankapp.data.model.signup.SignUpRequest;
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

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;
    private Context context;

    RegisterPresenter(Context context) {
        this.context = context;
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(RegisterView mvpView) {
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

    void callServiceSignUp(SignUpRequest signUpRequest) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callRegister(context, signUpRequest).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(loginResponse -> {
                    getMvpView().hideProgressDialog();
                    if (loginResponse.code() != 200)
                        getMvpView().sendToLogin();
                    else {
                        getMvpView().createAlertDialog();
                        getMvpView().showAlertDialog(loginResponse.message());
                    }
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().createAlertDialog();
                    getMvpView().showAlertDialog(throwable.getMessage());
                    Log.i(getClass().getSimpleName(), "Transactional error");
                }));
    }
}
