package com.example.hotelnfc;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Frag_login extends Fragment {

    View view;

    TextInputLayout textInputLayoutID, textInputLayoutPW;
    TextInputEditText textInputEditTextID, textInputEditTextPW;
    Button button_signin, button_signup, button_find;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_login, null);

        textInputLayoutID = view.findViewById(R.id.textInputLayout_login_id);
        textInputLayoutPW = view.findViewById(R.id.textInputLayout_login_pw);

        textInputEditTextID = view.findViewById(R.id.Edit_Login_Id);
        textInputEditTextPW = view.findViewById(R.id.Edit_Login_Pw);

        button_signin = view.findViewById(R.id.button_signin);
        button_signup = view.findViewById(R.id.button_signup);
        button_find = view.findViewById(R.id.button_find);

        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputID = textInputEditTextID.getText().toString();
                String inputPW = textInputEditTextPW.getText().toString();
                Activity act = getActivity();

                try {
                    String login_result = new LoginToApp().execute(new URL_make("log_in").makeURL(), inputID, inputPW).get();

                    if( login_result.equals("2")){
                        Snackbar.make(v,"로그인 성공.", Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(act, MainActivity.class);
                        intent.putExtra("loginID",inputID);
                        startActivity(intent);
                        act.finish();
                    }
                    else{
                        Snackbar.make(v,"입력하신 값을 다시한번 확인해주세요.", Snackbar.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

    public class LoginToApp extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                dos.writeBytes("UserID="+params[1]+"&UserPW="+params[2]);
                dos.flush();
                dos.close();

                InputStreamReader is = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(is);
                String results = "";

                while (true){
                    results = reader.readLine();
                    if(results == null){
                        break;
                    }output.append(results);
                }

                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

}
