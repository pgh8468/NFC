package com.example.hotelnfc;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class frag_reserve_room extends Fragment {

    private ArrayList<Item_room> arrayList;
    private ItemRoomAdapter itemRoomAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public frag_reserve_room() {}

    View view;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reserve_room, null);

        recyclerView = view.findViewById(R.id.room_recyclerview);
        itemRoomAdapter = new ItemRoomAdapter(getContext() ,arrayList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(itemRoomAdapter);

        //arrayList2 = new ArrayList<Item_room>();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        arrayList = new ArrayList<>();
        arrayList.add(new Item_room("Queen", "75,500", R.drawable.first));
        arrayList.add(new Item_room("King", "90,500", R.drawable.first));
    }
}
