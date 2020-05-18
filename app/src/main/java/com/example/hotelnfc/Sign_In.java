package com.example.hotelnfc;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Sign_In extends AppCompatActivity {

    //차례대로 id, pw, pw 확인, email 입력
    TextInputLayout textInputLayout_id, textInputLayout_pw, textInputLayout_pw_check, textInputLayout_email;
    TextInputEditText textInputEditText_id,textInputEditText_pw,textInputEditText_pw_check,textInputEditText_email;
    Button btn_finish;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        textInputLayout_id = findViewById(R.id.textInputLayout_id);
        textInputLayout_pw = findViewById(R.id.textInputLayout_pw);
        textInputLayout_pw_check = findViewById(R.id.textInputLayout_pw_check);
        textInputLayout_email = findViewById(R.id.textInputLayout_email);

        textInputEditText_id = findViewById(R.id.textInputEditText_id);
        textInputEditText_pw = findViewById(R.id.textInputEditText_pw);
        textInputEditText_pw_check = findViewById(R.id.textInputEditText_pw_check);
        textInputEditText_email = findViewById(R.id.textInputEditText_email);

        textInputLayout_id.setCounterEnabled(true);
        textInputLayout_pw.setCounterEnabled(true);
        textInputLayout_pw_check.setCounterEnabled(true);
        textInputLayout_email.setCounterEnabled(true);

        textInputLayout_id.setCounterMaxLength(16);
        textInputLayout_pw.setCounterMaxLength(16);
        textInputLayout_pw_check.setCounterMaxLength(16);
        textInputLayout_email.setCounterMaxLength(20);

        btn_finish = (Button) findViewById(R.id.btn_finish);
    }
}
