package com.example.nutechwallet.ui.toup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutechwallet.R;
import com.example.nutechwallet.databinding.ActivityTopUpAndTransferBinding;
import com.example.nutechwallet.model.toupandtransfer.TopUpAndTransferBody;
import com.example.nutechwallet.ui.home.HomeActivity;
import com.example.nutechwallet.ui.toup.contract.TopUpAndTransferPresenterContract;
import com.example.nutechwallet.ui.toup.contract.TopUpAndTransferViewContract;
import com.example.nutechwallet.util.Const;
import com.example.nutechwallet.util.Injection;

public class TopUpAndTransferActivity extends AppCompatActivity implements TopUpAndTransferViewContract {
    private ActivityTopUpAndTransferBinding binding;
    private TopUpAndTransferPresenterContract presenter;
    private boolean topupStatus;
    private int saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopUpAndTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new TopUpAndTransferPresenter(
                Injection.provideRepository(getApplicationContext()),
                TopUpAndTransferActivity.this);

        topupStatus = getIntent().getExtras().getBoolean("TopUp");
        saldo = getIntent().getExtras().getInt("Saldo");

        showContentActivity();
        actionButton();


    }


    private void showContentActivity() {
        binding.balanceTV.setText(Const.formatRupiah((double) saldo));
        if (topupStatus) {
            binding.tvTitle.setText(getText(R.string.topup_nutech));
        }else {
            binding.tvTitle.setText(getText(R.string.transfer_nutech));
        }
    }

    private void actionButton() {
        binding.tvTitle.setOnClickListener(v ->{
            backToHomeActivity();
        });

        binding.btnTransaction.setOnClickListener(v->{
            String total = binding.etTotal.getText().toString();
            if (total.isEmpty()){
                Toast.makeText(TopUpAndTransferActivity.this, "Anda belum memasukkan nominal", Toast.LENGTH_LONG).show();
            }else {
                TopUpAndTransferBody body = new TopUpAndTransferBody();
                body.setAmount(Integer.parseInt(total));
                if (topupStatus) {
                    presenter.doTopUp(body);
                } else {
                    boolean request = saldo > Integer.parseInt(total);
                    if (request) {
                        presenter.doTransfer(body);
                    }else {
                        Toast.makeText(TopUpAndTransferActivity.this, "Maaf saldo anda tidak mencukupi untuk melakukan transfer", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void setPresenter(TopUpAndTransferPresenterContract var1) {
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
    public void doTransactionFailed(String toString) {
        Toast.makeText(this, toString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void doTransactionSuccessfully(String toString) {
        Toast.makeText(this, toString, Toast.LENGTH_LONG).show();
        backToHomeActivity();
    }

    private void backToHomeActivity() {
        Intent intent = new Intent(TopUpAndTransferActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}