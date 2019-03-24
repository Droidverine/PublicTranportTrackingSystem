package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Activities.MapsActivity;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Buses;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.R;

import java.util.List;

public class BusesAdapter extends RecyclerView.Adapter<BusesAdapter.busesholder> {
    Context context;
    List<Buses> busesList;

    public BusesAdapter(Context context, List<Buses> busesList) {
        this.context = context;
        this.busesList = busesList;
    }

    @NonNull
    @Override
    public BusesAdapter.busesholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.participant_item,null);
        return new busesholder(view);    }

    @Override
    public void onBindViewHolder(@NonNull BusesAdapter.busesholder holder,final int position) {
        holder.bustitle.setText("Bus Title :"+busesList.get(position).getBustitle());
        holder.busdriver.setText("Driver :"+busesList.get(position).getUsername());
        holder.busnumber.setText("Bus Number :"+busesList.get(position).getBusnumber());
        holder.busitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MapsActivity.class);
                intent.putExtra("lat",busesList.get(position).getLat());
                intent.putExtra("longi",busesList.get(position).getLon());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return busesList.size();
    }
    class busesholder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener
    {   TextView bustitle,busnumber,busdriver;
    CardView busitem;

        public busesholder(View itemView) {
            super(itemView);
            busitem=(CardView)itemView.findViewById(R.id.busitem);
            bustitle=(TextView)itemView.findViewById(R.id.bustitle);
            busnumber=(TextView)itemView.findViewById(R.id.busnumber);
            busdriver=(TextView)itemView.findViewById(R.id.busdriver);

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    }
}
