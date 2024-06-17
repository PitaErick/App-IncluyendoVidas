package com.example.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.database.DataBase;
import com.example.models.RecursoLugares;
import com.example.models.RecursoRespuestaForo;
import com.example.models.RecursoTemaForo;
import com.example.models.Usuario;
import com.example.models.UsuarioDiscapacitado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ControladorDataBase {
    private final SQLiteDatabase db;
    private static ControladorDataBase controlador;
    private ControladorUsuarios usuario;
    private Context context;

    private ControladorDataBase(Context context) {
        this.context = context;
        DataBase dbHelper = new DataBase(this.context);
        db = dbHelper.getWritableDatabase();
    }

    public static ControladorDataBase getInstancia(Context context) {
        if (controlador == null) {
            controlador = new ControladorDataBase(context.getApplicationContext());
        }
        return controlador;
    }

    public boolean verificarUsuario(String user, String password) {
        String query = "SELECT UserID FROM Usuarios WHERE Username=? and Password=?";
        Cursor c = db.rawQuery(query, new String[]{user, password});
        long id = -1;
        if (c.moveToFirst()) {
            id = c.getLong(c.getColumnIndexOrThrow("UserID"));
            asignarUsuario(id);
        }
        c.close();
        return id != -1;
    }

    private void asignarUsuario(long idUser) {
        String query = "SELECT DISTINCT * FROM Usuarios AS u INNER JOIN Discapacidades AS d ON u.DiscapacidadID=d.DiscapacidadID WHERE UserID=?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(idUser)});
        if (c.moveToFirst()) {
            long discapacidadID = c.getLong(c.getColumnIndexOrThrow("DiscapacidadID"));
            String nombre, apellido, username, password, fnacimiento, tipoDiscapacidad, gradoDiscapacidad, descripcionDiscapacidad;
            nombre = c.getString(c.getColumnIndexOrThrow("Nombre"));
            apellido = c.getString(c.getColumnIndexOrThrow("Apellido"));
            username = c.getString(c.getColumnIndexOrThrow("Username"));
            password = c.getString(c.getColumnIndexOrThrow("Password"));
            fnacimiento = c.getString(c.getColumnIndexOrThrow("FechaNacimiento"));
            tipoDiscapacidad = c.getString(c.getColumnIndexOrThrow("Tipo"));
            gradoDiscapacidad = c.getString(c.getColumnIndexOrThrow("Grado"));
            descripcionDiscapacidad = c.getString(c.getColumnIndexOrThrow("Descripcion"));
            if (discapacidadID != 1) {
                this.usuario = new ControladorUsuarios(new UsuarioDiscapacitado(idUser, discapacidadID, nombre, apellido, username, password, fnacimiento, tipoDiscapacidad, gradoDiscapacidad, descripcionDiscapacidad));
            } else {
                this.usuario = new ControladorUsuarios(new Usuario(idUser, nombre, apellido, username, password, fnacimiento));
            }
        } else {
            System.out.println("ERRROR EN ASIGNAR USUARIO");
        }
        c.close();
    }

    public boolean verificarUsuarioExistente(String user) {
        String query = "SELECT UserID FROM Usuarios WHERE Username=?";
        Cursor c = db.rawQuery(query, new String[]{user});
        boolean flag = c.getCount() == 1;
        c.close();
        return !flag;
    }

    public void registrarUsuarioDiscapacitado(String nombre, String apellido, String username, String password, String fechanacimiento, int discapacidad, int tipoDiscapacidad, int gradoDiscapacidad, String descripcionDiscapacidad) {

    }

    public boolean registrarUsuarioDiscapacitado(UsuarioDiscapacitado user) {
        if (verificarUsuarioExistente(user.getUsername())) {
            boolean flg1 = registrarDiscapacidad(user);
            boolean flg2 = registrarUsuarioModelo(user);
            return flg1 && flg2;
        } else {
            return false;
        }
    }

    public boolean editarUsuarioDiscapacitado(UsuarioDiscapacitado user) {
        boolean flg1 = editarDiscapacidad(user);
        boolean flg2 = editarUDiscapacitado(user);
        return flg1 && flg2;
    }

    public boolean editarUDiscapacitado(UsuarioDiscapacitado newUser) {
        //INSERT INTO Usuarios (UserID,Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion)
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserID", this.usuario.getUserID());
        contentValues.put("Nombre", newUser.getNombre());
        contentValues.put("Apellido", newUser.getApellido());
        contentValues.put("FechaNacimiento", newUser.getFechanacimiento());
        contentValues.put("DiscapacidadID", this.usuario.getDiscapacidadID());
        contentValues.put("Descripcion", newUser.getDescripcionDiscapacidad());
        int result = db.update("Usuarios", contentValues, "UserID = ?", new String[]{String.valueOf(this.usuario.getUserID())});
        if(result > 0){
            asignarUsuario(this.usuario.getUserID());
        }
        return result > 0;
    }


    private boolean editarDiscapacidad(UsuarioDiscapacitado user) {
        //String query="INSERT INTO Discapacidades (Tipo, Grado) VALUES ('"+user.getTipoDiscapacidad()+"', '"+user.getGradoDiscapacidad()+"')";
        ContentValues contentValues = new ContentValues();
        contentValues.put("DiscapacidadID", this.usuario.getDiscapacidadID());
        contentValues.put("Tipo", user.getTipoDiscapacidad());
        contentValues.put("Grado", user.getGradoDiscapacidad());
        int result = db.update("Discapacidades", contentValues, "DiscapacidadID = ?", new String[]{String.valueOf(this.usuario.getDiscapacidadID())});
        return result > 0;
    }

    private boolean registrarUsuarioModelo(UsuarioDiscapacitado user) {
        //INSERT INTO Usuarios (Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion) VALUES \n" +
        //"    ('admin', 'admin1234', 'David', 'Pita', '19-05-2001', 1,'N/A')"
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Username", user.getUsername());
            values.put("Password", user.getPassword());
            values.put("Nombre", user.getNombre());
            values.put("Apellido", user.getApellido());
            values.put("FechaNacimiento", user.getFechanacimiento());
            values.put("DiscapacidadID", user.getIDDiscapacidad());
            values.put("Descripcion", user.getDescripcionDiscapacidad());
            id = db.insert("Usuarios", null, values);
            user.setIDUsuario(id);
        } catch (SQLException ex) {
            return false;
        }
        return id != -1;
    }

    private boolean registrarDiscapacidad(UsuarioDiscapacitado user) {
        //String query="INSERT INTO Discapacidades (Tipo, Grado) VALUES ('"+user.getTipoDiscapacidad()+"', '"+user.getGradoDiscapacidad()+"')";
        String query = "SELECT DiscapacidadID FROM Discapacidades WHERE Tipo=? AND Grado=?";
        Cursor cursor = db.rawQuery(query, new String[]{user.getTipoDiscapacidad(), user.getGradoDiscapacidad()});
        long id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndexOrThrow("DiscapacidadID"));
        } else {
            // Si no existe, insertamos el nuevo nombre
            ContentValues values = new ContentValues();
            values.put("Tipo", user.getTipoDiscapacidad());
            values.put("Grado", user.getGradoDiscapacidad());
            id = db.insert("Discapacidades", null, values);
        }
        cursor.close();
        user.setIDDiscapacidad(id);
        return id != -1;
    }

    public boolean registrarUsuarioNODiscapacitado(Usuario user) {
        System.out.println(user.toString());
        if (verificarUsuarioExistente(user.getUsername())) {
            return registrarUsuarioModelo(user);
        } else {
            return false;
        }
    }

    public boolean editarUsuarioNODiscapacitado(Usuario newUser) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserID", this.usuario.getUserID());
        contentValues.put("Nombre", newUser.getNombre());
        contentValues.put("Apellido", newUser.getApellido());
        contentValues.put("FechaNacimiento", newUser.getFechanacimiento());
        int result = db.update("Usuarios", contentValues, "UserID = ?", new String[]{String.valueOf(this.usuario.getUserID())});
        if(result > 0){
            asignarUsuario(this.usuario.getUserID());
        }
        return result > 0;
    }

    private boolean registrarUsuarioModelo(Usuario user) {
        //INSERT INTO Usuarios (Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion) VALUES \n" +
        //"    ('admin', 'admin1234', 'David', 'Pita', '19-05-2001', 1,'N/A')"
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Username", user.getUsername());
            values.put("Password", user.getPassword());
            values.put("Nombre", user.getNombre());
            values.put("Apellido", user.getApellido());
            values.put("FechaNacimiento", user.getFechanacimiento());
            values.put("DiscapacidadID", 1);
            values.put("Descripcion", "");
            id = db.insert("Usuarios", null, values);
            user.setIDUsuario(id);
            System.out.println(id);
        } catch (SQLException ex) {
            return false;
        }
        return id != -1;
    }

    public void guardarPreferencia() {
        Preferencias preferencias = new Preferencias(context);
        long idUser = usuario.getUserID();
        if (idUser != -1) {
            preferencias.guardarPreferenciaLogin(idUser);
        } else {
            System.out.println("-------\nERROR GUARDAR PREFERENCIA\n-------\n");
        }
    }

    public boolean checkPreferenciaLogin() {
        Preferencias preferencias = new Preferencias(this.context);
        if (preferencias.checkPreferenciaLogin()) {
            if (this.usuario == null) {
                String userString = preferencias.getPreferenciaLogin();
                asignarUsuario(Long.parseLong(userString));
            }
            return true;
        } else {
            return false;
        }
    }

    public String getNombreUsuario() {
        if (usuario == null) {
            checkPreferenciaLogin();
        }
        return usuario.getNombre();
    }


    public List<RecursoLugares> getRecursosLugares() {
        //(idLugar INTEGER PRIMARY KEY,tipo_discapacidad TEXT,nombre TEXT,descripcion TEXT,ubicacion TEXT,contacto TEXT,horarios TEXT,resenas REAL)
        List<RecursoLugares> dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM RecursoLugares", null);
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
                String ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"));
                String contacto = cursor.getString(cursor.getColumnIndexOrThrow("contacto"));
                String horarios = cursor.getString(cursor.getColumnIndexOrThrow("horarios"));
                float resenas = cursor.getFloat(cursor.getColumnIndexOrThrow("resenas"));
                dataList.add(new RecursoLugares(nombre, descripcion, ubicacion, contacto, horarios, resenas));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    public List<RecursoLugares> getRecursosLugares(String[] criterioBusqueda) {
        //(idLugar INTEGER PRIMARY KEY,tipo_discapacidad TEXT,nombre TEXT,descripcion TEXT,ubicacion TEXT,contacto TEXT,horarios TEXT,resenas REAL)
        List<RecursoLugares> dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM RecursoLugares" + getQueryFiltro(criterioBusqueda, "tipo_discapacidad"), null);
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
                String ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"));
                String contacto = cursor.getString(cursor.getColumnIndexOrThrow("contacto"));
                String horarios = cursor.getString(cursor.getColumnIndexOrThrow("horarios"));
                float resenas = cursor.getFloat(cursor.getColumnIndexOrThrow("resenas"));
                dataList.add(new RecursoLugares(nombre, descripcion, ubicacion, contacto, horarios, resenas));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    /*
    private String getQueryFiltroLugares(String[] criterioBusqueda) {
        //WHERE tipo_discapacidad LIKE '%{valor}%'"
        if (!criterioBusqueda[0].isEmpty()) {
            StringBuilder query = new StringBuilder();
            query.append(" WHERE tipo_discapacidad LIKE '%");
            for (int i = 0; i < criterioBusqueda.length; i++) {
                query.append(criterioBusqueda[i]);
                query.append("%'");
                if (i != (criterioBusqueda.length - 1)) {
                    query.append(" AND tipo_discapacidad LIKE '%");
                }
            }
            return query.toString();
        }
        return "";
    }
     */

    private String getQueryFiltro(String[] criterioBusqueda, String columnaBuscar) {
        if (criterioBusqueda == null) {
            return "";
        }
        if (!criterioBusqueda[0].isEmpty()) {
            StringBuilder query = new StringBuilder();
            query.append(" WHERE ");
            query.append(columnaBuscar);
            query.append(" LIKE '%");
            for (int i = 0; i < criterioBusqueda.length; i++) {
                query.append(criterioBusqueda[i]);
                query.append("%'");
                if (i != (criterioBusqueda.length - 1)) {
                    query.append(" AND ");
                    query.append(columnaBuscar);
                    query.append(" LIKE '%");
                }
            }
            return query.toString();
        }
        return "";
    }


    public List<RecursoTemaForo> getRecursosListadoForos() {
        //(idLugar INTEGER PRIMARY KEY,tipo_discapacidad TEXT,nombre TEXT,descripcion TEXT,ubicacion TEXT,contacto TEXT,horarios TEXT,resenas REAL)
        List<RecursoTemaForo> dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT ForoID,Nombre,temaForo,descripcionForo,tipo_discapacidad FROM Foros INNER JOIN Usuarios ON Foros.UserID=Usuarios.UserID", null);
        if (cursor.moveToFirst()) {
            do {
                long idTemaForo = cursor.getLong(cursor.getColumnIndexOrThrow("ForoID"));
                String autor = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                String tema = cursor.getString(cursor.getColumnIndexOrThrow("temaForo"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcionForo"));
                String tipoDiscapacidad = cursor.getString(cursor.getColumnIndexOrThrow("tipo_discapacidad"));
                dataList.add(new RecursoTemaForo(idTemaForo, autor, tema, descripcion, tipoDiscapacidad));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    public List<RecursoTemaForo> getRecursosListadoForos(String[] criterioBusqueda) {
        //(idLugar INTEGER PRIMARY KEY,tipo_discapacidad TEXT,nombre TEXT,descripcion TEXT,ubicacion TEXT,contacto TEXT,horarios TEXT,resenas REAL)
        List<RecursoTemaForo> dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT ForoID,Nombre,temaForo,descripcionForo,tipo_discapacidad FROM Foros INNER JOIN Usuarios ON Foros.UserID=Usuarios.UserID" + getQueryFiltro(criterioBusqueda, "tipo_discapacidad"), null);
        if (cursor.moveToFirst()) {
            do {
                long idTemaForo = cursor.getLong(cursor.getColumnIndexOrThrow("ForoID"));
                String autor = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                String tema = cursor.getString(cursor.getColumnIndexOrThrow("temaForo"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcionForo"));
                String tipoDiscapacidad = cursor.getString(cursor.getColumnIndexOrThrow("tipo_discapacidad"));
                dataList.add(new RecursoTemaForo(idTemaForo, autor, tema, descripcion, tipoDiscapacidad));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }


    public RecursoTemaForo getForoByIDTemaForo(long ForoID) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT ForoID,Nombre,temaForo,descripcionForo,tipo_discapacidad FROM Foros INNER JOIN Usuarios ON Foros.UserID=Usuarios.UserID WHERE Foros.ForoID=?", new String[]{String.valueOf(ForoID)});
        if (cursor.moveToFirst()) {
            do {
                long idTemaForo = cursor.getLong(cursor.getColumnIndexOrThrow("ForoID"));
                String autor = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                String tema = cursor.getString(cursor.getColumnIndexOrThrow("temaForo"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcionForo"));
                String tipoDiscapacidad = cursor.getString(cursor.getColumnIndexOrThrow("tipo_discapacidad"));
                return new RecursoTemaForo(idTemaForo, autor, tema, descripcion, tipoDiscapacidad);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return null;
    }

    public List<RecursoRespuestaForo> getRecursosRespuestasForo(RecursoTemaForo temaForo) {
        List<RecursoRespuestaForo> dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT Nombre, descripcionRespuesta FROM RespuestaForo AS r INNER JOIN Usuarios AS u ON R.UserID=U.UserID WHERE ForoID = ?", new String[]{String.valueOf(temaForo.getIdTemaForo())});
        if (cursor.moveToFirst()) {
            do {
                String autor = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                String descripcionRespuesta = cursor.getString(cursor.getColumnIndexOrThrow("descripcionRespuesta"));
                dataList.add(new RecursoRespuestaForo(autor, descripcionRespuesta));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }

    public boolean publicarForo(String tema, String contenido, String tipo_discapacidad) {
        //INSERT INTO Foros (ForoID,UserID, temaForo, descripcionForo, tipo_discapacidad)
        long id = -1;
        long idUser = usuario.getUserID();
        if (idUser != -1) {
            try {
                ContentValues values = new ContentValues();
                values.put("UserID", idUser);
                values.put("temaForo", tema);
                values.put("descripcionForo", contenido);
                values.put("tipo_discapacidad", tipo_discapacidad);
                id = db.insert("Foros", null, values);
            } catch (SQLException ex) {
                System.out.println("ERROR AL INSERTAR EL DATO: " + idUser);
                return false;
            }
        } else {
            System.out.println("ERROR DE ID USER: " + idUser);
        }
        return id != -1;
    }

    public boolean publicarRespuesta(long idForo, String contenido) {
        // ,ForoID, UserID, descripcionRespuesta
        long id = -1;
        long idUser = usuario.getUserID();
        if (idUser != -1) {
            try {
                ContentValues values = new ContentValues();
                values.put("ForoID", idForo);
                values.put("UserID", idUser);
                values.put("descripcionRespuesta", contenido);
                id = db.insert("RespuestaForo", null, values);
            } catch (SQLException ex) {
                System.out.println("ERROR AL INSERTAR EL DATO: " + idUser);
                return false;
            }
        } else {
            System.out.println("ERROR DE ID USER: " + idUser);
        }
        return id != -1;
    }

    public Object getUsuario() {
        return this.usuario.getUser();
    }

    public void borrarPreferencia() {
        Preferencias preferencias = new Preferencias(context);
        preferencias.borrarPreferenciaLogin();
    }

    public void finalizarSesion() {
        borrarPreferencia();
        this.usuario = null;
        this.db.close();
        this.context = null;
    }
}
