package com.example.hotelnfc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class frag_check_reserve_two extends Fragment {

    ImageView check_img;
    TextView checking_two_checkin, checking_two_checkout, client_one, client_two, client_three, client_four, textView;
    private Item_Checkroom selectedItem;
    Button reserve_cancle_button;

    View view;

    public frag_check_reserve_two(Item_Checkroom selectedItem){
        this.selectedItem = selectedItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_check_reserve_two, null);

        checkId(); //findViewById

        textView.setText("예약된 객실 이용객 핸드폰 번호입니다.\n"+"핸드폰으로 객실을 이용하실 수 있습니다.");

        checking_two_checkin.setText(selectedItem.getCheck_checkin());
        checking_two_checkout.setText(selectedItem.getCheck_checkout());

        client_one.append(" : "+selectedItem.getBook_userphone());
        if(!selectedItem.getControlnum1().equals("null"))
            client_two.append(" : "+selectedItem.getControlnum1());
        else if (!selectedItem.getControlnum2().equals("null"))
            client_three.append(" : "+selectedItem.getControlnum2());
        else if (!selectedItem.getControlnum3().equals("null"))
            client_four.append(" : "+selectedItem.getControlnum3());
        else{
            client_two.setText("");
            client_three.setText("");
            client_four.setText("");
        }

        reserve_cancle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("push button","push button");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("reservation cancel").setMessage("해당 방의 예약을 취소하시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            String results = new CancleReservation().execute(new URL_make("cancel_reservation").makeURL(), selectedItem.getBookID(), selectedItem.getRoom_num(), selectedItem.getBook_date()).get();

                            if(results.equals("0")){
                                Toast.makeText(getActivity(),"예약취소에 성공하셨습니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getActivity(),"예약취소에 실패하셨습니다.", Toast.LENGTH_LONG).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });

        return view;
    }

    void checkId() {
        check_img = view.findViewById(R.id.check_img);

        //체크인 체크아웃
        checking_two_checkin = view.findViewById(R.id.checking_two_checkin);
        checking_two_checkout = view.findViewById(R.id.checking_two_checkout);

        //예약했을 때 함께 추가한 객실 이용객 번호
        client_one = view.findViewById(R.id.client_one);
        client_two = view.findViewById(R.id.client_two);
        client_three = view.findViewById(R.id.client_three);
        client_four = view.findViewById(R.id.client_four);

        textView = view.findViewById(R.id.textView); //설명하는 Text

        reserve_cancle_button = view.findViewById(R.id.reserve_cancle_button);
    }

    public class CancleReservation extends AsyncTask<String, Void, String>{

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
                dos.writeBytes("UserID="+params[1]+"&BookedNum="+params[2]+"&BookedDate="+params[3]);
                dos.flush();
                dos.close();

                InputStreamReader is = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(is);
                String results= "";

                while (true){
                    results =reader.readLine();
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
