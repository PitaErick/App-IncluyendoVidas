package com.example.incluyendovidas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controller.ControladorDataBase;
import com.example.controller.AdapterRecursoLugar;
import com.example.models.RecursoLugares;

import java.util.List;

public class LugaresActivity extends AppCompatActivity {
    private ControladorDataBase db;
    private RecyclerView recyclerView;
    private AdapterRecursoLugar mAdapter;
    private LinearLayout lytFiltroLugar;
    private CheckBox itemAuditiva;
    private CheckBox itemFisica;
    private CheckBox itemIntelectual;
    private CheckBox itemLenguaje;
    private CheckBox itemPsicosocial;
    private CheckBox itemVisual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lugares);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = ControladorDataBase.getInstancia(this);
        itemAuditiva = findViewById(R.id.filtroAuditiva);
        itemFisica = findViewById(R.id.filtroFisica);
        itemIntelectual = findViewById(R.id.filtroIntelectual);
        itemLenguaje = findViewById(R.id.filtroLenguaje);
        itemPsicosocial = findViewById(R.id.filtroPsicosocial);
        itemVisual = findViewById(R.id.filtroVisual);

        recyclerView = findViewById(R.id.recyclerViewLugares);
        lytFiltroLugar = findViewById(R.id.lytFiltroLugar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<RecursoLugares> dataList = db.getRecursosLugares();
        mAdapter = new AdapterRecursoLugar(dataList);
        recyclerView.setAdapter(mAdapter);
    }

    public void filtrarLugar(View v) {
        if (lytFiltroLugar.getVisibility() == View.GONE) {
            lytFiltroLugar.setVisibility(View.VISIBLE);
        } else if (lytFiltroLugar.getVisibility() == View.VISIBLE) {
            List<RecursoLugares> filteredData = db.getRecursosLugares(criterioBusqueda());
            mAdapter.updateData(filteredData);
            lytFiltroLugar.setVisibility(View.GONE);
        }
    }

    private String[] criterioBusqueda() {
        StringBuilder query = new StringBuilder();
        if (itemAuditiva.isChecked()) {
            query.append(itemAuditiva.getText());
            query.append(";");
        }
        if (itemLenguaje.isChecked()) {
            query.append(itemLenguaje.getText());
            query.append(";");
        }
        if (itemPsicosocial.isChecked()) {
            query.append(itemPsicosocial.getText());
            query.append(";");
        }
        if (itemVisual.isChecked()) {
            query.append(itemVisual.getText());
            query.append(";");
        }
        if (itemFisica.isChecked()) {
            query.append(itemFisica.getText());
            query.append(";");
        }
        if (itemIntelectual.isChecked()) {
            query.append(itemIntelectual.getText());
            query.append(";");
        }
        return query.toString().split(";");
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