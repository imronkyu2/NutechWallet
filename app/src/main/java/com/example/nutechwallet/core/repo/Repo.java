package com.example.nutechwallet.core.repo;

import com.example.nutechwallet.model.login.LoginBody;
import com.example.nutechwallet.model.register.UserBody;
import com.example.nutechwallet.util.mvp.PostCallback;

public interface Repo {

    void doRegistration(UserBody user, PostCallback callback);

    void doLogin(LoginBody login, PostCallback callback);
}
