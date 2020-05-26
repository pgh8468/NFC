package com.example.hotelnfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_nfc extends Fragment {

    View view;
    TextView nfc_booked, nfc_detail_booked;
    Button btn_issue_key;
    NfcAdapter nfcAdapter;
    String logined_id = null;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_nfc, null);

        nfc_booked = view.findViewById(R.id.txt_remain_book_date);
        nfc_detail_booked = view.findViewById(R.id.txt_logined_book_info);
        btn_issue_key = view.findViewById(R.id.btn_issue_key);

        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());

        if(nfcAdapter == null){
            Toast.makeText(getActivity(), "nfc 안킨거 아니냐?", Toast.LENGTH_LONG).show();
            String android_id = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
            Log.e("check id",android_id);

        }

        btn_issue_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NfcIssueKey.class);

                logined_id = "testID";

                if(logined_id == null){
                    Toast.makeText(getActivity(),"로그인이 필요한 서비스입니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    intent.putExtra("logined_id",logined_id);
                    startActivity(intent);

                }

            }
        });

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
