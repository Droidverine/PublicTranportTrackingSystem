package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Adapters.BusesAdapter;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Adapters.TicketsAdapter;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Connmanager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Tickets;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.R;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils.Offlinedatabase;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils.PublicTransportTrackingSystem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TicketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Tickets> list;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Offlinedatabase offlinedatabase;
    FloatingActionButton floatingActionButton;
    private OnFragmentInteractionListener mListener;

    public TicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        //  getdata();
        recyclerView=view.findViewById(R.id.ticketsrecyler);
        progressBar=view.findViewById(R.id.ticket_fragment_progressBar);
        floatingActionButton = view.findViewById(R.id.addticketfab);
        getdata();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main2cont, new BookticketFragment());
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getdata() {
        Connmanager connmanager = new Connmanager(getActivity());
        offlinedatabase = new Offlinedatabase(getContext());
        if (connmanager.checkNetworkConnection()) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //  final Query userQuery = rootRef.orderByChild("busnumber").equalTo(number);

            rootRef.child("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("tickets", "" + dataSnapshot);
                    offlinedatabase.truncateTickets();

                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String date = datas.child("date").getValue().toString();
                        String time = datas.child("time").getValue().toString();
                        String source = datas.child("source").getValue().toString();
                        String desitnation = datas.child("destination").getValue().toString();
                        String price = datas.child("price").getValue().toString();
                        String quantity = datas.child("quantity").getValue().toString();
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put(DetailsManager.TICKET_SOURCE, source);
                        hashMap.put(DetailsManager.TICKET_DESTINATION, desitnation);
                        hashMap.put(DetailsManager.TICKET_NUMBER, "323223");
                        hashMap.put(DetailsManager.TICKET_EMAIL, Uid);
                        hashMap.put(DetailsManager.TICKET_TIME, time);
                        hashMap.put(DetailsManager.TICKET_DATE, date);
                        hashMap.put(DetailsManager.TICKET_PRICE, price);
                        hashMap.put(DetailsManager.TICKET_QUANTITY, quantity);


                        offlinedatabase.insertTickets(hashMap);
                        new getPatientsFromDb().execute();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
        new getPatientsFromDb().execute();


    }

    public class getPatientsFromDb extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.d("MessageFragment", "onPreExecute ");
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {


            Offlinedatabase offlinedatabase = new Offlinedatabase(PublicTransportTrackingSystem.getInstance().getApplicationContext());
            list = offlinedatabase.getTickets(FirebaseAuth.getInstance().getCurrentUser().getUid());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TicketsAdapter ticketsAdapter = new TicketsAdapter(getActivity(), list);

            LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
            layoutmanager.setReverseLayout(false);
            layoutmanager.setStackFromEnd(false);
            recyclerView.setLayoutManager(layoutmanager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(ticketsAdapter);

            progressBar.setVisibility(View.GONE);
            if (list.isEmpty()) {
                //       textView.setVisibility(View.VISIBLE);
            }

        }
    }
}
