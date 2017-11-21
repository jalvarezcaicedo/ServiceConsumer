package com.example.jalvarez.serviceconsumer.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.jalvarez.serviceconsumer.R;

public class BaseFragment extends Fragment implements BaseView {

    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void createProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
    }

    @Override
    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void showProgressDialog(int message) {
        progressDialog.setMessage(getString(message));
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.cancel();
    }

    @Override
    public void createAlertDialog() {
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setCancelable(false);
    }

    @Override
    public void showAlertDialog(int message) {
        alertDialog.setMessage(message);
        showAlert();
    }

    @Override
    public void showAlertDialog(String message) {
        alertDialog.setMessage(message);
        showAlert();
    }

    private void showAlert() {
        alertDialog.setNeutralButton(R.string.accept, (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        alertDialog.show();
    }

}
