package com.example.hotelnfc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class frag_reserve_room extends Fragment{

    private ArrayList<Item_room> arrayList;
    private ItemRoomAdapter itemRoomAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public frag_reserve_room() {}

    public frag_reserve_room(ArrayList<Item_room> arrayList) {
        this.arrayList = arrayList;
    }

    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reserve_room, null);

        recyclerView = view.findViewById(R.id.room_recyclerview);
        itemRoomAdapter = new ItemRoomAdapter(arrayList);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemRoomAdapter);


        return view;
    }
}
