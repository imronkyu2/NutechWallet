package com.example.nutechwallet.ui.toup.contract;

import com.example.nutechwallet.model.toupandtransfer.TopUpAndTransferBody;
import com.example.nutechwallet.util.mvp.BasePresenter;

public interface TopUpAndTransferPresenterContract extends BasePresenter {

    void doTopUp(TopUpAndTransferBody body);

    void doTransfer(TopUpAndTransferBody body);
}
