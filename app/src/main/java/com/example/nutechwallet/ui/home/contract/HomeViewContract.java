package com.example.nutechwallet.ui.home.contract;

import com.example.nutechwallet.model.balance.BalanceResponse;
import com.example.nutechwallet.model.history.TransactionHistoryResponse;
import com.example.nutechwallet.util.mvp.BaseView;

import java.util.List;

public interface HomeViewContract extends BaseView<HomePresenterContract> {
    void doGetBalanceSuccessfully(BalanceResponse var1);

    void doGetBalanceFailed(String data_tidak_tersedia);

    void doGetTransactionHistorySuccessfully(List<TransactionHistoryResponse> var1);

    void doGetTransactionHistoryFailed(String toString);
}
