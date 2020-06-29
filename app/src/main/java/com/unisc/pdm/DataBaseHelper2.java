package com.unisc.pdm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper2 extends SQLiteOpenHelper {
    public DataBaseHelper2(@Nullable Context context) {
        super(context, "banco.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS cores "
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " R TEXT NOT NULL," +
                " G TEXT NOT NULL, " +
                " B TEXT NOT NULL, " +
                " nome TEXT NOT NULL  ); ";

        try {
            db.execSQL( sql );
            Log.i("INFO DB", "Sucesso ao criar a tabela" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage() );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
