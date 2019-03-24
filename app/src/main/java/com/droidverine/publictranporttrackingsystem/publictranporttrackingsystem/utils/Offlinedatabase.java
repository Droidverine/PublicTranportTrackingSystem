package com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Buses;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Fares;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Messages;
import com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.Models.Tickets;

import java.util.ArrayList;
import java.util.HashMap;

import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_DRIVER;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_EMAIL;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_ID;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_LAT;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_LON;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_NUMBER;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_TABLE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.BUSES_TITLE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.FARE_DESTINATION;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.FARE_ID;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.FARE_PRICE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.FARE_SOURCE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.FARE_TABLE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_DATE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_DESTINATION;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_EMAIL;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_ID;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_NUMBER;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_PRICE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_QUANTITY;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_SOURCE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_TABLE;
import static com.droidverine.publictranporttrackingsystem.publictranporttrackingsystem.DetailsManager.TICKET_TIME;


/**
 * Created by DELL on 13-12-2017.
 */

public class Offlinedatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TPSB.db";
    public static final String MESSAGES_TABLE_NAME = "MessagesTable";
    public static final String MESSAGES_COLUMN_ID = "msgid";


    public static final String MESSAGES_COLUMN_TITLE = "msghead";
    public static final String MESSAGES_COLUMN_BODY = "msgbody";
    public static final String MESSAGES_UID = "uid";


    private static final String TRUNCATE_MESSAGES = "DELETE FROM " + MESSAGES_TABLE_NAME;

    private static final String CREATE_NOTIF_TABLE = "CREATE TABLE IF NOT EXISTS " + MESSAGES_TABLE_NAME + " ( " +
            MESSAGES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " + MESSAGES_UID + " VARCJAR DEFAULT NULL ," +
            MESSAGES_COLUMN_TITLE + " VARCHAR DEFAULT NULL, " +
            MESSAGES_COLUMN_BODY + " , " + DetailsManager.MESSAGES_COLUMN_TIME + " VARCHAR DEFAULT NULL );";

//BUS TABLE START

    private static final String CREATE_BUSES_TABLE = "CREATE TABLE IF NOT EXISTS " + BUSES_TABLE + " ( " +
            BUSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DetailsManager.BUSES_TITLE + " VARCHAR DEFAULT NULL, "
            + BUSES_EMAIL + " VARCHAR DEFAULT NULL, " + BUSES_LAT + " VARCHAR DEFAULT NULL, " + BUSES_LON + " VARCHAR DEFAULT NULL, "
            + BUSES_DRIVER + " VARCHAR DEFAULT NULL, " +
            DetailsManager.BUSES_NUMBER + " VARCHAR DEFAULT NULL );";

    private static final String TRUNCATE_BUSES = "DELETE FROM " + BUSES_TABLE;

//BUS TABLE END


//FARE TABLE START

    private static final String CREATE_FARE_TABLE = "CREATE TABLE IF NOT EXISTS " + FARE_TABLE + " ( " +
            FARE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DetailsManager.FARE_SOURCE + " VARCHAR DEFAULT NULL, "
            + FARE_DESTINATION + " VARCHAR DEFAULT NULL, " + FARE_PRICE + " VARCHAR DEFAULT NULL );";

    private static final String TRUNCATE_FARE = "DELETE FROM " + FARE_TABLE;

    //FARE TABLE END
    private static final String CREATE_TICKETS_TABLE = "CREATE TABLE IF NOT EXISTS " + TICKET_TABLE + " ( " +
            TICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DetailsManager.TICKET_NUMBER + " VARCHAR DEFAULT NULL, "
            + TICKET_EMAIL + " VARCHAR DEFAULT NULL, " + TICKET_DATE + " VARCHAR DEFAULT NULL, " + TICKET_TIME + " VARCHAR DEFAULT NULL, "
            + TICKET_SOURCE + " VARCHAR DEFAULT NULL, " + TICKET_DESTINATION + " VARCHAR DEFAULT NULL, " + TICKET_QUANTITY +" VARCHAR DEFAULT NULL, "
            + TICKET_PRICE + " VARCHAR DEFAULT NULL );";

    private static final String TRUNCATE_TICKETS = "DELETE FROM " + TICKET_TABLE;
//TICKETS TABLE START


