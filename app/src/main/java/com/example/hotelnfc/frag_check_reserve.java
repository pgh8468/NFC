package com.example.hotelnfc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        try {
            String bookedInfo = new GetUserBookedInfo().execute(new URL_make("get_user_booklist").makeURL(),MainActivity.logined_id).get();
            if(bookedInfo.equals("0")){
                Toast.makeText(getContext(),"현재 예약된 방이 없습니다.", Toast.LENGTH_LONG).show();
            }
            JSONArray jsonArray = new JSONArray(bookedInfo);
            Log.e("jsonArray len:", Integer.toString(jsonArray.length()));
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.get("BGrade").toString().equals("1")){
                    String checkin = jsonObject.get("BCin").toString();
                    String checkinNotime[] = checkin.split("T");
                    Log.e("checkinDate:", checkinNotime[0]);
                    String checkout = jsonObject.get("BCout").toString();
                    String checkoutNotime[] = checkout.split("T");
                    Log.e("checkoutDate:", checkoutNotime[0]);
                    String grade = jsonObject.get("BGrade").toString();
                    String roomnum = jsonObject.get("BNum").toString();
                    String booktel = jsonObject.get("BTel").toString();
                    Log.e("booked tel :", ""+booktel);
                    String contel1 = jsonObject.get("BSTel").toString();
                    String contel2 = jsonObject.get("BTTel").toString();
                    String contel3 = jsonObject.get("BFTel").toString();
                    String bookdate = jsonObject.get("BDate").toString();
                    String bookID = jsonObject.get("BID").toString();
                    checkroomArrayList.add(new Item_Checkroom(R.drawable.first, grade, checkinNotime[0], checkoutNotime[0], "75,000", booktel, contel1, contel2, contel3, roomnum, bookdate, bookID));
                }
                else if(jsonObject.get("").toString().equals("2")){
                    String checkin = jsonObject.get("BCin").toString();
                    String checkinNotime[] = checkin.split("T");
                    Log.e("checkinDate:", checkinNotime[0]);
                    String checkout = jsonObject.get("BCout").toString();
                    String checkoutNotime[] = checkout.split("T");
                    Log.e("checkoutDate:", checkoutNotime[0]);
                    String grade = jsonObject.get("BGrade").toString();
                    String roomnum = jsonObject.get("BNum").toString();
                    String booktel = jsonObject.get("BTel").toString();
                    Log.e("booked tel :", ""+booktel);
                    String contel1 = jsonObject.get("BSTel").toString();
                    String contel2 = jsonObject.get("BTTel").toString();
                    String contel3 = jsonObject.get("BFTel").toString();
                    String bookdate = jsonObject.get("BDate").toString();
                    String bookID = jsonObject.get("BID").toString();
                    checkroomArrayList.add(new Item_Checkroom(R.drawable.first, grade, checkinNotime[0], checkoutNotime[0], "90,500", booktel, contel1, contel2, contel3, roomnum, bookdate, bookID));
                }
                else {
                    Toast.makeText(getContext(),"현재 예약된 방이 없습니다.", Toast.LENGTH_LONG).show();
                }

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
//                Log.e("position :", Integer.toString(position));
//                Log.e("checked user phone :", checkroomArrayList.get(position).getBook_userphone());
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.beginTransaction().replace(R.id.content_fragment, new frag_check_reserve_two(checkroomArrayList.get(position)), null).addToBackStack(null).commit();
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public class GetUserBookedInfo extends AsyncTask<String, Void, String>{

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
                dos.writeBytes("UserID="+params[1]);
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
