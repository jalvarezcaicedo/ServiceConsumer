package com.example.jalvarez.serviceconsumer.data.datamanager;

import com.example.jalvarez.serviceconsumer.data.model.DataResponse;
import com.example.jalvarez.serviceconsumer.data.remote.TransactionalService;

import java.util.List;

import io.reactivex.Observable;

public class DataManager {

    public Observable<List<DataResponse>> getData() {
        return TransactionalService.newTransactionalService().getData();
    }
}
