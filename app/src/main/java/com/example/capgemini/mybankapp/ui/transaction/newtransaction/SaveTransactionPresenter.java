package com.example.capgemini.mybankapp.ui.transaction.newtransaction;

import android.content.Context;

import com.example.capgemini.mybankapp.R;
import com.example.capgemini.mybankapp.data.datamanager.DataManager;
import com.example.capgemini.mybankapp.data.model.transaction.TransactionRequest;
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

public class SaveTransactionPresenter extends BasePresenter<SaveTransactionView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;
    private Context context;

    SaveTransactionPresenter(Context context) {
        this.context = context;
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(SaveTransactionView mvpView) {
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

    void callSaveTransaction(TransactionRequest transactionRequest) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callSaveTransaction(context, transactionRequest).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(saveTransactionResponse -> {
                    getMvpView().hideProgressDialog();
                    if (saveTransactionResponse.code() == 200)
                        getMvpView().saveTransaction(saveTransactionResponse.body());
                    else
                        getMvpView().showSnackBarResponse(String.valueOf(saveTransactionResponse.code()), saveTransactionResponse.message(), false)
                                ;
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showSnackBarResponse(String.valueOf(context.getString(R.string.save_rejected_code)), throwable.getMessage(), false);
                }));
    }
}
