package com.example.a1.whereami.SubwaySystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.whereami.R;

import java.util.ArrayList;



public class SubwayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private ArrayList<SubwayInfo> subwayInfoArrayList;
    private String k_name,e_name;
    private int metro_no, start_end;
    int modify;
    Intent intent;
    Subway subway = new Subway();

    SubwayAdapter(Context context, ArrayList<SubwayInfo> subwayInfoArrayList, int start_end, int modify, Subway subway) {
        this.context = context;
        this.start_end = start_end;
        this.modify = modify;
        this.subway = subway;
        this.subwayInfoArrayList = subwayInfoArrayList;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView k_name;
        TextView e_name;
        LinearLayout layout;

        MyViewHolder(View view) {
            super(view);
            k_name = view.findViewById(R.id.k_name);
            e_name = view.findViewById(R.id.e_name);
            layout = view.findViewById(R.id.stationlinear);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.subway_station_list,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        k_name = subwayInfoArrayList.get(position).k_name;
        e_name = subwayInfoArrayList.get(position).e_name;
        metro_no = subwayInfoArrayList.get(position).metro_no;
        final String m_k_name = k_name;
        final int m_no = metro_no;

        myViewHolder.k_name.setText(k_name);
        myViewHolder.e_name.setText(e_name);

        if(modify != 1 ) {
            if(start_end == 1) {
                ((MyViewHolder) holder).layout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent( context, SubwayEndActivity.class );
                        intent.putExtra( "Type",1 );
                        intent.putExtra( "start_k_name",m_k_name );
                        intent.putExtra( "start_no",m_no );
                        context.startActivity( intent );

                    }
                } );
            } else if (start_end==2) {

                ((MyViewHolder) holder).layout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent( context, SubwaySttingActivity.class);
                        intent.putExtra( "start_k_name",subway.start_k_name );
                        intent.putExtra( "start_no",subway.start_no );
                        intent.putExtra( "end_k_name",m_k_name);
                        intent.putExtra( "end_no",m_no );
                        context.startActivity( intent );

                    }
                });
            }
        } else if(modify==1){
            if(start_end == 1) {
                ((MyViewHolder) holder).layout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( context, SubwaySttingActivity.class );
                        intent.putExtra( "start_k_name", m_k_name );
                        intent.putExtra( "start_no", m_no );
                        intent.putExtra( "end_k_name", subway.end_k_name );
                        intent.putExtra( "end_no", subway.end_no );

                        context.startActivity( intent );
                    }
                });
            } else if (start_end==2) {
                ((MyViewHolder) holder).layout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( context, SubwaySttingActivity.class );
                        intent.putExtra( "start_k_name", subway.start_k_name );
                        intent.putExtra( "start_no", subway.start_no );
                        intent.putExtra( "end_k_name", m_k_name );
                        intent.putExtra( "end_no", m_no );

                        context.startActivity( intent );
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return subwayInfoArrayList.size();
    }
}