package com.example.controller;

import com.example.models.Usuario;
import com.example.models.UsuarioDiscapacitado;

import java.util.HashMap;
import java.util.Map;

public class ControladorUsuarios {

    private Usuario user = null;
    private UsuarioDiscapacitado userDiscapacitado = null;

    public ControladorUsuarios(Usuario user) {
        this.user = user;
    }

    public ControladorUsuarios(UsuarioDiscapacitado user) {
        this.userDiscapacitado = user;
    }

    public ControladorUsuarios() {

    }

    public Object getUser() {
        if(userDiscapacitado!=null){
            return userDiscapacitado;
        }else if(user!=null){
            return user;
        }
        return null;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void setUser(UsuarioDiscapacitado userDiscapacitado) {
        this.userDiscapacitado = userDiscapacitado;
    }

    private void checkTipoUser(Object tipoUser) {
        if (tipoUser instanceof UsuarioDiscapacitado) {
            userDiscapacitado = (UsuarioDiscapacitado) tipoUser;
        } else if (tipoUser instanceof Usuario) {
            user = (Usuario) tipoUser;
        }
    }

    @Override
    public String toString() {
        if(userDiscapacitado!=null){
            return userDiscapacitado.toString();
        }else if(user!=null){
            return user.toString();
        }
        return "";
    }


    private Map<String, String> getMapUsuarios(String userString) {
        Map<String, String> map = new HashMap<>();
        String[] parts = userString.split(",");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    public long getUserID() {
        if(userDiscapacitado!=null){
            return userDiscapacitado.getIDUsuario();
        }else if(user!=null){
            return user.getIDUsuario();
        }
        return -1;
    }

    public long getDiscapacidadID() {
        if(userDiscapacitado!=null){
            return userDiscapacitado.getIDDiscapacidad();
        }
        return -1;
    }

    public String getNombre() {
        if(userDiscapacitado!=null){
            return userDiscapacitado.getNombre();
        }else if(user!=null){
            return user.getNombre();
        }
        return "";
    }
}
