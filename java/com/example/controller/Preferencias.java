package com.example.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {
    private final String PREFERENCIA_KEY="user_key";
    private final String PREFERENCIA_NOMBRE="credencialesLogin";
    private Context context;
    private SharedPreferences credencial;

    public Preferencias(Context context) {
        this.context = context.getApplicationContext();
    }

    /*
    public void guardarPreferenciaLogin(ControladorUsuarios user) {
        credencial = context.getSharedPreferences("credencialesLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = credencial.edit();
        editor.putString("usuario", user.toString());
        editor.commit();
    }*/

    public void guardarPreferenciaLogin(long userID) {
        credencial = context.getSharedPreferences(PREFERENCIA_NOMBRE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = credencial.edit();
        editor.putString(PREFERENCIA_KEY, String.valueOf(userID));
        editor.commit();
    }

    public void borrarPreferenciaLogin() {
        SharedPreferences credencial = context.getSharedPreferences(PREFERENCIA_NOMBRE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = credencial.edit();
        editor.clear();
        editor.commit();
    }

    public boolean checkPreferenciaLogin() {
        SharedPreferences credencial = context.getSharedPreferences(PREFERENCIA_NOMBRE, Context.MODE_PRIVATE);
        return (credencial.contains(PREFERENCIA_KEY));
    }

    public String getPreferenciaLogin() {
        SharedPreferences credencial = context.getSharedPreferences(PREFERENCIA_NOMBRE, Context.MODE_PRIVATE);
        String userString = credencial.getString(PREFERENCIA_KEY, null);
        if (userString != null) {
            return userString;
        }
        return "-1";
    }
}
