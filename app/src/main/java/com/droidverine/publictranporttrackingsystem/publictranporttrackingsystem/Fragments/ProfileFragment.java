package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Adapters.BusesAdapter;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Connmanager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.R;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils.Offlinedatabase;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils.PublicTransportTrackingSystem;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String loc;
    FirebaseDatabase firebaseDatabase;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Offlinedatabase offlinedatabase;
    LocationManager locationManager;
    private OnFragmentInteractionListener mListener;
    CircleImageView circleImageView;
    TextView username;
    Dialog myDialog;
    FloatingActionButton floatingActionButton;
    TextView contactnumtxt, Emailtxt, Addresstxt, Emergencytxt;
    LocationManager mLocationManager;
    Button editbtn;
    EditText contactedt, emergencyedt, addressedt;
    DetailsManager detailsManager;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");
        getdata();
        circleImageView = view.findViewById(R.id.avatar);
        username = view.findViewById(R.id.usernameprofile);
        floatingActionButton = view.findViewById(R.id.fabprf);
        contactnumtxt = view.findViewById(R.id.txtcontact);
        Emailtxt = view.findViewById(R.id.txtemail);
        Emergencytxt = view.findViewById(R.id.txtemerg);
        Addresstxt = view.findViewById(R.id.txtaddr);
         detailsManager=new DetailsManager(getActivity());

        contactnumtxt.setText("Contact Number: "+detailsManager.getContactNo());
        Emailtxt.setText("Email: "+detailsManager.getUserEmail());
        Addresstxt.setText("Address: "+detailsManager.getaddress());
        Emergencytxt.setText("Emergency :"+detailsManager.getUserEmergency());
        editbtn = view.findViewById(R.id.edtbtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialog = new Dialog(getContext());

                TextView txtclose;
                Log.d("prescriptionfrag", "chala");
                Button btncall;
                myDialog.setContentView(R.layout.custom_popup_profile);
                txtclose = (TextView) myDialog.findViewById(R.id.txtclosepro);
                txtclose.setText("X");
                contactedt = (EditText) myDialog.findViewById(R.id.contactedt);
                emergencyedt = (EditText) myDialog.findViewById(R.id.emergencyedt);
                addressedt = (EditText) myDialog.findViewById(R.id.addressedt);

                getdata();

                btncall = (Button) myDialog.findViewById(R.id.btnedidetails);
                btncall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setdata(contactedt.getText().toString(), emergencyedt.getText().toString(), addressedt.getText().toString());
                        Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                        getdata();
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

        locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 400, this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlocation();

              Log.d("alareala",""+new DetailsManager(getActivity()).getUserEmergency());
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(new DetailsManager(getActivity()).getUserEmergency(), null, "18.9902° N" + " 73.1277° E", null, null);
            }
        });
        Glide.with(getActivity()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).centerInside().into(circleImageView);
        //     circleImageView.setImageURI();
        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getActivity(),""+location.getLatitude(),Toast.LENGTH_LONG).show();
        loc=""+location.getLongitude();
        Log.d("LOCAR", "onLocationChanged: ");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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

    public void setdata(final String contactnum, final String emergency, final String addresss) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        myRef.child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                dataSnapshot.getRef().child("username").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                dataSnapshot.getRef().child("contact").setValue(contactnum);
                dataSnapshot.getRef().child("emergency").setValue(emergency);
                dataSnapshot.getRef().child("address").setValue(addresss);


                Log.d("firebasevalueset", "set");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });

    }

    public void getdata() {
        Connmanager connmanager = new Connmanager(getActivity());
        offlinedatabase = new Offlinedatabase(getContext());


        if (connmanager.checkNetworkConnection()) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String contact;
                    if(dataSnapshot.child("contact").getValue()!=null&& dataSnapshot.child("emergency").getValue()!=null
                            && dataSnapshot.child("address").getValue()!=null) {
                         contact = dataSnapshot.child("contact").getValue().toString();
                        detailsManager.setContactNo(contact);
                        String emergency = dataSnapshot.child("emergency").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();
                        detailsManager.setUserEmergency(emergency);
                        detailsManager.setUserAddress(address);
                    }

                    detailsManager.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    contactnumtxt.setText("Contact Number: "+detailsManager.getContactNo());
                    Emailtxt.setText("Email: "+detailsManager.getUserEmail());
                    Addresstxt.setText("Address: "+detailsManager.getaddress());
                    Emergencytxt.setText("Emergency :"+detailsManager.getUserEmergency());
                 /*   Emergencytxt.setText("Emergency Contact: "+emergency);
                    contactnumtxt.setText("Contact Number: "+contact);
                    Addresstxt.setText("Address: "+address);
                    */


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

            });
        }
    }
    void getlocation()
    {



    }
}
