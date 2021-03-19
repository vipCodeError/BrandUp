package com.vipcodeerror.brandup.ui.main.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.asdev.phoneedittext.PhoneEditText;
import com.vipcodeerror.brandup.R;

import java.util.Objects;

/*
* Phone Edit text does not work with koltin code
* so better stick with java implementaion
*
* */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        PhoneEditText myPhoneEditText = findViewById(R.id.phoneEditText);
        String code = myPhoneEditText.getDIAL_CODE();

        AppCompatButton sendOtpBtn = findViewById(R.id.send_otp_btn);

        sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, OtpVerficationActivity.class);
                intent.putExtra("phone_num", Objects.requireNonNull(myPhoneEditText.getText()).toString());
                startActivity(intent);

            }
        });


    }
}