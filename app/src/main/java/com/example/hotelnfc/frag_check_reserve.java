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

    private ImageView check_room_img;
    private TextView check_checkin, chech_checkout;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;



    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reserve, null);

        check_room_img = view.findViewById(R.id.check_room_img);
        check_checkin = view.findViewById(R.id.check_checkin);
        chech_checkout = view.findViewById(R.id.check_checkout);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {

        }
    };

    public void onDetach() {
        super.onDetach();
        onBackPressedCallback.remove();
    }
}
