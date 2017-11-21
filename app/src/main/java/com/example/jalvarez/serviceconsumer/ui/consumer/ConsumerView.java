package com.example.jalvarez.serviceconsumer.ui.consumer;

import com.example.jalvarez.serviceconsumer.data.model.DataResponse;
import com.example.jalvarez.serviceconsumer.ui.base.BaseView;

/**
 * Created by jalvarez on 21/11/2017.
 */

public interface ConsumerView extends BaseView {

    void showData(DataResponse dataResponse);
}
