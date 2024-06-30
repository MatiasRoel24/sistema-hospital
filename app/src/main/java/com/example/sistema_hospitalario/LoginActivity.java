package com.example.sistema_hospitalario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_hospitalario.dao.listeners.OnUserReceivedListener;
import com.example.sistema_hospitalario.dto.UserDTO;
import com.example.sistema_hospitalario.manager.ExecutorManager;
import com.example.sistema_hospitalario.manager.UserManager;

public class LoginActivity extends AppCompatActivity {

    private UserManager userManager;
    private ExecutorManager executorManager;

    private String emailStr, passwordStr;

    private EditText editTextEmail, editTextPassword;
    private Button buttonSaveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Inicializo de inputs
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //Button Login
        buttonSaveLogin = findViewById(R.id.buttonSaveLogin);

        //Button Listener
        buttonSaveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Strings
                emailStr = editTextEmail.getText().toString();
                passwordStr = editTextPassword.getText().toString();

                //
                loginCheck(emailStr, passwordStr);
            }
        });

    }

    private void loginCheck(String emailStr, String passwordStr) {
        //Validacion de los inputs
        if (emailStr.isEmpty() || passwordStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        //Executor
        executorManager = new ExecutorManager();

        //UserManager
        userManager = new UserManager(executorManager.getExecutorService());
        userManager.setContext(this);

        //Obtencion de usuario
        userManager.getUser(emailStr, new OnUserReceivedListener() {
            @Override
            public void onUserReceived(UserDTO user) {
                //Metodo para validar si el usuario es correcto
                validateUser(user, passwordStr);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void validateUser(UserDTO user, String passwordStr){
        if (user != null && user.getPassword().equals(passwordStr)) {
            Intent intent = new Intent(LoginActivity.this, MainViewActivity.class);
            intent.putExtra("user_email", user.getEmail());
            intent.putExtra("user_name", user.getUserName());
            startActivity(intent);
        } else {
            if(user == null) {
                runOnUiThread(() -> {
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                    Toast.makeText(LoginActivity.this, "El usuario que ingreso no existe", Toast.LENGTH_SHORT).show();
                });
            }
            runOnUiThread(() -> {
                editTextPassword.setText("");
                Toast.makeText(LoginActivity.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
