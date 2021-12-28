package com.example.alcheringa2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
import dev.shreyaspatil.easyupipayment.model.TransactionStatus;

public class Category_Activity extends AppCompatActivity implements PaymentStatusListener {
    private EasyUpiPayment easyUpiPayment;

    Button student,Staff,outsider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String transcId = df.format(c);
        student=findViewById(R.id.IITG_Student);


        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getApplicationContext(), ""+transcId, Toast.LENGTH_SHORT).show();
                makePayment("1.00","riteshkumard149@okhdfcbank","Ritesh Kumar","Testing our payment method",transcId);
            }
        });

    }
    private void makePayment(String amount, String upi, String name, String desc, String transactionId)  {

        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .setPayeeVpa(upi)
                .setPayeeName(name)
                .setPayeeMerchantCode("1234")
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionId)
                .setDescription(desc)
                .setAmount(amount);

        try {
            easyUpiPayment = builder.build();
            easyUpiPayment.startPayment();
            easyUpiPayment.setPaymentStatusListener(this);
        } catch (AppNotFoundException e) {
            toast("Error: " + e.toString());
            Log.d("Payment",e.toString());

        }


        // Start payment / transaction
    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {

        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }

    }

    @Override
    public void onTransactionCancelled() {
        // this method is called when transaction is cancelled.
        Toast.makeText(getApplicationContext(), "Transaction cancelled..", Toast.LENGTH_SHORT).show();
    }
    private void onTransactionSuccess() {
        // Payment Success
        toast("Success");
    }

    private void onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted");
    }

    private void onTransactionFailed() {
        // Payment Failed
        toast("Failed");
    }
    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}