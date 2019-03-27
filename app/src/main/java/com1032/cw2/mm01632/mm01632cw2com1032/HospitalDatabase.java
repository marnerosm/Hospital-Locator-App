package com1032.cw2.mm01632.mm01632cw2com1032;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by marne on 16/05/2018.
 */

public class HospitalDatabase extends SQLiteOpenHelper{
    public static final String DATABASE_NAME= "hospitals_db";
    public static final String TABLE_NAME="hospitals_table";
    public static final String COL_1= "NAME";
    public static final String COL_2= "LATITUDE";
    public static final String COL_3= "LONGITUDE";
    public static final String COL_4= "CITY";
    public static final String COL_5= "TYPE";
    SQLiteDatabase db;

    public HospitalDatabase(Context context){
        super (context, DATABASE_NAME,null,1);
      db =this.getWritableDatabase();
    }

    /**
     * OnCreate method used to create the Sqlite Database.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (NAME TEXT PRIMARY KEY,LATITUDE INT,LONGITUDE INT,CITY TEXT, TYPE TEXT)");
        addHospital(db);
    }

    /**
     * onUpgrade method check if the database already exists.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    /**
     * addHospital method is used to manually add hospitals in the database.
     * @param db
     */
    public  void addHospital(SQLiteDatabase db){
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"The Royal Surrey Hospital\", 51.240902, -0.608113, \"Guildford\", \"Hospital\");");
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"Optegra Eye Hospital\", 51.241416, -0.613213, \"Guildford\", \"Clinic\");");
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"Nuffield Health Private Clinic\", 51.242114, -0.610982, \"Guildford\", \"Private\");");
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"BMI Mount Alvenia\", 51.235998, -0.564730, \"Guildford\", \"Private\");");
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"Woking Community Hospital\", 51.315068, -0.556502, \"Woking\", \"Hospital\");");
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"The Priory Hospital\", 51.322627, -0.622938, \"Woking\", \"Hospital\");");
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"Nuffield Health\", 51.332410, -0.561199, \"Woking\", \"Private\");");
        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES (\"The Surrey Osteopaths\",51.310328, -0.556741, \"Woking\", \"Clnic\");");


    }


    /**
     * getHospitals method uses a complex Sql Query to get the selected hospitals from the database.
     * It uses WHERE, AND ,and OR statements.
     * @param city
     * @param clinic
     * @param hospital
     * @param privateHospital
     * @return
     */
    public Cursor getHospitals(String city,String clinic , String hospital , String privateHospital){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res= db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
         Cursor res = db.rawQuery("SELECT "+ COL_1 +", " + COL_2 + ", " + COL_3 +", " + COL_4 + ", " + COL_5 + " FROM "+ TABLE_NAME + " WHERE "+ COL_4 +" = '"+city+"' AND ("+COL_5+" = '"+ hospital+ "' OR "+ COL_5+"= '"+clinic+ "' OR "+ COL_5+" = '"+privateHospital+"')",null);
        return res;
    }


}
