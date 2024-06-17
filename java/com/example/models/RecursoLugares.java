package com.example.models;

public class RecursoLugares {
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String contacto;
    private String horarios;
    private float resenas;

    public RecursoLugares(String nombre, String descripcion, String ubicacion, String contacto, String horarios, float resenas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.contacto = contacto;
        this.horarios = horarios;
        this.resenas = resenas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public float getResenas() {
        return resenas;
    }

    public void setResenas(float resenas) {
        this.resenas = resenas;
    }
}
