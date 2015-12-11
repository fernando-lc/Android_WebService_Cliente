package com.app.rentafinal2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME ="peliculas15";
	
	public SQLHelper(Context context){
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE rentas (idRenta TEXT, nombreCliente TEXT, nombrePelicula TEXT, fecha TEXT, cantidadPelicula TEXT)");
		db.execSQL("CREATE TABLE clientes (idCliente TEXT, nombreCliente TEXT)");
		db.execSQL("CREATE TABLE peliculas (idPelicula PRIMARY KEY, nombrePelicula TEXT)" );
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		android.util.Log.v("Constants", "Upgrading database ,which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS clientes");
		onCreate(db);
		
	}

}