//TICKETS TABLE END

    public Offlinedatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTIF_TABLE);
        sqLiteDatabase.execSQL(CREATE_BUSES_TABLE);
        sqLiteDatabase.execSQL(CREATE_TICKETS_TABLE);
        sqLiteDatabase.execSQL(CREATE_FARE_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insertNotifications(HashMap<String, String> map) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MESSAGES_UID, map.get(MESSAGES_UID));
        values.put(MESSAGES_COLUMN_TITLE, map.get(DetailsManager.MESSAGES_COLUMN_TITLE));
        values.put(MESSAGES_COLUMN_BODY, map.get(DetailsManager.MESSAGES_COLUMN_BODY));
        values.put(DetailsManager.MESSAGES_COLUMN_TIME, map.get(DetailsManager.MESSAGES_COLUMN_TIME));

        db.insert(MESSAGES_TABLE_NAME, null, values);
        Log.d("inserted", "data");

    }

    public void truncateNotifications() {
        SQLiteDatabase database = getWritableDatabase();
        Log.d("table truncated", "dropped");
        database.execSQL(TRUNCATE_MESSAGES);
    }

    public ArrayList<Messages> getAllNotifications() {
        ArrayList<Messages> messagesList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(MESSAGES_TABLE_NAME, new String[]{"*"}, null, null, null, null, null);
        //Cursorr cursoror=db.rawQuery("SELECT * FROM "+Constants.NOTIFICATIONS_TABLE_NAME+" ORDER BY "+Constants.NOTIFICATION_TIMESTAMP,null);
        //for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Messages notification_data = new Messages();
            notification_data.setUid(cursor.getString(cursor.getColumnIndex(MESSAGES_UID)));
            notification_data.setMsghead(cursor.getString(cursor.getColumnIndex(MESSAGES_COLUMN_TITLE)));
            notification_data.setMsgtext(cursor.getString(cursor.getColumnIndex(MESSAGES_COLUMN_BODY)));
            Long l = Long.parseLong(cursor.getString(cursor.getColumnIndex(DetailsManager.MESSAGES_COLUMN_TIME)));
            notification_data.setMsgtime(l);
            messagesList.add(notification_data);
        }
        cursor.close();
        db.close();
        return messagesList;
    }

    //BUSES STARTS HERE

    public void insertBuses(HashMap<String, String> map) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BUSES_TITLE, map.get(DetailsManager.BUSES_TITLE));
        values.put(BUSES_DRIVER, map.get(DetailsManager.BUSES_DRIVER));
        values.put(BUSES_NUMBER, map.get(DetailsManager.BUSES_NUMBER));
        values.put(BUSES_EMAIL, map.get(DetailsManager.BUSES_EMAIL));
        values.put(BUSES_LAT, map.get(DetailsManager.BUSES_LAT));
        values.put(BUSES_LON, map.get(DetailsManager.BUSES_LON));


        db.insert(BUSES_TABLE, null, values);
        Log.d("Driver Data", "inserted");


    }

    public void truncateBuses() {
        SQLiteDatabase database = getWritableDatabase();
        Log.d("table truncated", "dropped");
        database.execSQL(TRUNCATE_BUSES);
    }

    public ArrayList<Buses> getBuses(String number) {

        ArrayList<Buses> participantsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        //  Cursor cursor = db.query(PARTICIPANTS_TABLE, new String[]{"*"},null, null, null, null, null);
        String cursorQuery = "SELECT * FROM " + BUSES_TABLE + " WHERE " + DetailsManager.BUSES_NUMBER + " =" + number + " ORDER BY busesnumber;";
        Cursor cursor = db.rawQuery(cursorQuery, null);
        Log.d("BUSESdata", "sss"+cursor.toString());

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Buses buses_data = new Buses();
            buses_data.setBusnumber(cursor.getString(cursor.getColumnIndex(DetailsManager.BUSES_NUMBER)));
            buses_data.setBustitle(cursor.getString(cursor.getColumnIndex(DetailsManager.BUSES_TITLE)));
            buses_data.setUsername(cursor.getString(cursor.getColumnIndex(DetailsManager.BUSES_DRIVER)));
            buses_data.setEmail(cursor.getString(cursor.getColumnIndex(DetailsManager.BUSES_EMAIL)));
            buses_data.setLat(cursor.getString(cursor.getColumnIndex(DetailsManager.BUSES_LAT)));
            buses_data.setLon(cursor.getString(cursor.getColumnIndex(DetailsManager.BUSES_LON)));
            ;
            participantsArrayList.add(buses_data);
            Log.d("BUSESdata", cursor.getString(cursor.getColumnIndex(DetailsManager.BUSES_TITLE)));
        }
        cursor.close();
        db.close();
        return participantsArrayList;
    }


    //FARES
    public ArrayList<Fares> getFares(String number) {

        ArrayList<Fares> participantsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = db.query(FARE_TABLE, new String[]{"*"},null, null, null, null, null);

        // String cursorQuery = "SELECT * FROM " + BUSES_TABLE + " WHERE " + DetailsManager.FARE_SOURCE + " =" + number + " ORDER BY busesnumber;";
       // Cursor cursor = db.rawQuery(cursorQuery, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Fares buses_data = new Fares();
            buses_data.setSource(cursor.getString(cursor.getColumnIndex(DetailsManager.FARE_SOURCE)));


            Log.d("BUSESdata", cursor.getString(cursor.getColumnIndex(DetailsManager.FARE_PRICE)));

            // participantsArrayList.add(buses_data);
        }
        cursor.close();
        db.close();
        return participantsArrayList;
    }

    public void insertFares(HashMap<String, String> map) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FARE_SOURCE, map.get(DetailsManager.FARE_SOURCE));
        values.put(FARE_DESTINATION, map.get(DetailsManager.FARE_DESTINATION));
        values.put(FARE_PRICE, map.get(DetailsManager.FARE_PRICE));
        db.insert(FARE_TABLE, null, values);
        Log.d("FARE Data", "inserted");


    }

    public void truncateFares() {
        SQLiteDatabase database = getWritableDatabase();
        Log.d("table truncated", "dropped");
        database.execSQL(TRUNCATE_FARE);
    }

    //Tickets STARt
    public void insertTickets(HashMap<String, String> map) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TICKET_NUMBER, map.get(DetailsManager.TICKET_NUMBER));
        values.put(TICKET_PRICE, map.get(DetailsManager.TICKET_PRICE));
        values.put(TICKET_QUANTITY, map.get(DetailsManager.TICKET_QUANTITY));
        values.put(TICKET_DATE, map.get(DetailsManager.TICKET_DATE));
        values.put(TICKET_TIME, map.get(DetailsManager.TICKET_TIME));
        values.put(TICKET_SOURCE, map.get(DetailsManager.TICKET_SOURCE));
        values.put(TICKET_DESTINATION, map.get(DetailsManager.TICKET_DESTINATION));
        values.put(TICKET_EMAIL, map.get(DetailsManager.TICKET_EMAIL));
        db.insert(TICKET_TABLE, null, values);
        Log.d("Tickets Data", "inserted");

    }

    public ArrayList<Tickets> getTickets(String email) {

        ArrayList<Tickets> ticketsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        //  Cursor cursor = db.query(PARTICIPANTS_TABLE, new String[]{"*"},null, null, null, null, null);
        String cursorQuery = "SELECT * FROM " + TICKET_TABLE + " WHERE " + DetailsManager.TICKET_EMAIL + " = '" + email + "' ORDER BY ticketdate;";
        Cursor cursor = db.rawQuery(cursorQuery, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Tickets tickets_data = new Tickets();
            tickets_data.setDestination(cursor.getString(cursor.getColumnIndex(DetailsManager.TICKET_DESTINATION)));
            tickets_data.setSource(cursor.getString(cursor.getColumnIndex(DetailsManager.TICKET_SOURCE)));
            tickets_data.setPrice(cursor.getString(cursor.getColumnIndex(DetailsManager.TICKET_PRICE)));
            tickets_data.setQuantity(cursor.getString(cursor.getColumnIndex(DetailsManager.TICKET_QUANTITY)));
            tickets_data.setTime(cursor.getString(cursor.getColumnIndex(DetailsManager.TICKET_TIME)));
            tickets_data.setDate(cursor.getString(cursor.getColumnIndex(DetailsManager.TICKET_DATE)));

            ticketsArrayList.add(tickets_data);
            Log.d("TICKETDATA", cursor.getString(cursor.getColumnIndex(DetailsManager.TICKET_SOURCE)));
        }
        cursor.close();
        db.close();
        return ticketsArrayList;
    }

    public void truncateTickets() {
        SQLiteDatabase database = getWritableDatabase();
        Log.d("table truncated", "dropped");
        database.execSQL(TRUNCATE_TICKETS);
    }


}
