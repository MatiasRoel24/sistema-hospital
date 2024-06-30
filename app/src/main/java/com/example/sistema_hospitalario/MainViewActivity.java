package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

public class MainViewActivity extends AppCompatActivity {

    private EditText title;
    private Button buttonViewPatientList, buttonViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        //Obtener el email del usuario desde el Intent
        String userEmail = getIntent().getStringExtra("user_email");
        String userName = getIntent().getStringExtra("user_name");

        //Seteo en el titulo
        title.setText("Bienvenido " + userName);

        //Button Listener
        buttonViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainViewActivity.this, ProfileActivity.class);
                intent.putExtra("user_email", userEmail);
            }
        });

        //Button Listener
        buttonViewPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainViewActivity.this, ProfileActivity.class);
                intent.putExtra("user_email", userEmail);
            }
        });

    }
}
