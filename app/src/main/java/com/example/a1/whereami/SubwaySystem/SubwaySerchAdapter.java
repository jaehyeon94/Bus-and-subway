package com.example.a1.whereami.SubwaySystem;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.whereami.MainActivity;
import com.example.a1.whereami.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SubwaySerchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String s_name, e_name, end_time, count_day;
    private int time_total, transfer, start_no;
    ArrayList<SubwayTimeInfo> subwayTimeInfoArrayList;
    ArrayList<SubwayRoute> subwayRouteArrayList;
    ArrayList<SubwayTime> subwayTimes;
    Intent integer;


    SubwaySerchAdapter(Context context, ArrayList<SubwayTimeInfo> subwayTimeInfoArrayList, ArrayList<SubwayRoute> subwayRouteArrayList, String s_name, String e_name, String count_day, int start_no, int transfer, ArrayList<SubwayTime> subwayTimes) {
        this.context = context;
        this.subwayTimeInfoArrayList = subwayTimeInfoArrayList;
        this.subwayRouteArrayList = subwayRouteArrayList;
        this.subwayTimes= subwayTimes;
        this.s_name = s_name;
        this.e_name = e_name;
        this.start_no = start_no;
        this.count_day = count_day;
        this.transfer = transfer;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time_total, s_name, e_name, s_time, transfer, end_time;
        LinearLayout layout;

        MyViewHolder(View view) {
            super( view );
            time_total = view.findViewById( R.id.total_time );
            s_name = view.findViewById( R.id.s_name );
            s_time = view.findViewById( R.id.s_time );
            end_time = view.findViewById( R.id.end_time );
            transfer = view.findViewById( R.id.transfer );
            layout = view.findViewById( R.id.subway_serch_layout );

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.subway_serch_items, parent, false );
        return new MyViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int time_h = 0;
        int time_m = 0;
        String time;
        final String s_time = subwayTimeInfoArrayList.get( position ).hour + ":" + subwayTimeInfoArrayList.get( position ).time;
        StrictMode.enableDefaults();

        String str = "2018-10-17 " + s_time + ":00.123";


        int time_total = 0;
        for(int i = 0; i < subwayRouteArrayList.size(); i++) {
            time_total += subwayRouteArrayList.get(i).time;
        }

        time_total += subwayTimes.get(position).time + subwayTimes.get( position).time1;


        time_total = time_total / 60;
        if (time_total % 60 > 30) {
            time_total++;
        }
        if (time_total > 60) {
            time_h++;
            time_m = time_total - 60;
            time = "\n                " + time_h + "시간 " + time_m + "분 )";
        } else {
            time = String.valueOf( time_total ) + "분 )";
        }

        try {
            Date date = transforDate( str );
            SimpleDateFormat sdformat = new SimpleDateFormat( "HH:mm" );
            Calendar cal = Calendar.getInstance();
            cal.setTime( date );
            cal.add( Calendar.MINUTE, time_total );
            end_time = sdformat.format( cal.getTime() );

        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String ee_time = end_time;
        final String time_1 = time;

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.s_name.setText( s_name + " -> " + e_name );
        myViewHolder.s_time.setText( s_time );
        myViewHolder.end_time.setText( ee_time );
        myViewHolder.transfer.setText( transfer + " 번)" );
        myViewHolder.time_total.setText( "(걸리는 시간 : " + time_1 );

        ((MyViewHolder) holder).layout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, SubwayProcessActivity.class );
                intent.putExtra( "s_name", s_name );
                intent.putExtra( "e_name", e_name );
                intent.putExtra( "total_time", time_1 );
                intent.putExtra( "s_time", s_time );
                intent.putExtra( "e_time", ee_time );
                intent.putExtra( "transfer", transfer );
                intent.putExtra( "count_day", count_day );
                intent.putExtra( "subway_route", subwayRouteArrayList );
                context.startActivity( intent );
            }
        } );
    }

    public Date transforDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
        Date date = sdf.parse( str );
        return date;

    }


    public int getItemCount() {
        return subwayTimeInfoArrayList.size();
    }

}