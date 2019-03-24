package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils;

import android.app.Application;

public class PublicTransportTrackingSystem extends Application {
    private static PublicTransportTrackingSystem mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }
    public static synchronized PublicTransportTrackingSystem getInstance() {
        return mInstance;
    }
    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
