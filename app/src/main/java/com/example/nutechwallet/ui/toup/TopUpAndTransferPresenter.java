package com.example.nutechwallet.ui.toup;

import com.example.nutechwallet.core.repo.Repo;
import com.example.nutechwallet.model.NutechBaseResponse;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.model.toupandtransfer.TopUpAndTransferBody;
import com.example.nutechwallet.ui.toup.contract.TopUpAndTransferPresenterContract;
import com.example.nutechwallet.ui.toup.contract.TopUpAndTransferViewContract;
import com.example.nutechwallet.util.mvp.PostCallback;

import java.io.IOException;

public class TopUpAndTransferPresenter implements TopUpAndTransferPresenterContract {
    private final Repo repo;
    private final TopUpAndTransferViewContract viewContract;

    public TopUpAndTransferPresenter(Repo repo, TopUpAndTransferViewContract viewContract) {
        this.repo = repo;
        this.viewContract = viewContract;
        viewContract.setPresenter(this);
    }

    @Override
    public void doTopUp(TopUpAndTransferBody body) {
        viewContract.showContentLoading();
        repo.doTopUp(body, new PostCallback() {
            @Override
            public void onEntityPosted(Object var1) {
                viewContract.doTransactionSuccessfully("Top Up Saldo Anda Berhasil");
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
                        viewContract.doTransactionFailed(throwable.toString());
                    }
                }
                viewContract.hideContentLoading();
            }
        });
    }

    @Override
    public void doTransfer(TopUpAndTransferBody body) {
        repo.doTransfer(body, new PostCallback() {
            @Override
            public void onEntityPosted(Object var1) {
                viewContract.doTransactionSuccessfully("Transfer Saldo Anda Berhasil");
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
                        viewContract.doTransactionFailed(throwable.toString());
                    }
                }
                viewContract.hideContentLoading();
            }
        });
    }
}
