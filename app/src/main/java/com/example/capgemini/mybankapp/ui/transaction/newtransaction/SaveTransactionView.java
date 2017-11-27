package com.example.capgemini.mybankapp.ui.transaction.newtransaction;

import com.example.capgemini.mybankapp.data.model.transaction.TransactionResponse;
import com.example.capgemini.mybankapp.ui.base.BaseView;

public interface SaveTransactionView extends BaseView {

    void saveTransaction(TransactionResponse transactionResponse);

    void showSnackBarResponse(String code, String response, boolean successful);

    void sendToTransactionUI();

}
