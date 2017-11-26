package com.example.capgemini.mybankapp.ui.transaction;

import android.content.Context;

import com.example.capgemini.mybankapp.data.datamanager.DataManager;
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

public class TransactionPresenter extends BasePresenter<TransactionView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;
    private Context context;

    TransactionPresenter(Context context) {
        this.context = context;
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(TransactionView mvpView) {
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

    void callLastTransactions(String limit, String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callTransaction(context, limit, productNumber).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(lastTransactions -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showTransactionList(lastTransactions.body());
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showTransactionList(null);
                }));
    }
}
