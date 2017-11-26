package com.example.capgemini.mybankapp.ui.product;

import com.example.capgemini.mybankapp.data.model.customerinfo.CustomerProductResponse;
import com.example.capgemini.mybankapp.ui.base.BaseView;

public interface CustomerProductView extends BaseView {

    void showCustomerProductData(CustomerProductResponse body);

}
