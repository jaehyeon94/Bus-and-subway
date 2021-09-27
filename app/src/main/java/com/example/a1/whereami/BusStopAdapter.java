package com.example.a1.whereami;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {
    Context context;
    ArrayList<StopBusVO> stopBusVOs;
    String carno, lineid;

    public BusStopAdapter(Context context, ArrayList<StopBusVO> stopBusVOs) {
        this.context = context;
        this.stopBusVOs = stopBusVOs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_stop_items,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StopBusVO stopBusVO = stopBusVOs.get(position);
        carno = stopBusVO.getCarNo();
        lineid = stopBusVO.getLineid();

        holder.lineNo.setText(stopBusVO.getLineno() + "번");
        holder.remain_min.setText(stopBusVO.getRemain_min() + "분");
        holder.remain_station.setText(stopBusVO.getRemain_station() + "정거장 전");

      //  Toast.makeText( DestinationStationActivity.this,String.valueOf( lineid ),Toast.LENGTH_LONG ).show();



    }
    @Override
    public int getItemCount() {
        return stopBusVOs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView lineNo, remain_min, remain_station;
        CardView busstoplinear;

        public ViewHolder(View itemView) {
            super(itemView);
            lineNo = itemView.findViewById(R.id.busNo);
            remain_min = itemView.findViewById(R.id.remain_min);
            remain_station = itemView.findViewById(R.id.remain_station);
            busstoplinear = itemView.findViewById(R.id.busstopLinear);
            final String line = lineid;
            final String car = carno;
            busstoplinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DestinationStationActivity.class);
                    intent.putExtra("carno", car);
                    intent.putExtra("lineid",line);
                    context.startActivity(intent);
                }
            });
        }
    }
}
