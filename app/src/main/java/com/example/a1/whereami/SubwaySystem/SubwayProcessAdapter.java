package com.example.a1.whereami.SubwaySystem;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.whereami.R;

import java.util.ArrayList;

public class SubwayProcessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private ArrayList<SubwayRoute> subwayTansferArrayList;
    private String end_name,end_time, subway_type, subway_type_name;
    int count;

    SubwayProcessAdapter(Context context, ArrayList<SubwayRoute> subwayTansferArrayList) {
        this.context = context;
        this.subwayTansferArrayList = subwayTansferArrayList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView end_name, end_time, count, subway_type, subway_type_name;
        ImageView imageView;



        MyViewHolder(View view) {
            super(view);
            end_name = view.findViewById(R.id.end_name);
            end_time = view.findViewById(R.id.end_time);
            count = view.findViewById( R.id.count );
            subway_type = view.findViewById(R.id.subway_type);
            subway_type_name = view.findViewById(R.id.subway_type_name);
            imageView = view.findViewById( R.id.image_aa );
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.subwy_line_items,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;


          end_name = subwayTansferArrayList.get( position ).station;
          count = subwayTansferArrayList.get( position ).count;
          subway_type = subwayTansferArrayList.get( position ).direction_no;
          subway_type_name = subwayTansferArrayList.get( position ).direction_name;

          if(subwayTansferArrayList.get(position).start != "x") {
              end_time = subwayTansferArrayList.get( position ).end + "\n" +
                      subwayTansferArrayList.get( position ).start;
              myViewHolder.imageView.setImageResource( R.drawable.aaaa);
          } else {
              end_time = subwayTansferArrayList.get( position ).end;
              myViewHolder.imageView.setImageResource( R.drawable.aaa);

         }

        myViewHolder.end_name.setText(end_name);
        myViewHolder.end_time.setText(end_time);
        myViewHolder.count.setText(String.valueOf(count)+"역");
        myViewHolder.subway_type.setText(subway_type);
        myViewHolder.subway_type_name.setText(subway_type_name);

    }

    @Override
    public int getItemCount() {
        return subwayTansferArrayList.size();
    }
}