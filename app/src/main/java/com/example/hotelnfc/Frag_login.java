package com.example.hotelnfc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class Frag_login extends Fragment {

    View view;

    TextInputEditText TextInputEditText;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_login, null);

        TextInputEditText = view.findViewById(R.id.textInputEditText_id);

        return view;
    }

}
