package com.example.hotelnfc;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_room_setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_room_setting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String ARG_PARAM1 = "param1";
    private static String ARG_PARAM2 = "param2";
    private static String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    String StartDay;
    String LastDay;
    String RoomInfo;
    String RoomGrade;
    String check_radio_btn = "0";

    ImageView setting_img;
    TextView setting_checkin, setting_checkout, notify_usage;
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton2,radioButton3,radioButton4;
    TextInputLayout textInputLayout_one, textInputLayout_two, textInputLayout_three, textInputLayout_four;
    TextInputEditText textInputEditText_one, textInputEditText_two, textInputEditText_three, textInputEditText_four;
    Button reserve_finish_button;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    View view;

    public Frag_room_setting() {
        // Required empty public constructor
    }
    public Frag_room_setting(String StartDay, String LastDay, String RoomInfo, String RoomGrade){
        this.StartDay = StartDay;
        this.LastDay = LastDay;
        this.RoomInfo =RoomInfo;
        this.RoomGrade = RoomGrade;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_room_setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_room_setting newInstance(String param1, String param2, String param3) {
        Frag_room_setting fragment = new Frag_room_setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_room_setting, null);

        Findid(); //findviewbyid

        //객실 이용객 수에 따른 번호 입력창 visibility 설정
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == 0) {
                    Snackbar.make(view,"인원 수를 선택해주세요.", Snackbar.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButton) { //1인
                    textInputLayout_one.setVisibility(view.VISIBLE);
                    textInputLayout_two.setVisibility(view.GONE);
                    textInputLayout_three.setVisibility(view.GONE);
                    textInputLayout_four.setVisibility(view.GONE);
                    check_radio_btn = "1";
                }
                else if (checkedId == R.id.radioButton2) { //2인
                    textInputLayout_one.setVisibility(view.VISIBLE);
                    textInputLayout_two.setVisibility(view.VISIBLE);
                    textInputLayout_three.setVisibility(view.GONE);
                    textInputLayout_four.setVisibility(view.GONE);
                    check_radio_btn = "2";
                }
                else if (checkedId == R.id.radioButton3) { //3인
                    textInputLayout_one.setVisibility(view.VISIBLE);
                    textInputLayout_two.setVisibility(view.VISIBLE);
                    textInputLayout_three.setVisibility(view.VISIBLE);
                    textInputLayout_four.setVisibility(view.GONE);
                    check_radio_btn = "3";
                }
                else if (checkedId == R.id.radioButton4) { //4인
                    textInputLayout_one.setVisibility(view.VISIBLE);
                    textInputLayout_two.setVisibility(view.VISIBLE);
                    textInputLayout_three.setVisibility(view.VISIBLE);
                    textInputLayout_four.setVisibility(view.VISIBLE);
                    check_radio_btn = "4";
                }
            }
        });


        //예약 완료 눌렀을 때 동작
        reserve_finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder bookphone = new StringBuilder();
                int inputflag = 0;
                String check_phone_state = null;
                Log.e("Stringf","Stringf");

                if(check_radio_btn.equals("0")){
                    Snackbar.make(v, "최소 1개 이상의 전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                }

                if(check_radio_btn.equals("1")){
                    Log.e("input1:",textInputEditText_one.getText().toString());
                    if(textInputEditText_one.getText().toString() == null){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if(textInputEditText_one.getText().toString().length()>13){
                        Snackbar.make(v, "전화번호 형식에 맞게 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if (textInputEditText_one.getText().toString().equals("")){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        bookphone.append(textInputEditText_one.getText().toString());
                        bookphone.append("/");
                        inputflag =1;
                    }

                }

                if(check_radio_btn.equals("2")){
                    if(textInputEditText_one.getText().toString() == null | textInputEditText_two.getText().toString() == null){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if(textInputEditText_one.getText().toString().length()>13 | textInputEditText_two.getText().toString().length()>13){
                        Snackbar.make(v, "전화번호 형식에 맞게 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if (textInputEditText_one.getText().toString().equals("") | textInputEditText_two.getText().toString().equals("")){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        bookphone.append(textInputEditText_one.getText().toString());
                        bookphone.append("/");
                        bookphone.append(textInputEditText_two.getText().toString());
                        inputflag=1;
                    }

                }

                if(check_radio_btn.equals("3")){
                    if(textInputEditText_one.getText().toString() == null | textInputEditText_two.getText().toString() == null |
                            textInputEditText_three.getText().toString() == null){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if(textInputEditText_one.getText().toString().length()>13 | textInputEditText_two.getText().toString().length()>13 |
                            textInputEditText_one.getText().toString().length()>13){
                        Snackbar.make(v, "전화번호 형식에 맞게 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if (textInputEditText_one.getText().toString().equals("") | textInputEditText_two.getText().toString().equals("") |
                            textInputEditText_three.getText().toString().equals("")){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        bookphone.append(textInputEditText_one.getText().toString());
                        bookphone.append("/");
                        bookphone.append(textInputEditText_two.getText().toString());
                        bookphone.append("/");
                        bookphone.append(textInputEditText_three.getText().toString());
                        inputflag =1;
                    }
                }

                if(check_radio_btn.equals("4")){
                    if(textInputEditText_one.getText().toString() == null | textInputEditText_two.getText().toString() == null |
                            textInputEditText_three.getText().toString() == null | textInputEditText_four.getText().toString() == null){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if(textInputEditText_one.getText().toString().length()>13 | textInputEditText_two.getText().toString().length()>13 |
                            textInputEditText_one.getText().toString().length()>13 | textInputEditText_three.getText().toString().length()>13){
                        Snackbar.make(v, "전화번호 형식에 맞게 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else if (textInputEditText_one.getText().toString().equals("") | textInputEditText_two.getText().toString().equals("") |
                            textInputEditText_three.getText().toString().equals("") | textInputEditText_four.getText().toString().equals("")){
                        Snackbar.make(v, "전화번호를 입력해주세요.",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        bookphone.append(textInputEditText_one.getText().toString());
                        bookphone.append("/");
                        bookphone.append(textInputEditText_two.getText().toString());
                        bookphone.append("/");
                        bookphone.append(textInputEditText_three.getText().toString());
                        bookphone.append("/");
                        bookphone.append(textInputEditText_four.getText().toString());
                        Log.e("booked phone", bookphone.toString());
                        inputflag =1;
                    }
                }

                if(inputflag == 1){
                    Log.e("print radio btn",check_radio_btn);

                    String[] split_room = RoomInfo.split("/");
                    URL_make url = new URL_make("insert_newbook");
                    String inputURL = url.makeURL();
                    Log.e("String","String");
                    Log.e("final phone number", bookphone.toString());

                    for(int i=0; i<split_room.length; i++){
                        try {
                            String roomnum = split_room[i];

                            String results = new NewBookRoom().execute(inputURL, MainActivity.logined_id, roomnum, StartDay, LastDay, check_radio_btn, bookphone.toString()).get();

                            if(results.equals("0")){
                                //초기화면으로 전환
                                Toast.makeText(getContext(),"예약완료 !", Toast.LENGTH_LONG).show();
                                Snackbar.make(v,"예약완료 !", Snackbar.LENGTH_LONG).show();
                                fragmentManager = getFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentManager.beginTransaction().replace(R.id.content_fragment, new Frag_nfc(), null).addToBackStack(null).commit();
                                fragmentTransaction.commit();
                                break;
                            }

                            if( i == split_room.length-1){
                                Snackbar.make(v,"해당 옵션의 방은 매진되었습니다.", Snackbar.LENGTH_LONG).show();
                            }

                            if(results.equals("2")){
                                Snackbar.make(v,"해당 옵션의 방은 매진되었습니다.", Snackbar.LENGTH_LONG).show();
                            }
                            if(results.equals("3")){
                                Snackbar.make(v,"입력하신 핸드폰 번호를 다시 확인해주세요.", Snackbar.LENGTH_LONG).show();
                            }
                            if(results.equals("4")){
                                Snackbar.make(v,"관리자에게 문의하세요.", Snackbar.LENGTH_LONG).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    Log.e("split_room_final",Integer.toString(split_room.length));
                    Log.e("split_room_final",split_room[split_room.length-1]);


                }



            }
        });

        setting_checkin.setText(StartDay);
        setting_checkout.setText(LastDay);

        return view;
    }
    void Findid() {
        setting_img = view.findViewById(R.id.setting_img);

        setting_checkin = view.findViewById(R.id.setting_checkin);
        setting_checkout = view.findViewById(R.id.setting_checkout);
        notify_usage = view.findViewById(R.id.notify_usage);

        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton = view.findViewById(R.id.radioButton);
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton3 = view.findViewById(R.id.radioButton3);
        radioButton4 = view.findViewById(R.id.radioButton4);

        if(RoomGrade.equals("1")){
            radioButton4.setVisibility(View.GONE);
        }

        textInputLayout_one = view.findViewById(R.id.textInputLayout_one);
        textInputLayout_two = view.findViewById(R.id.textInputLayout_two);
        textInputLayout_three = view.findViewById(R.id.textInputLayout_three);
        textInputLayout_four = view.findViewById(R.id.textInputLayout_four);

        textInputEditText_one = view.findViewById(R.id.textInputEditText_one);
        textInputEditText_two = view.findViewById(R.id.textInputEditText_two);
        textInputEditText_three = view.findViewById(R.id.textInputEditText_three);
        textInputEditText_four = view.findViewById(R.id.textInputEditText_four);

        reserve_finish_button = view.findViewById(R.id.reserve_finish_button);

        textInputLayout_one.setCounterEnabled(true);
        textInputLayout_two.setCounterEnabled(true);
        textInputLayout_three.setCounterEnabled(true);
        textInputLayout_four.setCounterEnabled(true);

        textInputLayout_one.setCounterMaxLength(13);
        textInputLayout_two.setCounterMaxLength(13);
        textInputLayout_three.setCounterMaxLength(13);
        textInputLayout_four.setCounterMaxLength(13);

        textInputEditText_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable input_num) {

                if( !(input_num.toString().contains("-")) ){
                    textInputLayout_one.setError("전화번호 양식에 맞게 기입해주세요");
                }
                else{
                    textInputLayout_one.setError(null);
                }

            }
        });

        textInputEditText_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable input_num) {

                if( !(input_num.toString().contains("-")) ){
                    textInputLayout_two.setError("전화번호 양식에 맞게 기입해주세요");
                }
                else{
                    textInputLayout_two.setError(null);
                }

            }
        });

        textInputEditText_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable input_num) {

                if( !(input_num.toString().contains("-")) ){
                    textInputLayout_three.setError("전화번호 양식에 맞게 기입해주세요");
                }
                else{
                    textInputLayout_three.setError(null);
                }

            }
        });

        textInputEditText_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable input_num) {

                if( !(input_num.toString().contains("-")) ){
                    Snackbar.make(view,"전화번호 양식에 맞게 기입해주세요.",Snackbar.LENGTH_LONG).show();
                }
                else{
                    textInputLayout_four.setError(null);
                }

            }
        });
    }


    public class NewBookRoom extends AsyncTask<String, Void, String>{

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
                dos.writeBytes("UserID="+params[1]+"&roomnum="+params[2]+"&StartDate="+params[3]+"&EndDate="+params[4]+"&numofphone="+params[5]+"&bookphone="+params[6]);
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
}
