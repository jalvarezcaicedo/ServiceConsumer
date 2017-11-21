package com.example.jalvarez.serviceconsumer.ui.base;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface BaseView {

    void createProgressDialog();

    void showProgressDialog(String message);

    void showProgressDialog(int message);

    void hideProgressDialog();

    void createAlertDialog();

    void showAlertDialog(int message);

    void showAlertDialog(String message);

}
