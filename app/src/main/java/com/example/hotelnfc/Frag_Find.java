package com.example.hotelnfc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Frag_Find extends Fragment {

    View view;

    private TextInputLayout textInputLayout_find_id_phone, textInputLayout_find_pw_id, textInputLayout_find_pw_phone;
    private TextInputEditText textInputEditText_find_id_phone, textInputEditText_find_pw_id, textInputEditText_find_pw_phone;
    private Button button_find_id, button_find_pw;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_find, null);

        Idcheck();

        return view;
    }

    void Idcheck() {
        textInputLayout_find_id_phone = view.findViewById(R.id.textInputLayout_find_id_phone);
        textInputLayout_find_pw_id = view.findViewById(R.id.textInputLayout_find_pw_id);
        textInputLayout_find_pw_phone = view.findViewById(R.id.textInputLayout_find_pw_phone);

        textInputEditText_find_id_phone = view.findViewById(R.id.textInputEditText_find_id_phone);
        textInputEditText_find_pw_id = view.findViewById(R.id.textInputEditText_find_pw_id);
        textInputEditText_find_pw_phone = view.findViewById(R.id.textInputEditText_find_pw_phone);

        button_find_id = view.findViewById(R.id.button_find_id);
        button_find_pw = view.findViewById(R.id.button_find_pw);
    }
}
