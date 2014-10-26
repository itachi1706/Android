package com.itachi1706.minecrafttools.Database;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class McItemDB extends SQLiteOpenHelper{
	
	//DB Version
	public static final int DATABASE_VERSION = 1;

	//DB Name
	public static final String DATABASE_NAME = "database.db";
	
	//DB table Name
	public static final String TABLE_ITEMS = "McItem";
	
	//Codes table column names
	public static final String KEY_ID = "ID";
	public static final String KEY_BLOCK_ID = "blockID";
	public static final String KEY_BLOCK_SUBID = "blockSubId";
	public static final String KEY_NAME = "blockName";
	public static final String KEY_OBTAINABLE = "isObtainable";
	public static final String KEY_CRAFTABLE = "isCraftable";
	public static final String KEY_CRAFTSLOT_1 = "cs1";
	public static final String KEY_CRAFTSLOT_2 = "cs2";
	public static final String KEY_CRAFTSLOT_3 = "cs3";
	public static final String KEY_CRAFTSLOT_4 = "cs4";
	public static final String KEY_CRAFTSLOT_5 = "cs5";
	public static final String KEY_CRAFTSLOT_6 = "cs6";
	public static final String KEY_CRAFTSLOT_7 = "cs7";
	public static final String KEY_CRAFTSLOT_8 = "cs8";
	public static final String KEY_CRAFTSLOT_9 = "cs9";
	public static final String KEY_SMELTABLE = "isSmeltable";
	public static final String KEY_SMELTABLE_WITH = "smeltWith";
	public static final String KEY_IMAGE_URL = "imgURL";
	public static final String KEY_DESCRIPTION_URL = "descURL";
	public static final String KEY_ID_NAME = "blockIdName";
	
	
	public McItemDB(Context context){
		super(context, context.getExternalFilesDir(null)
	            + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		String CREATE_TABLE_SCAN = "CREATE TABLE " + TABLE_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				 + KEY_BLOCK_ID + " INT(10) NOT NULL," + KEY_BLOCK_SUBID + " INT(10) NOT NULL DEFAULT 0," + KEY_NAME + " VARCHAR(50) NOT NULL," +
				KEY_OBTAINABLE + " TINYINT(1) NOT NULL DEFAULT 0," + KEY_CRAFTABLE + " TINYINT(1) NULL DEFAULT O," + KEY_CRAFTSLOT_1 + " VARCHAR(10) NULL," + KEY_CRAFTSLOT_2 + " VARCHAR(10) NULL,"
				+ KEY_CRAFTSLOT_3 + " VARCHAR(10) NULL," + KEY_CRAFTSLOT_4 + " VARCHAR(10) NULL," + KEY_CRAFTSLOT_5 + " VARCHAR(10) NULL," + KEY_CRAFTSLOT_6 + " VARCHAR(10) NULL,"
				+ KEY_CRAFTSLOT_7 + " VARCHAR(10) NULL," + KEY_CRAFTSLOT_8 + " VARCHAR(10) NULL," + KEY_CRAFTSLOT_9 + " VARCHAR(10) NULL," + KEY_SMELTABLE + " TINYINT(1) NULL DEFAULT 0,"
				+ KEY_SMELTABLE_WITH + " VARCHAR(10) NULL," + KEY_IMAGE_URL + " VARCHAR(50) NULL," + KEY_DESCRIPTION_URL + " VARCHAR(50) NULL," + KEY_ID_NAME + " VARCHAR(100) NOT NULL DEFAULT 'minecraft:')";
		db.execSQL(CREATE_TABLE_SCAN);
		//firstBoot();
		Log.d("DB: ", "Table created.");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// Drop older table if existed
		Log.d("DB: ", "Dropping Table");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
 
        // Create tables again
        Log.d("DB: ", "Recreating table");
        onCreate(db);
	}
	
	public void dropTableAndRenew(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		onCreate(db);
	}
	
	//Add a Item with
	public void addItem(McItem item){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, item.getMainId());
		values.put(KEY_BLOCK_ID, item.getId());
		values.put(KEY_BLOCK_SUBID, item.getSubId());
		values.put(KEY_NAME, item.getName());
		values.put(KEY_OBTAINABLE, item.getObtainableNormally());
		values.put(KEY_CRAFTSLOT_1, item.getCraftSlot1());
		values.put(KEY_CRAFTSLOT_2, item.getCraftSlot2());
		values.put(KEY_CRAFTSLOT_3, item.getCraftSlot3());
		values.put(KEY_CRAFTSLOT_4, item.getCraftSlot4());
		values.put(KEY_CRAFTSLOT_5, item.getCraftSlot5());
		values.put(KEY_CRAFTSLOT_6, item.getCraftSlot6());
		values.put(KEY_CRAFTSLOT_7, item.getCraftSlot7());
		values.put(KEY_CRAFTSLOT_8, item.getCraftSlot8());
		values.put(KEY_CRAFTSLOT_9, item.getCraftSlot9());
		values.put(KEY_SMELTABLE, item.getIsSmeltable());
		values.put(KEY_SMELTABLE_WITH, item.getSmeltWith());
		values.put(KEY_IMAGE_URL, item.getImageURL());
		values.put(KEY_DESCRIPTION_URL, item.getDescriptionURL());
		values.put(KEY_CRAFTABLE, item.getIsCraftable());
		values.put(KEY_ID_NAME, item.getMinecraftIdName());
		db.insert(TABLE_ITEMS, null, values);
		db.close();
	}
	
	//Return all item records
	public ArrayList<McItem> getAllItems(){
		ArrayList<McItem> itemList = new ArrayList<McItem>();
		//Select all
		String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
		SQLiteDatabase db =  this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//Loop through and add to list (id, server address, port, name
		if (cursor.moveToFirst()){
			do {
				McItem item = new McItem(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),
                		cursor.getInt(4),cursor.getInt(5),cursor.getString(6),
                		cursor.getString(7),cursor.getString(8),
                		cursor.getString(9),cursor.getString(10),cursor.getString(11),
                		cursor.getString(12),cursor.getString(13),cursor.getString(14),
                		cursor.getInt(15),cursor.getString(16),cursor.getString(17),cursor.getString(18), cursor.getString(19));
				//Add to List
				itemList.add(item);
			} while (cursor.moveToNext());
		}
		
		//Return the contact list
		return itemList;
		
	}

}
