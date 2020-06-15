package com.example.hotelnfc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

    ImageView setting_img;
    TextView setting_checkin, setting_checkout, notify_usage;
    RadioGroup radioGroup;
    RadioButton radioButton,radioButton2,radioButton3,radioButton4;
    TextInputLayout textInputLayout_one, textInputLayout_two, textInputLayout_three, textInputLayout_four;
    TextInputEditText textInputEditText_one, textInputEditText_two, textInputEditText_three, textInputEditText_four;
    Button reserve_finish_button;

    View view;

    public Frag_room_setting() {
        // Required empty public constructor
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

        textInputLayout_one = view.findViewById(R.id.textInputLayout_one);
        textInputLayout_two = view.findViewById(R.id.textInputLayout_two);
        textInputLayout_three = view.findViewById(R.id.textInputLayout_three);
        textInputLayout_four = view.findViewById(R.id.textInputLayout_four);

        textInputEditText_one = view.findViewById(R.id.textInputEditText_one);
        textInputEditText_two = view.findViewById(R.id.textInputEditText_two);
        textInputEditText_three = view.findViewById(R.id.textInputEditText_three);
        textInputEditText_four = view.findViewById(R.id.textInputEditText_four);

        reserve_finish_button = view.findViewById(R.id.reserve_finish_button);
    }
}
