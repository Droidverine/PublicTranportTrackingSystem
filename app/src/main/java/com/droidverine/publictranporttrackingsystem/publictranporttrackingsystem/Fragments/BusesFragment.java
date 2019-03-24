package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Adapters.BusesAdapter;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Connmanager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Buses;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.R;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils.Offlinedatabase;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils.PublicTransportTrackingSystem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     EditText email;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Dialog myDialog;
    Offlinedatabase offlinedatabase;
    ProgressBar progressBar;
    FloatingActionButton floatingActionButton;
    TextView textView;
    DatabaseReference myRef;
    RecyclerView recyclerview;
   // PatientsAdapter patientsAdapter;
    BusesAdapter busesAdapter;
    FirebaseDatabase database;
    List<Buses> list = new ArrayList<>();


    public BusesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusesFragment newInstance(String param1, String param2) {
        BusesFragment fragment = new BusesFragment();
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

        View rview=inflater.inflate(R.layout.fragment_buses, container, false);
        recyclerview=rview.findViewById(R.id.prescriptionrecyler);
        progressBar = (ProgressBar) rview.findViewById(R.id.prescription_fragment_progressBar);
        new getPatientsFromDb().execute();

        floatingActionButton=rview.findViewById(R.id.prescriptionfab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialog = new Dialog(getContext());

                TextView txtclose;
                Log.d("prescriptionfrag","chala");
                Button btncall;
                myDialog.setContentView(R.layout.custom_popup_buses);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setText("X");
                email=(EditText)myDialog.findViewById(R.id.pateintemailedt);
                btncall = (Button) myDialog.findViewById(R.id.btnaddpatient);
                btncall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getdata(email.getText().toString());

                    }
                });
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

            }
        });

        new BusesFragment.getPatientsFromDb().execute();



        return rview;
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
    public class getPatientsFromDb extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            Log.d("MessageFragment", "onPreExecute ");
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params)
        {


           Offlinedatabase offlinedatabase=new Offlinedatabase( PublicTransportTrackingSystem.getInstance().getApplicationContext());
            list= offlinedatabase.getBuses("11");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            busesAdapter = new BusesAdapter(getActivity(),list);

            LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
            layoutmanager.setReverseLayout(false);
            layoutmanager.setStackFromEnd(false);
            recyclerview.setLayoutManager(layoutmanager);
            recyclerview.setItemAnimator( new DefaultItemAnimator());
            recyclerview.setAdapter(busesAdapter);

            progressBar.setVisibility(View.GONE);
            if (list.isEmpty())
            {
         //       textView.setVisibility(View.VISIBLE);
            }

        }
    }
    public void getdata(String number)
    {
        Connmanager connmanager=new Connmanager(getActivity());
        offlinedatabase=new Offlinedatabase(getContext());


        if(connmanager.checkNetworkConnection()) {
            offlinedatabase.truncateBuses();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Driver");
            final Query userQuery = rootRef.orderByChild("busnumber").equalTo(number);

            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("emailala", "" + dataSnapshot);

                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String keys = datas.getKey();
                        String driver=datas.child("username").getValue().toString();
                        String email = datas.child("email").getValue().toString();
                        String busnumber=datas.child("busnumber").getValue().toString();
                        String lat=datas.child("lat").getValue().toString();
                        String bustitle=datas.child("bustitle").getValue().toString();

                        String longi=datas.child("lon").getValue().toString();
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put(DetailsManager.BUSES_DRIVER, driver);
                        hashMap.put(DetailsManager.BUSES_NUMBER, busnumber);
                        hashMap.put(DetailsManager.BUSES_TITLE, bustitle);
                        hashMap.put(DetailsManager.BUSES_EMAIL, email);
                        hashMap.put(DetailsManager.BUSES_LON, longi);
                        hashMap.put(DetailsManager.BUSES_LAT, lat);



                        offlinedatabase.insertBuses(hashMap);
                        Log.d("DRIVERBUSES", "" + email);



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            new getPatientsFromDb().execute();

        }



    }
}
