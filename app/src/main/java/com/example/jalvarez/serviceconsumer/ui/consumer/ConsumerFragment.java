package com.example.jalvarez.serviceconsumer.ui.consumer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jalvarez.serviceconsumer.data.model.DataResponse;
import com.example.jalvarez.serviceconsumer.ui.base.BaseFragment;

/**
 * Created by jalvarez on 21/11/2017.
 */

public class ConsumerFragment extends BaseFragment implements ConsumerView{

    ConsumerPresenter consumerPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        consumerPresenter = new ConsumerPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showData(DataResponse dataResponse) {

    }
}
