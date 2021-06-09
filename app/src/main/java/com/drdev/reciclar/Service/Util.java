package com.drdev.reciclar.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.drdev.reciclar.R;

public class Util {

    private Context context;

    public Util(Context _context) {
        context = _context;
    }

    public void Toast(String msg, int leng){
        Toast.makeText(context, msg, leng == 1 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public void SaveUserPrefs(String key, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.UserSharedPrefs), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String GetUserPrefsString( String key, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.UserSharedPrefs), context.MODE_PRIVATE);
        return sharedPref.getString(key, value);
    }
}
