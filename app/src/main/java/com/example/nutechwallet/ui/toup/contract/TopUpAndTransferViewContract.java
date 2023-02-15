package com.example.nutechwallet.ui.toup.contract;

import com.example.nutechwallet.util.mvp.BaseView;

public interface TopUpAndTransferViewContract extends BaseView<TopUpAndTransferPresenterContract> {
    void doTransactionFailed(String toString);

    void doTransactionSuccessfully(String toString);
}
