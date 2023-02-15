package com.example.nutechwallet.ui.home;

import com.example.nutechwallet.core.repo.Repo;
import com.example.nutechwallet.model.ListBaseResponse;
import com.example.nutechwallet.model.balance.BalanceResponse;
import com.example.nutechwallet.model.history.TransactionHistoryResponse;
import com.example.nutechwallet.ui.home.contract.HomePresenterContract;
import com.example.nutechwallet.ui.home.contract.HomeViewContract;
import com.example.nutechwallet.util.mvp.LoadCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomePresenterContract {
    private final Repo repo;
    private final HomeViewContract viewContract;

    public HomePresenter(Repo repo, HomeViewContract homeView) {
        this.repo = repo;
        this.viewContract = homeView;
        viewContract.setPresenter(this);
    }

    @Override
    public void doGetBalance() {
        viewContract.showContentLoading();
        repo.doGetBalance(new LoadCallback<BalanceResponse>() {
            @Override
            public void onLoadedData(Object var1) {
                viewContract.doGetBalanceSuccessfully((BalanceResponse) var1);
                viewContract.hideContentLoading();
            }

            @Override
            public void onDataNotAvailable() {
                viewContract.doGetBalanceFailed("Data tidak tersedia");
                viewContract.hideContentLoading();
            }

            @Override
            public void onErrorRequest(Throwable throwable) {
                String string = throwable.toString();
                if(throwable instanceof IOException) {
                    viewContract.showConnectionFailed();
                }else {
                    if (string.equals("500")){
                        viewContract.showConnectionError();
                    }else {
                        viewContract.doGetBalanceFailed(throwable.toString());
                    }
                }
                viewContract.hideContentLoading();
            }
        });
    }

    @Override
    public void doGetTransactionHistory() {
        viewContract.showContentLoading();
        repo.doGetTransactionHistory(new LoadCallback<ListBaseResponse<TransactionHistoryResponse>>() {
            @Override
            public void onLoadedData(Object var1) {
                viewContract.doGetTransactionHistorySuccessfully((List<TransactionHistoryResponse>) var1);
                viewContract.hideContentLoading();
            }

            @Override
            public void onDataNotAvailable() {
                viewContract.doGetTransactionHistorySuccessfully(new ArrayList<>());
                viewContract.hideContentLoading();
            }

            @Override
            public void onErrorRequest(Throwable throwable) {
                String string = throwable.toString();
                if(throwable instanceof IOException) {
                    viewContract.showConnectionFailed();
                }else {
                    if (string.equals("500")){
                        viewContract.showConnectionError();
                    }else {
                        viewContract.doGetTransactionHistoryFailed(throwable.toString());
                    }
                }
                viewContract.hideContentLoading();
            }
        });
    }
}
