package com.example.capgemini.mybankapp.ui.menu;

import android.content.Context;

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

public class MenuPresenter extends BasePresenter<MenuView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;
    private Context context;

    MenuPresenter(Context context) {
        this.context = context;
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(MenuView mvpView) {
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
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                }));
    }
}
