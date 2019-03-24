package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by DELL on 10-11-2017.
 */

public class DetailsManager {
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "name";
    private static final String FOOD_CODE = "foodcode";

    //Buses Start
    public static final String BUSES_ID = "busesid";
    public static final String BUSES_TABLE = "busestable";
    public static final String BUSES_TITLE = "busestitle";
    public static final String BUSES_NUMBER = "busesnumber";
    public static final String BUSES_DRIVER = "busesdriver";
    public static final String BUSES_EMAIL = "busesemail";
    public static final String BUSES_LON = "longitude";
    public static final String BUSES_LAT = "latitude";
    //Buses End//

    //Tickets Start
    public static final String TICKET_ID = "ticketid";
    public static final String TICKET_TABLE = "tickettable";
    public static final String TICKET_DATE = "ticketdate";
    public static final String TICKET_TIME = "tickettime";
    public static final String TICKET_NUMBER = "ticketnumber";
    public static final String TICKET_QUANTITY = "ticketquantity";
    public static final String TICKET_PRICE = "ticketprice";
    public static final String TICKET_EMAIL = "ticketemail";
    public static final String TICKET_SOURCE = "ticketsource";
    public static final String TICKET_DESTINATION = "ticketdestination";
    //Ticket End
    //FARE START
    public static final String FARE_ID = "fareid";
    public static final String FARE_TABLE = "faretable";
    public static final String FARE_SOURCE = "faresource";
    public static final String FARE_DESTINATION = "faredestination";
    public static final String FARE_PRICE = "fareprice";
    //FARE END


    public static final String Schedule_TABLE = "Schduletable";
    public static final String Schedule_COLUMN_TITLE = "scheduletitle";
    public static final String Schedule_COLUMN_TIME = "scheduletime";
    public static final String MESSAGES_TABLE = "messagestable";
    public static final String MESSAGES_COLUMN_TITLE = "msghead";
    public static final String MESSAGES_COLUMN_BODY = "msgbody";
    public static final String MESSAGES_COLUMN_TIME = "msgtime";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION_TYPE_ID = "notification_type_id";
    public static final String NOTIFICATION_TITLE = "notification_title";
    public static final String NOTIFICATION_CONTENT = "notification_content";
    public static final String NOTIFICATION_ACTION_ID = "notification_action_id";
    public static final String NOTIFICATION_ACTION_NAME = "notification_action_name";
    public static final String NOTIFICATION_TIMESTAMP = "notification_timestamp";
    public static final String NOTIFICATION_UNREAD = "notification_unread";
    // Sharedpref file name
    private static final String PREF_NAME = "TECHXTERPREF";
    private static final String GOOGLE_PROFILE_URI = "google_profile_uri";
    private static final String CONTACT_NO = "contact_no";
    // shared preference for first time run
    private static final String IS_FIRST = "is_first";
    private static final String USER_EMAIL = "email";


    private static final String TEAM = "team";

    private static final String VENUE = "venue";

    // All Shared Preferences Keys
    private SharedPreferences pref;
    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Constructor
    public DetailsManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getTeam() {
        return pref.getString(TEAM, "");
    }

    public void setTeam(String team) {
        editor.putString(TEAM, team).apply();
    }

    public String getVENUE() {
        return pref.getString(VENUE, "");
    }

    public void setVenue(String venue) {
        editor.putString(VENUE, venue).apply();
    }

    public String getFoodCode() {
        return pref.getString(FOOD_CODE, "");
    }


    public String getUserId() {
        return pref.getString(USER_ID, " ");
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId).apply();
    }


    public String getGoogleProfileUri() {

        return pref.getString(GOOGLE_PROFILE_URI, "");
    }

    public void setFoodCode(String foodcode) {
        editor.putString(FOOD_CODE, foodcode).apply();
    }

    public void setGoogleProfileUri(String uri) {

        editor.putString(GOOGLE_PROFILE_URI, uri);

    }

    public String getName() {

        return pref.getString(USER_NAME, "");
        //pref.getString(USER_NAME, "name");
    }

    public void setName(String name) {
        editor.putString(USER_NAME, name).apply();
    }

    public Boolean getIsFirst() {
        return pref.getBoolean(IS_FIRST, false);
    }

    public void setIsFirst(boolean value) {
        editor.putBoolean(IS_FIRST, true).apply();
    }


    public void setContactNo(String contact) {
        editor.putString(CONTACT_NO, contact).apply();
    }

    public String getContactNo() {
        return pref.getString(CONTACT_NO, "none");
    }

    public void setEmail(String email) {
        editor.putString(USER_EMAIL, email).apply();
    }

    public String getUserEmail() {
        Log.d("kartoy", pref.getString(USER_EMAIL, "null"));
        return pref.getString(USER_EMAIL, "null");
    }

}
