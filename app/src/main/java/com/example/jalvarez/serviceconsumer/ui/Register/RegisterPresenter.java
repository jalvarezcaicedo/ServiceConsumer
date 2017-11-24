package com.example.jalvarez.serviceconsumer.ui.Register;

import android.util.Log;

import com.example.jalvarez.serviceconsumer.data.datamanager.DataManager;
import com.example.jalvarez.serviceconsumer.data.model.signup.SignUpRequest;
import com.example.jalvarez.serviceconsumer.ui.base.BasePresenter;
import com.example.jalvarez.serviceconsumer.util.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;

    RegisterPresenter() {
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

    void callServiceSignUp(SignUpRequest signUpRequest) {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callRegister(signUpRequest).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(loginResponse -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().sendToLogin();
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showAlertDialog(throwable.getMessage());
                    Log.i(getClass().getSimpleName(), "Transactional error");
                }));
    }
}
