package com.example.nutechwallet.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nutechwallet.core.singleton.SingletonLikeApp;
import com.example.nutechwallet.databinding.ActivityHomeBinding;
import com.example.nutechwallet.databinding.IncludeCardHomeBinding;
import com.example.nutechwallet.databinding.IncludeHeaderHomeBinding;
import com.example.nutechwallet.databinding.IncludeMenuHomeBinding;
import com.example.nutechwallet.model.balance.BalanceResponse;
import com.example.nutechwallet.model.history.TransactionHistoryResponse;
import com.example.nutechwallet.model.login.LoginResponse;
import com.example.nutechwallet.ui.home.adapter.TransactionHistoryAdapter;
import com.example.nutechwallet.ui.home.contract.HomePresenterContract;
import com.example.nutechwallet.ui.home.contract.HomeViewContract;
import com.example.nutechwallet.ui.login.LoginActivity;
import com.example.nutechwallet.ui.login.LoginPresenter;
import com.example.nutechwallet.util.Const;
import com.example.nutechwallet.util.Injection;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeViewContract {
    private ActivityHomeBinding binding;
    private IncludeHeaderHomeBinding headerBinding;
    private IncludeCardHomeBinding cardBinding;
    private IncludeMenuHomeBinding menuBinding;
    private HomePresenterContract presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new HomePresenter(Injection.provideRepository(getApplicationContext()), HomeActivity.this);
        initialization();
        headerLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.doGetBalance();
        presenter.doGetTransactionHistory();
    }

    private void initialization() {
        headerBinding = binding.headerLayout;
        cardBinding = binding.includeCardHome;
        menuBinding = binding.includeMenu;
    }
    @SuppressLint("SetTextI18n")
    private void headerLayout() {
        LoginResponse login = SingletonLikeApp.getInstance().getSharedPreferences(getApplicationContext()).getUser();
        headerBinding.tvUserName.setText(login.getFirstName() +" "+ login.getLastName());

        headerBinding.imageButton.setOnClickListener(v ->{

        });
    }

    @Override
    public void setPresenter(HomePresenterContract var1) {
        this.presenter = var1;
    }

    @Override
    public void showConnectionError() {
        Toast.makeText(this, "Server mengalami gangguan", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showContentLoading() {
        binding.animateView.playAnimation();
        binding.animateView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentLoading() {
        binding.animateView.setVisibility(View.GONE);
        binding.animateView.pauseAnimation();
    }

    @Override
    public void showConnectionFailed() {
        Toast.makeText(this, "Terjadi kesalahan koneksi", Toast.LENGTH_LONG).show();
    }

    @Override
    public void doGetBalanceSuccessfully(BalanceResponse var1) {
        cardBinding.balanceTV.setText(Const.formatRupiah((double) var1.getBalance()));
    }

    @Override
    public void doGetBalanceFailed(String data_tidak_tersedia) {
        cardBinding.balanceTV.setText(Const.formatRupiah((double) 0));
    }

    @Override
    public void doGetTransactionHistorySuccessfully(List<TransactionHistoryResponse> var1) {
        binding.recyclerTransaction.setHasFixedSize(true);
        binding.recyclerTransaction.setLayoutManager(new LinearLayoutManager(this));
        TransactionHistoryAdapter brandAdapter = new TransactionHistoryAdapter(getApplicationContext(), var1);
        binding.recyclerTransaction.setAdapter(brandAdapter);
    }

    @Override
    public void doGetTransactionHistoryFailed(String toString) {

    }
}