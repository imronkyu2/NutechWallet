package com.example.nutechwallet.model.toupandtransfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopUpAndTransferBody {

    @JsonProperty("amount")
    private int amount;

    public TopUpAndTransferBody() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}