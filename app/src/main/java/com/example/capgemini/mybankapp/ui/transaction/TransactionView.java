package com.example.capgemini.mybankapp.ui.transaction;

import com.example.capgemini.mybankapp.data.model.transaction.TransactionResponse;
import com.example.capgemini.mybankapp.ui.base.BaseView;

import java.util.List;

public interface TransactionView extends BaseView {

    void showTransactionList(List<TransactionResponse> listTransaction);

}
