package com.example.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

public class Validator {

    public boolean validarCampos(String nombre, String apellido, String username, String password, String fechanacimiento, int discapacidad, int discapacidadSI, int tipoDiscapacidad, int gradoDiscapacidad) {
        if (!nombre.isEmpty() && !apellido.isEmpty() && !username.isEmpty() && !password.isEmpty() && validarFecha(fechanacimiento) && discapacidad != -1) {
            if (discapacidad == discapacidadSI) {
                //El indice 0 representa la opcion de "Seleccione"
                return tipoDiscapacidad != 0 && gradoDiscapacidad != 0;
            }
            return true;
        }
        return false;
    }

    public boolean validarCampos(String nombre, String apellido, String fechanacimiento, int discapacidad, int discapacidadSI, int tipoDiscapacidad, int gradoDiscapacidad) {
        if (!nombre.isEmpty() && !apellido.isEmpty() && validarFecha(fechanacimiento) && discapacidad != -1) {
            if (discapacidad == discapacidadSI) {
                //El indice 0 representa la opcion de "Seleccione"
                return tipoDiscapacidad != 0 && gradoDiscapacidad != 0;
            }
            return true;
        }
        return false;
    }

    public boolean validarFecha(String fecha) {
        //Se validan las fechas en formato(dd-MM-yyyy)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
