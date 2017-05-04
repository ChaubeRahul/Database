package com.example.rahul.database;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


 class AdapterDemo extends RecyclerView.Adapter<AdapterDemo.BindView>{
    List<String> list;
     AdapterDemo(List<String> list) {
        this.list=list;
    }

    @Override
    public BindView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new BindView(view);
    }

    @Override
    public void onBindViewHolder(BindView holder, int position) {
        holder.textView.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     void setFilter(List<String> filter) {
        this.list = filter;
    }

    class BindView extends RecyclerView.ViewHolder {
        TextView textView;
         BindView(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.message);
        }
    }
}
