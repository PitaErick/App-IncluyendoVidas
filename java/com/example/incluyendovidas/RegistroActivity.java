package com.example.incluyendovidas;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
import com.example.models.Usuario;
import com.example.models.UsuarioDiscapacitado;
import com.example.validator.Validator;

public class RegistroActivity extends AppCompatActivity implements CalendarViewFragment.OnDateSelectedListener {
    private ControladorDataBase db;

    private TextView txtNombre;
    private TextView txtApellido;
    private TextView txtUsername;
    private TextView txtPassword;
    private TextView txtNacimiento;
    private TextView txtDescripcionDiscapacidad;
    private TextView txtAlerta;
    private RadioGroup groupDiscapacidad;
    private LinearLayout lytDiscapacidad;
    private Spinner spnTipoDiscapacidad;
    private Spinner spnGradoDiscapacidad;
    private ToggleButton btnShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = ControladorDataBase.getInstancia(this);
        //Adapters para spinners
        ArrayAdapter<String> adapterTipoDisc = new ArrayAdapter<>(this, R.layout.style_spinner, getResources().getStringArray(R.array.TiposDiscapacidades));
        ArrayAdapter<String> adapterGradoDisc = new ArrayAdapter<>(this, R.layout.style_spinner, getResources().getStringArray(R.array.GradoDiscapacidad));
        adapterTipoDisc.setDropDownViewResource(R.layout.style_spinner);
        adapterGradoDisc.setDropDownViewResource(R.layout.style_spinner);
        //metodos findViewById de todos los campos del formulario
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtDescripcionDiscapacidad = findViewById(R.id.txtDescripcionDiscapacidad);
        groupDiscapacidad = findViewById(R.id.groupDiscapacidad);
        lytDiscapacidad = findViewById(R.id.layoutDiscapacidad);
        spnTipoDiscapacidad = findViewById(R.id.spnTipoDiscapacidad);
        spnGradoDiscapacidad = findViewById(R.id.spnGradoDiscapacidad);
        spnTipoDiscapacidad.setAdapter(adapterTipoDisc);
        spnGradoDiscapacidad.setAdapter(adapterGradoDisc);
        txtAlerta = findViewById(R.id.txtAlertaRegistro);
        btnShowPassword = findViewById(R.id.btnShowPassword);
        btnShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            txtPassword.setTypeface(txtUsername.getTypeface());
        });
        groupDiscapacidad.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.dicapacidadSI) {
                lytDiscapacidad.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.dicapacidadNO) {
                lytDiscapacidad.setVisibility(View.GONE);
            }
        });
    }

    public void abrirCalendarview(View v) {
        CalendarViewFragment dialogFragment = new CalendarViewFragment();
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void registrarNuevoUsuario(View vista) {
        Validator v = new Validator();
        String nombre = String.valueOf(txtNombre.getText()).trim(),
                apellido = String.valueOf(txtApellido.getText()).trim(),
                username = String.valueOf(txtUsername.getText()).trim(),
                password = String.valueOf(txtPassword.getText()).trim(),
                fechanacimiento = String.valueOf(txtNacimiento.getText()).trim(),
                descripcionDiscapacidad = String.valueOf(txtDescripcionDiscapacidad.getText()).trim();
        int discapacidad = groupDiscapacidad.getCheckedRadioButtonId(),
                tipoDiscapacidad = spnTipoDiscapacidad.getSelectedItemPosition(),
                gradoDiscapacidad = spnGradoDiscapacidad.getSelectedItemPosition();
        System.out.println((v.validarCampos(nombre, apellido, username, password, fechanacimiento, discapacidad, R.id.dicapacidadSI, tipoDiscapacidad, gradoDiscapacidad)));
        System.out.println(discapacidad);
        if (v.validarCampos(nombre, apellido, username, password, fechanacimiento, discapacidad, R.id.dicapacidadSI, tipoDiscapacidad, gradoDiscapacidad)) {
            if (db.verificarUsuarioExistente(username)) {
                boolean flag=false;
                if (discapacidad == R.id.dicapacidadSI) {
                    flag=db.registrarUsuarioDiscapacitado(new UsuarioDiscapacitado(nombre,
                            apellido,
                            username,
                            password,
                            fechanacimiento,
                            String.valueOf(spnTipoDiscapacidad.getSelectedItem()),
                            String.valueOf(spnGradoDiscapacidad.getSelectedItem()),
                            descripcionDiscapacidad));
                } else if (discapacidad == R.id.dicapacidadNO) {
                    flag=db.registrarUsuarioNODiscapacitado(new Usuario(nombre,
                            apellido,
                            username,
                            password,
                            fechanacimiento));
                }
                if (flag){
                    limpiarFormulario();
                    Toast.makeText(this,"SE REGISTRO EL USUARIO EXITOSAMENTE",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"ERROR AL CREAR EL USUARIO",Toast.LENGTH_SHORT).show();
                    txtAlerta.setText("*ERROR: No se creo el usuario*");
                    txtAlerta.setVisibility(View.VISIBLE);
                }
            } else {
                txtAlerta.setText("*El Usuario ya existe*");
                txtAlerta.setVisibility(View.VISIBLE);
            }

        } else {
            txtAlerta.setText("*Complete todos los campos*");
            txtAlerta.setVisibility(View.VISIBLE);
            Toast.makeText(this, "ERROR EN VALIDAR CAMPOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelarRegistro(View v) {
        Intent intent = new Intent(v.getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateSelected(int year, int month, int dayOfMonth) {
        //dd-MM-yyyy
        txtNacimiento.setText(String.valueOf((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "-" + (month < 10 ? "0" + month : month) + "-" + year));
    }

    private void limpiarFormulario(){
        txtNombre.setText("");
        txtApellido.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtNacimiento.setText("");
        txtDescripcionDiscapacidad.setText("");
        txtAlerta.setText("");
        txtAlerta.setVisibility(View.GONE);
        spnTipoDiscapacidad.setSelection(0);
        spnGradoDiscapacidad.setSelection(0);
        groupDiscapacidad.clearCheck();
        lytDiscapacidad.setVisibility(View.GONE);
    }
}