package com.example.models;

import java.time.LocalDate;

public class UsuarioDiscapacitado extends Usuario {
    private long IDDiscapacidad;
    private String tipoDiscapacidad;
    private String gradoDiscapacidad;
    private String descripcionDiscapacidad;

    public UsuarioDiscapacitado(String nombre, String apellido, String username, String password, String fechanacimiento, String tipoDiscapacidad, String gradoDiscapacidad, String descripcionDiscapacidad) {
        super(nombre, apellido, username, password, fechanacimiento);
        this.tipoDiscapacidad = tipoDiscapacidad;
        this.gradoDiscapacidad = gradoDiscapacidad;
        this.descripcionDiscapacidad = descripcionDiscapacidad;
    }

    public UsuarioDiscapacitado(long IDUsuario, long IDDiscapacidad, String nombre, String apellido, String username, String password, String fechanacimiento, String tipoDiscapacidad, String gradoDiscapacidad, String descripcionDiscapacidad) {
        super(IDUsuario, nombre, apellido, username, password, fechanacimiento);
        this.IDDiscapacidad = IDDiscapacidad;
        this.tipoDiscapacidad = tipoDiscapacidad;
        this.gradoDiscapacidad = gradoDiscapacidad;
        this.descripcionDiscapacidad = descripcionDiscapacidad;
    }

    public UsuarioDiscapacitado(String nombre, String apellido, String fechanacimiento, String tipoDiscapacidad, String gradoDiscapacidad, String descripcionDiscapacidad) {
        super(nombre, apellido, fechanacimiento);
        this.tipoDiscapacidad = tipoDiscapacidad;
        this.gradoDiscapacidad = gradoDiscapacidad;
        this.descripcionDiscapacidad = descripcionDiscapacidad;
    }

    public long getIDDiscapacidad() {
        return IDDiscapacidad;
    }

    public void setIDDiscapacidad(long IDDiscapacidad) {
        this.IDDiscapacidad = IDDiscapacidad;
    }

    public UsuarioDiscapacitado() {
        super();
    }

    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public String getGradoDiscapacidad() {
        return gradoDiscapacidad;
    }

    public void setGradoDiscapacidad(String gradoDiscapacidad) {
        this.gradoDiscapacidad = gradoDiscapacidad;
    }

    public String getDescripcionDiscapacidad() {
        return descripcionDiscapacidad;
    }

    public void setDescripcionDiscapacidad(String descripcionDiscapacidad) {
        this.descripcionDiscapacidad = descripcionDiscapacidad;
    }

    @Override
    public String toString() {
        return super.toString() + ",iddiscapacidad=" + IDDiscapacidad + ",tipoDiscapacidad=" + tipoDiscapacidad + ",gradoDiscapacidad=" + gradoDiscapacidad + ",descripcionDiscapacidad=" + descripcionDiscapacidad;
    }
}
