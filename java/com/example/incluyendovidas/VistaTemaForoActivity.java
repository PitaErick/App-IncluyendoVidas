package com.example.incluyendovidas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.example.controller.AdapterRecursoVistaForo;
import com.example.controller.ControladorDataBase;
import com.example.models.RecursoRespuestaForo;
import com.example.models.RecursoTemaForo;

import java.util.List;

public class VistaTemaForoActivity extends AppCompatActivity {

    private ControladorDataBase db;
    private RecyclerView recyclerView;
    private AdapterRecursoVistaForo mAdapter;
    private TextView txtTitTema;
    private TextView txtDescripcionTema;
    private TextView txtAutorTema;
    private TextView txtComentario;
    private RecursoTemaForo temaForo;
    private LinearLayout lytRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vistatemaforo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = ControladorDataBase.getInstancia(this);
        temaForo = db.getForoByIDTemaForo(getIntent().getLongExtra("tema",-1));
        txtTitTema = findViewById(R.id.txtVistaForoTema);
        txtDescripcionTema = findViewById(R.id.txtVistaForoDescripcion);
        txtAutorTema = findViewById(R.id.txtVistaForoAutor);
        txtTitTema.setText(temaForo.getTema());
        txtDescripcionTema.setText(temaForo.getDescripcion());
        txtAutorTema.setText(temaForo.getAutor());
        lytRespuesta = findViewById(R.id.lytPublicarRespuesta);
        txtComentario=findViewById(R.id.txtComentario);

        recyclerView = findViewById(R.id.RecyclerViewVistaForo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<RecursoRespuestaForo> dataList = db.getRecursosRespuestasForo(temaForo);
        mAdapter = new AdapterRecursoVistaForo(dataList);
        recyclerView.setAdapter(mAdapter);
    }

    public void abrirSeccionRespuesta(View v){
        lytRespuesta.setVisibility(View.VISIBLE);
    }

    public void publicarRespuesta(View v){
        String comentario = String.valueOf(txtComentario.getText()).trim();
        if(!comentario.isEmpty()){
            if(db.publicarRespuesta(temaForo.getIdTemaForo(),comentario)){
                mAdapter.updateData(db.getRecursosRespuestasForo(temaForo));
                lytRespuesta.setVisibility(View.GONE);
            }else{
                Toast.makeText(this,"ERROR AL GUARDAR DATOS",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,"COMPLETE TODOS LOS CAMPOS",Toast.LENGTH_LONG).show();
        }
    }

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