package com.example.nutechwallet.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.nutechwallet.core.repo.Repo;
import com.example.nutechwallet.core.repo.RepoImpl;

public class Injection {

    public static Repo provideRepository(@NonNull Context context) {
        // todo getSharedPreferences
        return new RepoImpl(context);
    }

}
