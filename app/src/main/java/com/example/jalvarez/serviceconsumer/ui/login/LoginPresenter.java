package com.example.jalvarez.serviceconsumer.ui.login;

import android.util.Log;

import com.example.jalvarez.serviceconsumer.data.datamanager.DataManager;
import com.example.jalvarez.serviceconsumer.data.model.login.Login;
import com.example.jalvarez.serviceconsumer.ui.base.BasePresenter;
import com.example.jalvarez.serviceconsumer.util.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;

    LoginPresenter() {
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

    void callServiceLogin(Login login) {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callLogin(login).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(loginResponse -> {
                    getMvpView().hideProgressDialog();
                    Log.i(getClass().getSimpleName(), "HEADERS ->" + loginResponse.headers().get("authorization"));
                    getMvpView().sendToMenu();
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    Log.i(getClass().getSimpleName(), "Transactional error");
                }));
    }
}
