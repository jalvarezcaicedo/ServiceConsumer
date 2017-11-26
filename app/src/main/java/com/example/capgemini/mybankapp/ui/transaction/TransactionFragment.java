package com.example.capgemini.mybankapp.ui.transaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capgemini.mybankapp.R;
import com.example.capgemini.mybankapp.data.model.transaction.TransactionResponse;
import com.example.capgemini.mybankapp.ui.base.BaseFragment;
import com.example.capgemini.mybankapp.util.Constants;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends BaseFragment implements TransactionView, TextWatcher {

    public static final String FRAGMENT_TAG = "FRAGMENT_TRANSACTION";
    private SharedPreferences sharedPreferences;
    private TransactionPresenter transactionPresenter;
    private EditText prodNumberField;
    private RecyclerView recyclerTransactions;
    private TextView emptyView;
    private TransactionAdapter transactionAdapter;
    private ArrayList<TransactionResponse> transactionResponses;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        transactionPresenter = new TransactionPresenter(getActivity());
        transactionPresenter.attachView(this);
        setHasOptionsMenu(true);
        transactionResponses = new ArrayList<>();
        initUI(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tx, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_tx:

                break;
            default:
                Log.i(getClass().getSimpleName(), "No se reconoce la opcion indicada");
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI(android.view.View view) {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        emptyView = view.findViewById(R.id.empty_transaction);
        prodNumberField = view.findViewById(R.id.transaction_query_product);
        prodNumberField.addTextChangedListener(this);
        recyclerTransactions = view.findViewById(R.id.recycler_last_transactions);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerTransactions.setLayoutManager(llm);

        transactionAdapter = new TransactionAdapter(getActivity(), transactionResponses);
        transactionAdapter.notifyDataSetChanged();
        recyclerTransactions.setAdapter(transactionAdapter);
        recyclerTransactions.setHasFixedSize(true);
    }

    @Override
    public void showTransactionList(List<TransactionResponse> listTransaction) {
        verifyVisibilityDataState();
        transactionResponses.addAll(listTransaction);
        transactionAdapter.notifyDataSetChanged();
    }

    private boolean verifyVisibilityDataState() {
        if (transactionResponses.size() > 0) {
            recyclerTransactions.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            return true;
        } else {
            recyclerTransactions.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            return false;
        }
    }

    //<editor-fold desc="TextWatcher">
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            if (charSequence.length() > 0)
                transactionPresenter.callLastTransactions(sharedPreferences.getString(Constants.LIMIT, ""), charSequence.toString());
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    //</editor-fold>
}
