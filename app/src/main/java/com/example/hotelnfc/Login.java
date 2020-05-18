package com.example.hotelnfc;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextInputLayout textInputLayout_login_id; //ID입력
    TextInputLayout textInputLayout_login_pw; //PW입력
    TextInputEditText Edit_Login_Id, Edit_Login_Pw;
    Button button_signin, button_signup, button_find; //차례대로 회원가입, 로그인, ID/PW찾기

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputLayout_login_id = findViewById(R.id.textInputLayout_login_id);
        textInputLayout_login_pw = findViewById(R.id.textInputLayout_login_pw);
        button_signin = findViewById(R.id.button_signin);
        button_signup = findViewById(R.id.button_signup);
        button_find = findViewById(R.id.button_find);
        Edit_Login_Id = findViewById(R.id.Edit_Login_Id);
        Edit_Login_Pw = findViewById(R.id.Edit_Login_Pw);

        textInputLayout_login_id.setCounterEnabled(true);
        textInputLayout_login_pw.setCounterEnabled(true);
        textInputLayout_login_id.setCounterMaxLength(30);
        textInputLayout_login_pw.setCounterMaxLength(50);
    }

}
