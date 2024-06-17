package com.example.incluyendovidas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controller.ControladorDataBase;
import com.example.controller.ControladorUsuarios;
import com.example.controller.Preferencias;
import com.example.models.Usuario;
import com.example.models.UsuarioDiscapacitado;
import com.example.validator.Validator;

public class PerfilActivity extends AppCompatActivity implements CalendarViewFragment.OnDateSelectedListener {
    private ControladorDataBase db;

    private TextView txtNombre;
    private TextView txtApellido;
    private TextView txtNacimiento;
    private TextView txtDescripcionDiscapacidad;
    private TextView txtAlerta;
    private RadioGroup groupDiscapacidad;
    private LinearLayout lytDiscapacidad;
    private Spinner spnTipoDiscapacidad;
    private Spinner spnGradoDiscapacidad;
    private RadioButton itemSI;
    private RadioButton itemNo;
    private ArrayAdapter<String> adapterTipoDisc;
    private ArrayAdapter<String> adapterGradoDisc;
    private CheckBox checkPreferencia;
    private Preferencias preferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = ControladorDataBase.getInstancia(this);
        preferencia= new Preferencias(this);
        //Adapters para spinners
        adapterTipoDisc = new ArrayAdapter<>(this, R.layout.style_spinnerv2, getResources().getStringArray(R.array.TiposDiscapacidades));
        adapterGradoDisc = new ArrayAdapter<>(this, R.layout.style_spinnerv2, getResources().getStringArray(R.array.GradoDiscapacidad));
        adapterTipoDisc.setDropDownViewResource(R.layout.style_spinnerv2);
        adapterGradoDisc.setDropDownViewResource(R.layout.style_spinnerv2);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtDescripcionDiscapacidad = findViewById(R.id.txtDescripcionDiscapacidad);
        groupDiscapacidad = findViewById(R.id.groupDiscapacidad);
        lytDiscapacidad = findViewById(R.id.layoutDiscapacidad);
        spnTipoDiscapacidad = findViewById(R.id.spnTipoDiscapacidadPerfil);
        spnGradoDiscapacidad = findViewById(R.id.spnGradoDiscapacidadPerfil);
        itemSI = findViewById(R.id.dicapacidadSI);
        itemNo = findViewById(R.id.dicapacidadNO);
        checkPreferencia = findViewById(R.id.checkPreferenciaPerfil);
        spnTipoDiscapacidad.setAdapter(adapterTipoDisc);
        spnGradoDiscapacidad.setAdapter(adapterGradoDisc);
        txtAlerta = findViewById(R.id.txtAlertaRegistro);
        checkPreferencia.setChecked(db.checkPreferenciaLogin());
        groupDiscapacidad.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.dicapacidadSI) {
                lytDiscapacidad.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.dicapacidadNO) {
                lytDiscapacidad.setVisibility(View.GONE);
            }
        });
        checkPreferencia.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                db.guardarPreferencia();
            }else{
                db.borrarPreferencia();
            }
        });
        Object user = db.getUsuario();
        if (user instanceof UsuarioDiscapacitado) {
            llenarCampos((UsuarioDiscapacitado) user);
        } else if (user instanceof Usuario) {
            llenarCampos((Usuario) user);
        }
    }

    private void llenarCampos(UsuarioDiscapacitado user) {
        txtNombre.setText(user.getNombre());
        txtApellido.setText(user.getApellido());
        txtNacimiento.setText(user.getFechanacimiento());
        txtDescripcionDiscapacidad.setText(user.getDescripcionDiscapacidad());
        itemSI.setChecked(true);
        spnTipoDiscapacidad.setSelection(adapterTipoDisc.getPosition(user.getTipoDiscapacidad()));
        spnGradoDiscapacidad.setSelection(adapterTipoDisc.getPosition(user.getTipoDiscapacidad()));
    }

    private void llenarCampos(Usuario user) {
        txtNombre.setText(user.getNombre());
        txtApellido.setText(user.getApellido());
        txtNacimiento.setText(user.getFechanacimiento());
        itemNo.setChecked(true);
    }

    public void editarUsuario(View vista) {
        Validator v = new Validator();
        String nombre = String.valueOf(txtNombre.getText()).trim(),
                apellido = String.valueOf(txtApellido.getText()).trim(),
                fechanacimiento = String.valueOf(txtNacimiento.getText()).trim(),
                descripcionDiscapacidad = String.valueOf(txtDescripcionDiscapacidad.getText()).trim();
        int discapacidad = groupDiscapacidad.getCheckedRadioButtonId(),
                tipoDiscapacidad = spnTipoDiscapacidad.getSelectedItemPosition(),
                gradoDiscapacidad = spnGradoDiscapacidad.getSelectedItemPosition();
        System.out.println((v.validarCampos(nombre, apellido, fechanacimiento, discapacidad, R.id.dicapacidadSI, tipoDiscapacidad, gradoDiscapacidad)));
        System.out.println(discapacidad);
        if (v.validarCampos(nombre, apellido, fechanacimiento, discapacidad, R.id.dicapacidadSI, tipoDiscapacidad, gradoDiscapacidad)) {

                boolean flag=false;
                if (discapacidad == R.id.dicapacidadSI) {
                    flag=db.editarUsuarioDiscapacitado(new UsuarioDiscapacitado(nombre,
                            apellido,
                            fechanacimiento,
                            String.valueOf(spnTipoDiscapacidad.getSelectedItem()),
                            String.valueOf(spnGradoDiscapacidad.getSelectedItem()),
                            descripcionDiscapacidad));
                } else if (discapacidad == R.id.dicapacidadNO) {
                    flag=db.editarUsuarioNODiscapacitado(new Usuario(nombre,
                            apellido,
                            fechanacimiento));
                }
                if (flag){
                    Toast.makeText(this,"SE EDITO EL USUARIO EXITOSAMENTE",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"ERROR AL CREAR EL USUARIO",Toast.LENGTH_SHORT).show();
                    txtAlerta.setText("*ERROR: No se edito el usuario*");
                    txtAlerta.setVisibility(View.VISIBLE);
                }

        } else {
            txtAlerta.setText("*Complete todos los campos*");
            txtAlerta.setVisibility(View.VISIBLE);
            Toast.makeText(this, "ERROR EN VALIDAR CAMPOS", Toast.LENGTH_SHORT).show();
        }
    }
    public void abrirCalendarview(View v) {
        CalendarViewFragment dialogFragment = new CalendarViewFragment();
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void cancelarPerfil(View v) {
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateSelected(int year, int month, int dayOfMonth) {
        //dd-MM-yyyy
        txtNacimiento.setText(String.valueOf((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + (month < 10 ? "0" + month : month) + "-" + year));
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