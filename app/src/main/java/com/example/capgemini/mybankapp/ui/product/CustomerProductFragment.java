package com.example.capgemini.mybankapp.ui.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capgemini.mybankapp.R;
import com.example.capgemini.mybankapp.data.model.customerinfo.CustomerProductResponse;
import com.example.capgemini.mybankapp.ui.base.BaseFragment;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CustomerProductFragment extends BaseFragment implements CustomerProductView, TextWatcher {

    public static final String FRAGMENT_TAG = "FRAGMENT_CUSTOMER_PRODUCT";
    private CustomerProductPresenter customerProductPresenter;
    private TextView productData;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_customer_product, container, false);
        initUI(view);
        customerProductPresenter = new CustomerProductPresenter(getActivity());
        customerProductPresenter.attachView(this);

        return view;
    }

    private void initUI(android.view.View view) {
        productData = view.findViewById(R.id.customer_product_data);
        EditText productId = view.findViewById(R.id.txt_product_id);
        productId.addTextChangedListener(this);
    }

    @Override
    public void showCustomerProductData(CustomerProductResponse body) {
        if (body != null)
            productData.setText(body.toString());
        else
            productData.setText(getString(R.string.invalid_product_data));
    }

    //<editor-fold desc="TextWatcher">
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            if (!TextUtils.isEmpty(charSequence))
                customerProductPresenter.callCustomerProduct(charSequence.toString());
            else
                productData.setText(getString(R.string.no_product_data));
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    //</editor-fold>
}
