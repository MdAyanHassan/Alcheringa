package com.example.alcheringa2022;

import android.app.Activity;
import android.util.Log;

import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;


public class MerchantActivity extends Activity implements PaymentResultWithDataListener {
    final String TAG = "MerchantActivityLog";


    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        Log.d(TAG, "onPaymentFailure");

    }

/*    @Override
    public void onPaymentError(int code, String response) {
        *//**
         * Add your logic here for a failed payment response
         *//*
    }*/

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {

        Log.d(TAG, "onPaymentSuccess: ss");

    }
}
