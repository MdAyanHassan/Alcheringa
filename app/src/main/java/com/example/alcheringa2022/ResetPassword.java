package com.example.alcheringa2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ResetPassword extends AppCompatActivity {

    EditText email;
    Button sendOTP;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passowrd);

        email = findViewById(R.id.email);

        sendOTP = findViewById(R.id.reset_password);
        sendOTP.setOnClickListener(view -> {
            String emailAddress = email.getText().toString();
            boolean isEmailValid = Pattern.compile("^(.+)@(\\S+)$")
                    .matcher(emailAddress)
                    .matches();
            if(isEmailValid){
                /*Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                intent.putExtra("email",email.getText().toString());
                startActivity(intent);*/
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "Email sent.");
                                Toast.makeText(getApplicationContext(),"Password Reset Mail has been sent on your Email Address", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Your account does not exist", Toast.LENGTH_LONG).show();
                            }
                        });
            }else{
                Toast.makeText(getApplicationContext(),"Please enter a valid email", Toast.LENGTH_LONG).show();
            }
        });

        back=findViewById(R.id.back_button);
        back.setOnClickListener(view -> finish());
    }
}