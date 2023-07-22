package com.example.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class dailyadapter extends RecyclerView.Adapter<dailyadapter.ViewHolder> {
    Context context;
    ArrayList<daily> list;

    public dailyadapter(Context context, ArrayList<daily> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public dailyadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.dailyitems,parent,false);
        return new dailyadapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull dailyadapter.ViewHolder holder, int position) {
        daily or=list.get(position);
        holder.Order.setText(or.getRorder());

        holder.Details.setText(or.getDetails());
holder.Date.setText(or.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Order,Details,Date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Order=itemView.findViewById(R.id.order);

            Details=itemView.findViewById(R.id.detail);
            Date=itemView.findViewById(R.id.date);
        }
    }
}
