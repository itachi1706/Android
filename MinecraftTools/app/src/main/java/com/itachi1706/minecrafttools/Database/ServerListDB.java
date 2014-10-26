package com.itachi1706.minecrafttools.Database;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ServerListDB extends SQLiteOpenHelper{
	
	
		//DB Version
		public static final int DATABASE_VERSION = 1;

		//DB Name
		public static final String DATABASE_NAME = "database.db";
		
		//DB table Name
		public static final String TABLE_SERVER = "ServerList";
		
		//Codes table column names
		public static final String KEY_ID = "ID";
		public static final String KEY_SERVER_ADDR = "Server_Address";
		public static final String KEY_SERVER_PORT = "Server_Port";
		public static final String KEY_SERVER_VER = "Server_Ver";
		public static final String KEY_SERVER_NAME = "Server_Name";

		public ServerListDB(Context context){
			super(context, context.getExternalFilesDir(null)
	                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			String CREATE_TABLE_SCAN = "CREATE TABLE " + TABLE_SERVER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
					 + KEY_SERVER_ADDR + " VARCHAR(100) NOT NULL," + KEY_SERVER_PORT + " INTEGER DEFAULT 25565," + KEY_SERVER_VER + " VARCHAR(10) NOT NULL," +
					KEY_SERVER_NAME + " TEXT NULL)";
			db.execSQL(CREATE_TABLE_SCAN);
			//firstBoot();
			Log.d("DB: ", "Table created.");
		}
		
		@SuppressWarnings("unused")
		private void firstBoot(){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_SERVER_ADDR, "localhost");
			values.put(KEY_SERVER_PORT, 25565);
			values.put(KEY_SERVER_VER, "1.7");
			values.put(KEY_SERVER_NAME, "localhost");
			db.insert(TABLE_SERVER, null, values);
			db.close();
		}

        public void dropTableAndRenew(){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVER);
            onCreate(db);
        }
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			// Drop older table if existed
			Log.d("DB: ", "Dropping Table");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVER);
	 
	        // Create tables again
	        Log.d("DB: ", "Recreating table");
	        onCreate(db);
		}
		
		//Add a server with server name
		public void addServer(ServerList server){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_SERVER_ADDR, server.getAddress().getHostName());
			values.put(KEY_SERVER_PORT, server.getAddress().getPort());
			values.put(KEY_SERVER_VER, server.getVersion());
			values.put(KEY_SERVER_NAME, server.getName());
			db.insert(TABLE_SERVER, null, values);
			db.close();
		}
		
		//Edit a server's name
		public int editServerName(ServerList server){
			SQLiteDatabase db =  this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_SERVER_NAME, server.getName());
			return db.update(TABLE_SERVER, values, KEY_ID + " = ?", new String[] { String.valueOf(server.getId())});
		}
		
		//Delete a server record
		public void deleteServer(ServerList server){
			SQLiteDatabase db =  this.getWritableDatabase();
			db.delete(TABLE_SERVER, KEY_ID + " = ?", new String[] {String.valueOf(server.getId())});
			db.close();
		}
		
		//Return all server records
		public ArrayList<ServerList> getAllServers(){
			ArrayList<ServerList> serverList = new ArrayList<ServerList>();
			//Select all
			String selectQuery = "SELECT * FROM " + TABLE_SERVER;
			SQLiteDatabase db =  this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			//Loop through and add to list (id, server address, port, name
			if (cursor.moveToFirst()){
				do {
					ServerList server = new ServerList();
					server.setAddress(cursor.getString(1), cursor.getInt(2));
					server.setId(cursor.getInt(0));
					server.setName(cursor.getString(4));
					server.setVersion(cursor.getString(3));
					//Add to List
					serverList.add(server);
				} while (cursor.moveToNext());
			}
			
			//Return the contact list
			return serverList;
			
		}
	

}
