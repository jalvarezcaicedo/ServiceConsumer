package com.example.jalvarez.serviceconsumer.ui.consumer;

import com.example.jalvarez.serviceconsumer.data.model.DataResponse;
import com.example.jalvarez.serviceconsumer.data.model.Login;
import com.example.jalvarez.serviceconsumer.ui.base.BaseView;

import java.util.List;

public interface ConsumerView extends BaseView {

    void showData(List<DataResponse> dataResponse);

    void showDataLogin(Login login);

}
