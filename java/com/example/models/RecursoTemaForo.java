package com.example.models;

public class RecursoTemaForo {
    private long idTemaForo;
    private String autor;
    private String tema;
    private String descripcion;
    private String tipoDiscapacidad;

    public RecursoTemaForo(long idTemaForo, String autor, String tema, String descripcion, String tipoDiscapacidad) {
        this.idTemaForo = idTemaForo;
        this.autor = autor;
        this.tema = tema;
        this.descripcion = descripcion;
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public long getIdTemaForo() {
        return idTemaForo;
    }

    public void setIdTemaForo(long idTemaForo) {
        this.idTemaForo = idTemaForo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
