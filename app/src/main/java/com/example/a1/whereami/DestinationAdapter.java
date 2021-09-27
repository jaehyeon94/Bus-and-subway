package com.example.a1.whereami;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {
    Context context;
    ArrayList<LineInfo> lineinfos;
    String selectCarNo;
    String lineid;
    StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();

    public DestinationAdapter(Context context, ArrayList<LineInfo> lineinfos, String carno, String lineid) {
        this.context = context;
        this.lineinfos = lineinfos;
        this.selectCarNo = carno;
        this.lineid=lineid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.destination_info_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stationNM.setText(lineinfos.get(position).getBstopnm());
        holder.carno.setText("");
        holder.busimage.setVisibility(View.INVISIBLE);
        holder.busimage.setImageResource(R.drawable.bus);
        holder.destinationLinear.setBackgroundColor(Color.WHITE);
        if(lineinfos.get(position).getCarno()!=null) {
            holder.carno.setText(lineinfos.get(position).getCarno());
            holder.busimage.setVisibility(View.VISIBLE);

            if(selectCarNo.equals(lineinfos.get(position).getCarno().substring(3))){
                Log.e("선택된 차넘버 : ",selectCarNo);
                Log.e("linefos.getCarno : ", lineinfos.get(position).getCarno() + "." + (lineinfos.get(position).getCarno().substring(3)));
                holder.busimage.setImageResource(R.drawable.selectbus);
            }
            if(startDestinationVO.getStartStation().equals(lineinfos.get(position).getBstopnm())){
                Log.e("실행됨","실행완료");
                holder.destinationLinear.setBackgroundColor(Color.GRAY);
                holder.destinationLinear.requestFocus();
            }
        }
    }

    @Override
    public int getItemCount() {
        return lineinfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView stationNM;
        LinearLayout destinationLinear;
        ImageView busimage;
        TextView carno;

        public ViewHolder(View itemView) {
            super(itemView);
            stationNM = itemView.findViewById(R.id.busstationName);
            destinationLinear = itemView.findViewById(R.id.destinationLinear);
            busimage = itemView.findViewById(R.id.busicon);
            carno = itemView.findViewById(R.id.carno);


            destinationLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();
                    startDestinationVO.setDestinationStation(stationNM.getText().toString());
                    Intent intent = new Intent(context.getApplicationContext(),NowNextStationActivity.class);
                    intent.putExtra("carno",selectCarNo);
                    intent.putExtra("lineid",lineid);
                    context.startActivity(intent);
                }
            });
        }
    }
}
