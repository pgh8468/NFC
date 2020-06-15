package com.example.hotelnfc;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class frag_check_reserve extends Fragment {

    ImageView check_img;
    TextView checking_checkin, checking_checkout, client_one, client_two, client_three, client_four, textView;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_check_reserve, null);

        checkId(); //findViewById

        textView.setText("객실 이용객 번호입니다.\n" + "해당 번호를 가진 핸드폰으로 객실 이용이 가능합니다.");

        return view;
    }

    void checkId() {
        check_img = view.findViewById(R.id.check_img);

        //체크인 체크아웃
        checking_checkin = view.findViewById(R.id.checking_checkin);
        checking_checkout = view.findViewById(R.id.checking_checkout);

        //예약했을 때 함께 추가한 객실 이용객 번호
        client_one = view.findViewById(R.id.client_one);
        client_two = view.findViewById(R.id.client_two);
        client_three = view.findViewById(R.id.client_three);
        client_four = view.findViewById(R.id.client_four);

        textView = view.findViewById(R.id.textView); //설명하는 Text
    }

}
