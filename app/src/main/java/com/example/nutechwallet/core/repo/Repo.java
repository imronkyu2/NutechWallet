package com.example.nutechwallet.core.repo;

import com.example.nutechwallet.model.ListBaseResponse;
import com.example.nutechwallet.model.balance.BalanceResponse;
import com.example.nutechwallet.model.history.TransactionHistoryResponse;
import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.model.register.UserResponse;
import com.example.nutechwallet.model.toupandtransfer.TopUpAndTransferBody;
import com.example.nutechwallet.model.updateprofile.UpdateProfileBody;
import com.example.nutechwallet.util.mvp.LoadCallback;
import com.example.nutechwallet.util.mvp.PostCallback;

public interface Repo {

    void doRegistration(UserBody user, PostCallback callback);

    void doLogin(LoginBody login, PostCallback callback);

    void doUpdateProfile(UpdateProfileBody updateProfileBody, PostCallback callback);

    void getProfile(LoadCallback<UserResponse> callback);

    void doTopUp(TopUpAndTransferBody topup, PostCallback callback);

    void doTransfer(TopUpAndTransferBody transfer, PostCallback callback);

    void doGetTransactionHistory(LoadCallback<ListBaseResponse<TransactionHistoryResponse>> callback);

    void doGetBalance(LoadCallback<BalanceResponse> callback);
}
