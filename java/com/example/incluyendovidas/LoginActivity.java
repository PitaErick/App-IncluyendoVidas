package com.example.incluyendovidas;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controller.ControladorDataBase;

public class LoginActivity extends AppCompatActivity {
    private ControladorDataBase db;
    private EditText txtPassword;
    private TextView txtUser;
    private CheckBox checkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = ControladorDataBase.getInstancia(this);
        checkPreferenciasLogin();
        ToggleButton btnCheck = findViewById(R.id.btnShowPassword);
        txtPassword = findViewById(R.id.txtPassword);
        txtUser = findViewById(R.id.txtUser);
        checkLogin=findViewById(R.id.checkLogin);
        btnCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            txtPassword.setTypeface(txtUser.getTypeface());
        });
    }
    public void iniciarSesion(View v){
        TextView txtAlerta = findViewById(R.id.txtAlerta);
        String user = String.valueOf(txtUser.getText());
        String password = String.valueOf(txtPassword.getText());
        if(db.verificarUsuario(user,password)){
            if(checkLogin.isChecked()){
                db.guardarPreferencia();
            }
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            txtAlerta.setVisibility(View.VISIBLE);
        }
    }
    public void registrarUsuario(View v){
        Intent intent = new Intent(v.getContext(),RegistroActivity.class);
        startActivity(intent);
    }
    private void checkPreferenciasLogin(){
        if(db.checkPreferenciaLogin()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }
}