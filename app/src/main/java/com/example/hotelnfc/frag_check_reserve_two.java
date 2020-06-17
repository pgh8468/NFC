package com.example.hotelnfc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class frag_check_reserve_two extends Fragment {

    ImageView check_img;
    TextView checking_two_checkin, checking_two_checkout, client_one, client_two, client_three, client_four, textView;
    Button reserve_cancle_button;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_check_reserve_two, null);

        checkId(); //findViewById

        textView.setText("예약된 객실 이용객 핸드폰 번호입니다.\n"+"핸드폰으로 객실을 이용하실 수 있습니다.");



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
}
