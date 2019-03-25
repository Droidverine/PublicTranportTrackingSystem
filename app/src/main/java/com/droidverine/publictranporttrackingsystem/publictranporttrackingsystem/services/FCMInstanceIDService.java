package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.services;

import android.util.Log;

import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class FCMInstanceIDService extends FirebaseInstanceIdService
{
	
	String firebaseToken;
	DetailsManager session;
	
	@Override
	public void onTokenRefresh()
	{
		super.onTokenRefresh();
		session = new DetailsManager(this);
		firebaseToken = FirebaseInstanceId.getInstance().getToken();
		Log.d("Techxter_token",""+firebaseToken);
		getSharedPrefs(firebaseToken);
		
	}
	
	public void getSharedPrefs(String token)
	{
//        SharedPreferences sharedPreferences = this.getSharedPreferences("FCMSharedPref", this.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("auth_token",token);
//        editor.apply();
		Log.d("FCMInstanceIDService", "getSharedPrefs=" + token);
		session = new DetailsManager(this);
		//session.setFCM(token);
	}
}
