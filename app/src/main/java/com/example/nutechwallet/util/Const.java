package com.example.nutechwallet.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Const {
    public static final class ResponseCodes {
        public static final int SUCCESS = 0;
        public static final int TOKEN_EXPIRED = 108;

    }

    public static final class Preferences{
        public static final String TOKEN = "NUTECH_TOKEN";
        public static final String USER_LOGIN = "NUTECH_USER_LOGIN";
    }

    public static final class Transaction{
        public static final String TRANSFER = "transfer";
        public static final String TOPUP = "topup";

    }

    public static String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
