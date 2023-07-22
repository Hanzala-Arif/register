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

public class orderadapter extends RecyclerView.Adapter<orderadapter.ViewHolder> {
    Context context;
    ArrayList<order> list;

    public orderadapter(Context context, ArrayList<order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    order or=list.get(position);
holder.Order.setText(or.getOrdernum());
holder.Customer_name.setText(or.getCsname());
holder.Details.setText(or.getDetails());
holder.checkBox.setChecked(or.getDelievery());
holder.checkBox2.setChecked(or.getOK());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Order,Customer_name,Details;
        public CheckBox checkBox,checkBox2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            checkBox2=itemView.findViewById(R.id.checkBox2);
            Order=itemView.findViewById(R.id.order);
            Customer_name=itemView.findViewById(R.id.csname);
            Details=itemView.findViewById(R.id.detail);
        }
    }
}
