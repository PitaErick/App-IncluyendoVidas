package com.example.incluyendovidas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controller.AdapterRecursoListadoForos;
import com.example.controller.ControladorDataBase;
import com.example.models.RecursoTemaForo;

import java.util.List;

public class ForosActivity extends AppCompatActivity {
    private ControladorDataBase db;
    private RecyclerView recyclerView;
    private AdapterRecursoListadoForos mAdapter;
    private LinearLayout lytFiltroLugar;
    private LinearLayout lytPublicarForo;
    private LinearLayout lytListadoForos;
    private LinearLayout lytButtonForo;
    private CheckBox itemAuditiva;
    private CheckBox itemFisica;
    private CheckBox itemIntelectual;
    private CheckBox itemLenguaje;
    private CheckBox itemPsicosocial;
    private CheckBox itemVisual;
    private Button btnFiltro;
    private TextView txtTemaForo;
    private TextView txtContenidoForo;
    private TextView lblAlerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_foros);
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
        lytFiltroLugar = findViewById(R.id.lytFiltroLugar);
        lytPublicarForo = findViewById(R.id.lytPublicarForo);
        lytListadoForos = findViewById(R.id.lytListadoForos);
        lytButtonForo = findViewById(R.id.lytButtonForo);
        btnFiltro = findViewById(R.id.btnFiltrarLugar);
        txtTemaForo = findViewById(R.id.txtTemaPublicar);
        txtContenidoForo = findViewById(R.id.txtDescripcionPublicar);
        lblAlerta = findViewById(R.id.lblAlertaPublicarForo);

        recyclerView = findViewById(R.id.recyclerViewListadoForo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<RecursoTemaForo> dataList = db.getRecursosListadoForos();
        mAdapter = new AdapterRecursoListadoForos(this, dataList);
        recyclerView.setAdapter(mAdapter);
        cerrarSeccionPublicar();
    }

    public void filtrarListadoForos(View v) {
        if (lytFiltroLugar.getVisibility() == View.GONE) {
            lytFiltroLugar.setVisibility(View.VISIBLE);
        } else if (lytFiltroLugar.getVisibility() == View.VISIBLE) {
            List<RecursoTemaForo> filteredData = db.getRecursosListadoForos(criterioBusqueda());
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


    public void abriSeccionPublicar() {
        lytListadoForos.setVisibility(View.GONE);
        btnFiltro.setVisibility(View.GONE);
        lytButtonForo.setVisibility(View.GONE);
        lytFiltroLugar.setVisibility(View.VISIBLE);
        lytPublicarForo.setVisibility(View.VISIBLE);
    }

    public void cerrarSeccionPublicar() {
        lytListadoForos.setVisibility(View.VISIBLE);
        btnFiltro.setVisibility(View.VISIBLE);
        lytButtonForo.setVisibility(View.VISIBLE);
        lytFiltroLugar.setVisibility(View.GONE);
        lytPublicarForo.setVisibility(View.GONE);
    }

    public void abrirSeccion(View v) {
        abriSeccionPublicar();
    }

    public void publicarForo(View v) {
        String tema = String.valueOf(txtTemaForo.getText()).trim();
        String contenido = String.valueOf(txtContenidoForo.getText()).trim();
        if (!tema.isEmpty() && !contenido.isEmpty()) {
            if (db.publicarForo(tema, contenido, getTipoDiscapacidad())) {
                lblAlerta.setVisibility(View.GONE);
                mAdapter.updateData(db.getRecursosListadoForos());
                cerrarSeccionPublicar();
                Toast.makeText(this, "Se Publico Exitosamente su foro", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Se Produjo un error al guardar", Toast.LENGTH_LONG).show();
            }
        } else {
            lblAlerta.setVisibility(View.VISIBLE);
        }
    }

    private String getTipoDiscapacidad() {
        StringBuilder query = new StringBuilder();
        if (itemAuditiva.isChecked()) {
            query.append(itemAuditiva.getText());
            query.append(",");
        }
        if (itemLenguaje.isChecked()) {
            query.append(itemLenguaje.getText());
            query.append(",");
        }
        if (itemPsicosocial.isChecked()) {
            query.append(itemPsicosocial.getText());
            query.append(",");
        }
        if (itemVisual.isChecked()) {
            query.append(itemVisual.getText());
            query.append(",");
        }
        if (itemFisica.isChecked()) {
            query.append(itemFisica.getText());
            query.append(",");
        }
        if (itemIntelectual.isChecked()) {
            query.append(itemIntelectual.getText());
            query.append(",");
        }
        if (query.length() == 0) {
            query.append(itemAuditiva.getText());
            query.append(",");
            query.append(itemLenguaje.getText());
            query.append(",");
            query.append(itemPsicosocial.getText());
            query.append(",");
            query.append(itemVisual.getText());
            query.append(",");
            query.append(itemFisica.getText());
            query.append(",");
            query.append(itemIntelectual.getText());
            return query.toString();
        } else {
            return query.toString().substring(0, query.toString().length() - 1);
        }
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