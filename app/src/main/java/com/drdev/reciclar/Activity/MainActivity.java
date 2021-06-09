package com.drdev.reciclar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.drdev.reciclar.R;
import com.drdev.reciclar.Service.Util;

public class MainActivity extends AppCompatActivity {
    private Button continuarBT;
    private Util _util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _util = new Util(MainActivity.this);
        if(CheckUser())
            startActivity(new Intent(MainActivity.this, HomeActivity.class));

        GetObjs();
        SetListeners();
    }

    private void GetObjs() {
        continuarBT = findViewById(R.id.BT_Comecar);
    }

    private void SetListeners(){
        continuarBT.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, InicioActivity.class)));
    }

    private boolean CheckUser() {
        String user = _util.GetUserPrefsString(getString(R.string.UserNameSharedPrefs), "");
        return !user.equals("");
    }
}