package com.example.jalvarez.serviceconsumer.ui.consumer;

import android.util.Log;

import com.example.jalvarez.serviceconsumer.data.datamanager.DataManager;
import com.example.jalvarez.serviceconsumer.ui.base.BasePresenter;
import com.example.jalvarez.serviceconsumer.util.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ConsumerPresenter extends BasePresenter<ConsumerView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;

    public ConsumerPresenter() {
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(ConsumerView mvpView) {
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

    void callServiceData() {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.getData().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(dataResponse -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showData(dataResponse);
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    Log.i(getClass().getSimpleName(), "Transactional error");
                }));
    }
}
