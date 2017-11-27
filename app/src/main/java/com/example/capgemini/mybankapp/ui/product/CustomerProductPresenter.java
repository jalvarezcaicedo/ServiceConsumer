package com.example.capgemini.mybankapp.ui.product;

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

public class CustomerProductPresenter extends BasePresenter<CustomerProductView> {

    private CompositeDisposable disposables;
    private DataManager dataManager;
    private Context context;

    CustomerProductPresenter(Context context) {
        this.context = context;
        this.dataManager = new DataManager();
    }

    @Override
    public void attachView(CustomerProductView mvpView) {
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

    void callCustomerProduct(String productNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        getMvpView().createProgressDialog();
        getMvpView().showProgressDialog(Constants.STRING_PLEASE_WAIT);
        disposables.add(dataManager.callCustomerProductInfo(context, productNumber).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(customerProductResponse -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showCustomerProductData(customerProductResponse.body());
                }, throwable -> {
                    getMvpView().hideProgressDialog();
                    getMvpView().showCustomerProductData(null);
                }));
    }
}
