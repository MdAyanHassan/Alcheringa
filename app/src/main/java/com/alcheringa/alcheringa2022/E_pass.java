package com.alcheringa.alcheringa2022;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class E_pass extends AppCompatActivity {
    Boolean isE_pass_Generated=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epass);
        if(isE_pass_Generated){


        }
        else{
            generateORCode("gungun","Vipinjalothriya123@gmail.com","7011879379","0");
        }
    }
    private void generateORCode(String name, String email, String phno,String isValid_Pass) {
        String final_string = name + "," + email + "," + phno;
        String key="2ew1O7DDCKjIImXryfwM";
        try {
            String encrypt= AESCrypt.encrypt(key,final_string);
            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(encrypt, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                ((ImageView) findViewById(R.id.qr_code_image)).setImageBitmap(bmp);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }



    }
}