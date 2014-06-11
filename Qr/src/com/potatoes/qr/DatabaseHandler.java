package com.potatoes.qr;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	//DB Version
	public static final int DATABASE_VERSION = 3;

	//DB Name
	public static final String DATABASE_NAME = "scanned";
	
	//DB table Name
	public static final String TABLE_CODE = "Codes";
	
	//Codes table column names
	public static final String KEY_ID = "ID";
	public static final String KEY_CONTENTS = "BC_Contents";
	public static final String KEY_FORMAT = "BC_Format";
	public static final String KEY_ERROR = "BC_Error";
	public static final String KEY_ORIENT = "BC_Orientation";
	public static final String KEY_CONTENTS2 = "QR_Contents";
	public static final String KEY_FORMAT2 = "QR_Format";
	public static final String KEY_ERROR2 = "QR_Error";
	public static final String KEY_ORIENT2 = "QR_Orientation";
	
	
	public DatabaseHandler(Context context){
		super(context, Environment.getExternalStorageDirectory()
                + File.separator + "/Codes/" + File.separator
                + DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		String CREATE_TABLE_SCAN = "CREATE TABLE " + TABLE_CODE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				 + KEY_CONTENTS + " TEXT," + KEY_FORMAT + " TEXT," + KEY_ERROR + " TEXT," + KEY_ORIENT + " INTEGER," +
				KEY_CONTENTS2 + " TEXT," + KEY_FORMAT2 + " TEXT," + KEY_ERROR2 + " TEXT," + KEY_ORIENT2 + " INTEGER" + ")";
		db.execSQL(CREATE_TABLE_SCAN);
		Log.d("DB: ", "Table created.");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// Drop older table if existed
		Log.d("DB: ", "Dropping Table");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODE);
 
        // Create tables again
        Log.d("DB: ", "Recreating table");
        onCreate(db);
	}
	
	//Add
	void addCode(String content, String format, String error, Integer orientation, int check){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		Log.d("DB: ", "Checking qr or barcode...");
		if (check == 1){		//Barcode
		Log.d("DB: ", "Entering content...");
		values.put(KEY_CONTENTS, content);
		Log.d("DB: ", "Entering format...");
		values.put(KEY_FORMAT, format);
		Log.d("DB: ", "Entering orientation...");
		values.put(KEY_ORIENT, orientation);
		Log.d("DB: ", "Entering error...");
		values.put(KEY_ERROR, error);
		}
		if (check == 2){		//QR Code
			Log.d("DB: ", "Entering content...");
			values.put(KEY_CONTENTS2, content);
			Log.d("DB: ", "Entering format...");
			values.put(KEY_FORMAT2, format);
			Log.d("DB: ", "Entering orientation...");
			values.put(KEY_ORIENT2, orientation);
			Log.d("DB: ", "Entering error...");
			values.put(KEY_ERROR2, error);
		}
		Log.d("DB: ", "Inserting to table....");
		db.insert(TABLE_CODE, null, values);
		db.close();
	}

} 
