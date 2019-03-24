package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Tickets;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.R;

import java.util.ArrayList;

public class TicketsAdapter  extends RecyclerView.Adapter<TicketsAdapter.ticketsholder> {
    Context context;
    ArrayList<Tickets> ticketsArrayList;

    public TicketsAdapter(Context context, ArrayList<Tickets> ticketsArrayList) {
        this.context = context;
        this.ticketsArrayList = ticketsArrayList;
    }

    @NonNull
    @Override
    public TicketsAdapter.ticketsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ticket_item,null);
        return new TicketsAdapter.ticketsholder(view);    }

    @Override
    public void onBindViewHolder(@NonNull TicketsAdapter.ticketsholder holder, int position) {
     holder.ticketnumbertxt.setText("Ticket Number: " +ticketsArrayList.get(position).getQuantity());
        holder.timetxt.setText("Time: " +ticketsArrayList.get(position).getTime());
        holder.datetxt.setText("Date: " +ticketsArrayList.get(position).getDate());
        holder.desttxt.setText("Destination :" +ticketsArrayList.get(position).getDestination());
        holder.srctxt.setText("Source :" +ticketsArrayList.get(position).getSource());
        holder.quantitytxt.setText("Quantity :" +ticketsArrayList.get(position).getQuantity());


    }

    @Override
    public int getItemCount() {
        return ticketsArrayList.size();
    }
    class ticketsholder extends RecyclerView.ViewHolder {
        TextView datetxt,timetxt,desttxt,srctxt,ticketnumbertxt,quantitytxt;
        public ticketsholder(View itemView) {
            super(itemView);
            datetxt=itemView.findViewById(R.id.ticketdate);
            timetxt=itemView.findViewById(R.id.ticketime);
            ticketnumbertxt=itemView.findViewById(R.id.ticketnumber);
            srctxt=itemView.findViewById(R.id.ticketsrc);
            desttxt=itemView.findViewById(R.id.ticketdestination);
            quantitytxt=itemView.findViewById(R.id.ticketquantity);

        }
    }
}
