package com.example.hotelnfc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class frag_check_reserve extends Fragment {

    private ArrayList<Item_Checkroom> checkroomArrayList;
    private CheckroomAdapter checkroomAdapter;
    private RecyclerView check_room_recyclerview;
    private LinearLayoutManager linearLayoutManager;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    View view;

    public frag_check_reserve() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        checkroomArrayList = new ArrayList<>();

        checkroomArrayList.add(new Item_Checkroom(R.drawable.first, "Queen", "2020-06-17", "2020-06-20", "75,500"));

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_check_reserve, null);

        checkroomAdapter = new CheckroomAdapter(getContext(), checkroomArrayList);
        check_room_recyclerview = view.findViewById(R.id.check_room_recyclerview);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        check_room_recyclerview.setLayoutManager(linearLayoutManager);

        check_room_recyclerview.setAdapter(checkroomAdapter);

        //아이템 클릭하면 그에 맞는 예약 정보를 보여주는 frag_check_reserve_two 화면으로 전환
        checkroomAdapter.setCheckItemClick(new CheckroomAdapter.CheckItemClick() {
            @Override
            public void OnClick(View v, int position) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new frag_check_reserve_two(), null).addToBackStack(null).commit();
                fragmentTransaction.commit();
            }
        });

        return view;
    }


}
