package com.example.models;

public class RecursoRespuestaForo {
    private RecursoTemaForo temaForo;
    private long idUser;
    private String autorRespuesta;
    private String descripcionRespuesta;

    public RecursoRespuestaForo(RecursoTemaForo temaForo, long idUser, String descripcionRespuesta) {
        this.temaForo = temaForo;
        this.idUser = idUser;
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public RecursoRespuestaForo(RecursoTemaForo temaForo, String autorRespuesta, String descripcionRespuesta) {
        this.temaForo = temaForo;
        this.autorRespuesta = autorRespuesta;
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public RecursoRespuestaForo(String autor, String descripcionRespuesta) {
        this.autorRespuesta = autor;
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public String getAutorRespuesta() {
        return autorRespuesta;
    }

    public void setAutorRespuesta(String autorRespuesta) {
        this.autorRespuesta = autorRespuesta;
    }

    public RecursoTemaForo getTemaForo() {
        return temaForo;
    }

    public void setTemaForo(RecursoTemaForo temaForo) {
        this.temaForo = temaForo;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }
}
