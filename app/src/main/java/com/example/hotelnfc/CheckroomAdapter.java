package com.example.hotelnfc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckroomAdapter extends RecyclerView.Adapter<CheckroomAdapter.CheckViewHolder> {

    Context context;
    private ArrayList<Item_Checkroom> checkroomArrayList;

    CheckItemClick checkItemClick;

    public CheckroomAdapter(Context context, ArrayList<Item_Checkroom> checkroomArrayList) {
        this.context = context;
        this.checkroomArrayList = checkroomArrayList;
    }

    public interface CheckItemClick {
        void OnClick(View v, int position);
    }

    public void setCheckItemClick(CheckItemClick checkItemClick) {
        this.checkItemClick = checkItemClick;
    }

    @NonNull
    @Override
    public CheckroomAdapter.CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkroom, parent, false);

        CheckViewHolder holder = new CheckViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckroomAdapter.CheckViewHolder holder, final int position) {

        holder.check_img.setImageResource(checkroomArrayList.get(position).getCheck_img());
        holder.check_roomclass.setText(checkroomArrayList.get(position).getCheck_roomclass());
        holder.check_checkin.setText(checkroomArrayList.get(position).getCheck_checkin());
        holder.check_checkout.setText(checkroomArrayList.get(position).getCheck_checkout());
        holder.check_price.setText(checkroomArrayList.get(position).getCheck_price());

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkItemClick != null) {
                    checkItemClick.OnClick(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkroomArrayList.size();
    }

    public class CheckViewHolder extends RecyclerView.ViewHolder {

        protected ImageView check_img;
        protected TextView check_roomclass, check_checkin, check_checkout, check_price;

        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);

            check_img = itemView.findViewById(R.id.check_img);
            check_roomclass = itemView.findViewById(R.id.check_roomclass);
            check_checkin = itemView.findViewById(R.id.check_checkin);
            check_checkout = itemView.findViewById(R.id.check_checkout);
            check_price = itemView.findViewById(R.id.check_price);

        }
    }
}
