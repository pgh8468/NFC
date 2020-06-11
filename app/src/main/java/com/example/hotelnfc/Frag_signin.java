package com.example.hotelnfc;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Frag_signin extends Fragment implements MainActivity.onKeyBackPressedListener {

    View view;
    //TextInputLayout - 레이아웃 구성
    TextInputLayout textInputLayout_id;
    TextInputLayout textInputLayout_pw;
    TextInputLayout textInputLayout_pw_check;
    TextInputLayout textInputLayout_name;
    TextInputLayout textInputLayout_phone;
    TextInputLayout textInputLayout_email;

    //TextInputEditText - 회원에게 정보를 입력받음
    TextInputEditText textInputEditText_id;
    TextInputEditText textInputEditText_pw;
    TextInputEditText textInputEditText_pw_check;
    TextInputEditText textInputEditText_name;
    TextInputEditText textInputEditText_phone;
    TextInputEditText textInputEditText_email;

    //Button
    Button button_approval;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_sign_in, null);

        //TextInputLayout - 레이아웃 구성
        textInputLayout_id = view.findViewById(R.id.textInputLayout_id);
        textInputLayout_pw = view.findViewById(R.id.textInputLayout_pw);
        textInputLayout_pw_check = view.findViewById(R.id.textInputLayout_pw_check);
        textInputLayout_name = view.findViewById(R.id.textInputLayout_name);
        textInputLayout_phone = view.findViewById(R.id.textInputLayout_phone);
        textInputLayout_email = view.findViewById(R.id.textInputLayout_email);

        //TextInputEditText - 회원에게 정보를 입력받음
        textInputEditText_id = view.findViewById(R.id.textInputEditText_id);
        textInputEditText_pw = view.findViewById(R.id.textInputEditText_pw);
        textInputEditText_pw_check = view.findViewById(R.id.textInputEditText_pw_check);
        textInputEditText_name = view.findViewById(R.id.textInputEditText_name);
        textInputEditText_phone = view.findViewById(R.id.textInputEditText_phone);
        textInputEditText_email = view.findViewById(R.id.textInputEditText_email);

        button_approval = view.findViewById(R.id.btn_finish);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);

        button_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = textInputEditText_id.getText().toString();
                String pw = textInputEditText_pw.getText().toString();
                String pw_check = textInputEditText_pw_check.getText().toString();
                String name = textInputEditText_name.getText().toString();
                String phone = textInputEditText_phone.getText().toString();
                String email = textInputEditText_email.getText().toString();

                try {

                    Activity act = getActivity();
                    Log.e("test", new DupInfoCheck().execute(new URL_make("check_id").makeURL(), id, "ID").get());

                    if( id.length() ==0 || pw.length() ==0 || pw_check.length() ==0 || name.length() ==0 || phone.length()==0 || email.length() ==0){
                        Toast.makeText(act,"입력되지 않은 정보를 입력해주세요.", Toast.LENGTH_LONG).show();
                    }

                    else if( id.length()<4){
                        Toast.makeText(act,"아이디는 최소 5자리 이상이어야 합니다.", Toast.LENGTH_LONG).show();
                    }

                    else if( !(pw.equals(pw_check)) ){
                        Toast.makeText(act,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    }

                    else if ( (new DupInfoCheck().execute(new URL_make("check_id").makeURL(), id, "ID").get()).equals(id)){
                        Toast.makeText(act,"중복된 아이디가 이미 존재합니다.", Toast.LENGTH_LONG).show();
                    }

                    else if( (new DupInfoCheck().execute(new URL_make("check_email").makeURL(), email, "Email").get()).equals(email) ){
                        Toast.makeText(act,"중복된 email 이 이미 존재합니다.", Toast.LENGTH_LONG).show();
                    }

                    else if ( (new DupInfoCheck().execute(new URL_make("check_tel").makeURL(), phone, "Tel").get()).equals(phone) ){
                        Toast.makeText(act,"중복된 phone 이 이미 존재합니다.", Toast.LENGTH_LONG).show();
                    }

                    else{

                        URL_make url_make = new URL_make("add_user");
                        String inputURL = url_make.makeURL();
                        String response = new AddUserInfoToServer().execute(inputURL, id, pw, name, phone, email).get();

                        //서버에서 받아온 값의 유효성 검증
                        if(response.equals("1")){
                            Toast.makeText(act,"참 잘했어요.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(act,"error check to server.", Toast.LENGTH_LONG).show();
                        }

                    }


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    public class AddUserInfoToServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write("UserID="+params[1]+"&UserPW="+params[2]+"&UserName="+params[3]+"&UserTel="+params[4]+"&UserEmail="+params[5]);
                bw.flush();
                bw.close();

                InputStreamReader is = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(is);
                String results = "";

                while(true){
                    results = reader.readLine();
                    if(results ==null){
                        break;
                    }output.append(results);
                }

            } catch (MalformedURLException e) { //url error
                e.printStackTrace();
            } catch (IOException e) { // http url connection
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    public class DupInfoCheck extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(con.getOutputStream());

                if(params[2].equals("ID"))
                    dos.writeBytes("UserID="+params[1]);
                else if(params[2].equals("Email"))
                    dos.writeBytes("UserEmail="+params[1]);
                else
                    dos.writeBytes("UserTel="+params[1]);
                dos.flush();
                dos.close();

                InputStreamReader is = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(is);
                String results = "";

                while(true){
                    results = reader.readLine();
                    if(results == null){
                        break;
                    }output.append(results);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    @Override
    public void onBackKey() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setOnKeyBackPressedListener(null);
        mainActivity.onBackPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnKeyBackPressedListener(this);
    }

}


