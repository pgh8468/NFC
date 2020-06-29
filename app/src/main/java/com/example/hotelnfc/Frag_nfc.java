package com.example.hotelnfc;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Frag_nfc extends Fragment {

    View view;
    TextView nfc_booked, nfc_detail_booked, txt_logined_book_info;
    Button btn_issue_key;

    String resultday;

    MainActivity mainActivity = new MainActivity();
    MainActivity main = (MainActivity) getActivity();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String stayDay;

    View navi_header;

    public Frag_nfc(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_nfc, null);

       // nfc_booked = view.findViewById(R.id.txt_remain_book_date);
        nfc_detail_booked = view.findViewById(R.id.txt_logined_book_info);
        btn_issue_key = view.findViewById(R.id.btn_issue_key);
        txt_logined_book_info = view.findViewById(R.id.txt_logined_book_info);

        if(MainActivity.logined_id != null){
            txt_logined_book_info.setText("방 문 앞에서 NFC 를\n활성화 하고 모바일키 \n발급 받기를 눌러주세요.");

            MainActivity.client_name.setText(MainActivity.logined_id);
        }

        btn_issue_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.logined_id != null) {
                    Intent intent = new Intent(getActivity(), NfcIssueKey.class);
                    intent.putExtra("logined_id", MainActivity.logined_id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
