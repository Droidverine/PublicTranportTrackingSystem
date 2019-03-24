package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Adapters.BusesAdapter;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Connmanager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Fares;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookticketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookticketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookticketFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayAdapter spinnerArrayAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabaseRef;

    private OnFragmentInteractionListener mListener;
    Offlinedatabase offlinedatabase;
    ProgressBar progressBar;
    ArrayList<Fares> list;
    ArrayList<String> spinnerdata, spinnerdest, spinnerprice;
    Spinner srcsp, destsp, quantitysp;
    ArrayAdapter<String> arrayAdapter;
    Button btnbook;

    public BookticketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookticketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookticketFragment newInstance(String param1, String param2) {
        BookticketFragment fragment = new BookticketFragment();
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
        View view = inflater.inflate(R.layout.fragment_bookticket, container, false);
        srcsp = view.findViewById(R.id.drpsource);
        list = new ArrayList<>();
        spinnerdest = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bookings");
        destsp = view.findViewById(R.id.drpdestination);
        quantitysp = view.findViewById(R.id.drpquant);
        btnbook=view.findViewById(R.id.btnbookticket);
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookticket();
            }
        });
        progressBar=view.findViewById(R.id.book_fragment_progressBar);
        getdata();
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
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("fare");
            final Query userQuery = rootRef.orderByChild("source");
            spinnerdata = new ArrayList<String>();
            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("fares", "" + dataSnapshot);
                    offlinedatabase.truncateFares();

                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String keys = datas.getKey();
                        String source = datas.child("source").getValue().toString();
                        String destination = datas.child("destination").getValue().toString();
                        String price = datas.child("price").getValue().toString();
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put(DetailsManager.FARE_SOURCE, source);
                        hashMap.put(DetailsManager.FARE_DESTINATION, destination);
                        hashMap.put(DetailsManager.FARE_PRICE, price);

                        offlinedatabase.insertFares(hashMap);
                        spinnerdest.add(destination);
                        spinnerdata.add(source);

                        new BookticketFragment
                                .getPatientsFromDb().execute();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }


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
            list = offlinedatabase.getFares("ss");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
          ArrayAdapter  arrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerdest);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.quantity, android.R.layout.simple_spinner_item);

            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerdata);
            srcsp.setAdapter(arrayAdapter);
            destsp.setAdapter(arrayAdapter1);
            quantitysp.setAdapter(adapter);
              progressBar.setVisibility(View.GONE);
            if (list.isEmpty()) {
                //       textView.setVisibility(View.VISIBLE);
            }

        }
    }

    public void bookticket()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy/MM /dd ");
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm a");
        tformat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String time= tformat.format(calendar.getTime());
        String strDate = dformat.format(calendar.getTime());
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).child("source").setValue("panvel");
        mDatabaseRef.child(uploadId).child("destination").setValue("dadar");
        mDatabaseRef.child(uploadId).child("quantity").setValue("1");
        mDatabaseRef.child(uploadId).child("price").setValue("10");
        mDatabaseRef.child(uploadId).child("date").setValue(strDate);
        mDatabaseRef.child(uploadId).child("time").setValue(time);


    }



}
