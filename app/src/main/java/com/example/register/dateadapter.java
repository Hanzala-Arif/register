package com.example.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class dateadapter extends RecyclerView.Adapter<dateadapter.ViewHolder>{
    Context context;
    ArrayList<date> list;
    public dateadapter(Context context, ArrayList<date> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public dateadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.dates_items,parent,false);
        return new dateadapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull dateadapter.ViewHolder holder, int position) {
        date or=list.get(position);
        holder.order.setText(or.getOrder());
        holder.details.setText(or.getDetails());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView order,details;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order=itemView.findViewById(R.id.order);
            details=itemView.findViewById(R.id.details);
        }
    }
}
