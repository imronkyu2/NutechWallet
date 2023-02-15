package com.example.nutechwallet.ui.home.contract;

import com.example.nutechwallet.util.mvp.BasePresenter;

public interface HomePresenterContract extends BasePresenter {
    void doGetBalance();

    void doGetTransactionHistory();
}
