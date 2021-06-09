package com.drdev.reciclar.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.drdev.reciclar.Models.Usuario;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TBL_USUARIO = "Usuario";
    public static final String COLUMN_ID_USUARIO = "IdUsuario";
    public static final String COLUMN_NOME_USUARIO = "Nome";
    public static final String COLUMN_EMAIL_USUARIO = "Email";
    public static final String COLUMN_SENHA_USUARIO = "Senha";

    private StringBuilder sb;

    public DatabaseHelper(Context context) {
        super(context, "TaskLogger.db", null, 1);
        sb = new StringBuilder();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sb.append("CREATE TABLE " + TBL_USUARIO + " (");
        sb.append(COLUMN_ID_USUARIO + " INTEGER NOT NULL, ");
        sb.append(COLUMN_NOME_USUARIO + " TEXT NOT NULL, ");
        sb.append(COLUMN_EMAIL_USUARIO + " TEXT NOT NULL, ");
        sb.append(COLUMN_SENHA_USUARIO + " TEXT  NOT NULL, ");
        sb.append("PRIMARY KEY(" + COLUMN_ID_USUARIO + " AUTOINCREMENT) ");
        sb.append(");");

        db.execSQL(sb.toString());
        sb.delete(0, sb.length());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean CriarUsuario(Usuario model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOME_USUARIO, model.Nome);
        cv.put(COLUMN_EMAIL_USUARIO, model.Email);
        cv.put(COLUMN_SENHA_USUARIO, model.Senha);

        long retorno = db.insert(TBL_USUARIO, null, cv);
        db.close();
        return retorno != -1;
    }

    public Usuario ObterUsuarioLogin(Usuario filter) {
        Usuario model = new Usuario();

        String query = "SELECT * FROM " + TBL_USUARIO + " WHERE " + COLUMN_EMAIL_USUARIO + " = '" + filter.Email + "' AND " + COLUMN_SENHA_USUARIO + " = '" + filter.Senha + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            model.IdUsuario = cursor.getInt(0);
            model.Nome = cursor.getString (1);
            model.Email = cursor.getString(2);
            model.Senha = cursor.getString(3);
        }

        cursor.close();
        db.close();
        return model;
    }
}
