package com.example.hotelnfc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class frag_reserve_room extends Fragment {

    private ArrayList<Item_room> arrayList;
    private ItemRoomAdapter itemRoomAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String StartDay, LastDay, RoomInfo;
    private StringBuilder room1 = null, room2 = null;

    MainActivity mainActivity;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public frag_reserve_room(){

    }

    public frag_reserve_room(String StartDay, String LastDay, String RoomInfo) {
        this.StartDay = StartDay;
        this.LastDay = LastDay;
        this.RoomInfo = RoomInfo;
    }

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        arrayList = new ArrayList<>();

        room1 = new StringBuilder();
        room2 = new StringBuilder();

        try {
            JSONArray jsonArray = new JSONArray(RoomInfo);
            Log.e("ary", jsonArray.toString());
            for( int i=0; i< jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get("RGrade").toString().equals("1")){
                    room1.append(jsonObject.get("RNum").toString());
                    room1.append("/");
                }
                if(jsonObject.get("RGrade").toString().equals("2")){
                    room2.append(jsonObject.get("RNum").toString());
                    room2.append("/");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("room1", room1.toString());
        Log.e("room2", room2.toString());

        if( !(room1.toString().equals(""))){
            arrayList.add(new Item_room("Queen", "75,500", R.drawable.first));
        }
        if(!(room2.toString().equals(""))){
            arrayList.add(new Item_room("King", "90,500", R.drawable.first));
        }
        if(room1.toString().equals("") & room2.toString().equals("")){
            Toast.makeText(getContext()," 현재 예약할 수 있는 방이 없습니다.", Toast.LENGTH_LONG).show();
        }

        arrayList.add(new Item_room("King", "90,500", R.drawable.first));

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_reserve_room, null);

        recyclerView = view.findViewById(R.id.room_recyclerview);
        itemRoomAdapter = new ItemRoomAdapter(getContext() ,arrayList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(itemRoomAdapter);

        mainActivity = (MainActivity) getActivity();

        fragmentManager = mainActivity.getSupportFragmentManager();

        //원하는 객실 클릭리스너
        itemRoomAdapter.setItemClick(new ItemRoomAdapter.ItemClick() {
            @Override
            public void OnClick(View v, int position) {
                Log.e("click?"+position, arrayList.get(position).getRoom_class());
                Log.e("FirstDay",StartDay);
                Log.e("LastDay",LastDay);

                if(position ==0){
//                    String[] split_room1 = room1.toString().split("/");
//                    Log.e("split_room1[0]: ", split_room1[0]);
                    Frag_room_setting frag_room_setting = new Frag_room_setting(StartDay,LastDay,room1.toString(),"1");
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, frag_room_setting, null).addToBackStack(null).commit();
                    fragmentTransaction.commit();

                }
                if(position ==1){
//                    String[] split_room2 = room2.toString().split("/");
//                    Log.e("split_room1[0]: ", split_room2[0]);
                    Frag_room_setting frag_room_setting = new Frag_room_setting(StartDay,LastDay,room2.toString(),"2");
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentManager.beginTransaction().replace(R.id.content_fragment, frag_room_setting, null).addToBackStack(null).commit();
                    fragmentTransaction.commit();

                }


            }
        });

        return view;
    }

    public class RemainRoomList extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder output = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(con.getOutputStream());
                dos.writeBytes("StartD="+params[1]+"&EndD="+params[2]);
                dos.flush();
                dos.close();

                InputStreamReader is = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(is);
                String results = "";

                while(true){
                    results = reader.readLine();
                    if(results == null){
                        break;
                    }output.append(results);
                }

                con.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

}
