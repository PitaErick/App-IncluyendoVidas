package com.example.models;

import java.time.LocalDate;

public class Usuario {
    private long IDUsuario;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String fechanacimiento;

    public Usuario(String nombre, String apellido, String username, String password, String fechanacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.fechanacimiento = fechanacimiento;
    }
    public Usuario(long IDUsuario,String nombre, String apellido, String username, String password, String fechanacimiento) {
        this.IDUsuario=IDUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.fechanacimiento = fechanacimiento;
    }

    public Usuario(String nombre, String apellido, String fechanacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechanacimiento = fechanacimiento;
    }

    public Usuario() {

    }

    public long getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(long IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

        @Override
    public String toString() {
        return "iduser="+IDUsuario+",nombre="+nombre+",apellido="+apellido+",user="+username+",password="+password+",fnacimiento="+fechanacimiento;
    }
}
