package com.example.hotelnfc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class frag_reserve extends Fragment{

    public frag_reserve() {}

    View view;
    Button reserve_room_button;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reserve, null);

        reserve_room_button = view.findViewById(R.id.reserve_room_button);

        reserve_room_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag_reserve_room frag_reserve_room = new frag_reserve_room();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_fragment, frag_reserve_room);
                fragmentTransaction.commit();
                Log.d("씨발", "씨이발 됐당 내가 바보였다;;");
            }
        });

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
