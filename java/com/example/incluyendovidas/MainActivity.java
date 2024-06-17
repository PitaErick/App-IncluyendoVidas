package com.example.incluyendovidas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controller.ControladorDataBase;

public class MainActivity extends AppCompatActivity {
    private TextView txtTit;
    private ControladorDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = ControladorDataBase.getInstancia(this);
        txtTit = findViewById(R.id.txtTituloBienvenida);
        txtTit.setText(txtTit.getText().toString() + db.getNombreUsuario());
    }

    public void mainLugares(View v) {
        Intent intent = new Intent(this, LugaresActivity.class);
        startActivity(intent);
    }

    public void mainForo(View v) {
        Intent intent = new Intent(this, ForosActivity.class);
        startActivity(intent);
    }

    public void mainInfo(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Metodos Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuPrincipal = getMenuInflater();
        menuPrincipal.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent opcion = null;
        if (item.getItemId() == R.id.itemAcerca) {
            opcion = new Intent(this, AcercadeActivity.class);
        } else if (item.getItemId() == R.id.itemPerfil) {
            opcion = new Intent(this, PerfilActivity.class);
        } else if (item.getItemId() == R.id.itemMenuPrincipal) {
            opcion = new Intent(this, MainActivity.class);
        }else if (item.getItemId() == R.id.itemCerrarSesion) {
            ControladorDataBase db = ControladorDataBase.getInstancia(this);
            db.finalizarSesion();
            opcion = new Intent(this, LoginActivity.class);
        }
        startActivity(opcion);
        return true;
    }
}