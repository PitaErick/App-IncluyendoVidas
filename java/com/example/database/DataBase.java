package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private final static String DB_NAME = "salvandovidasproduccion.sqlite";
    private final static int DB_VERSION = 1;
    private final static String TABLE_CREATE_DISCAPACIDAD = "CREATE TABLE IF NOT EXISTS Discapacidades (DiscapacidadID INTEGER PRIMARY KEY,Tipo TEXT,Grado TEXT)";
    private final static String TABLE_CREATE_USER = "CREATE TABLE IF NOT EXISTS Usuarios (UserID INTEGER PRIMARY KEY,Username TEXT,Password TEXT,Nombre TEXT,Apellido TEXT,FechaNacimiento TEXT,DiscapacidadID INTEGER,Descripcion TEXT,FOREIGN KEY (DiscapacidadID) REFERENCES Discapacidades(DiscapacidadID))";
    private final static String TABLE_CREATE_LUGARES = "CREATE TABLE IF NOT EXISTS RecursoLugares (idLugar INTEGER PRIMARY KEY,tipo_discapacidad TEXT,nombre TEXT,descripcion TEXT,ubicacion TEXT,contacto TEXT,horarios TEXT,resenas REAL)";
    private final static String TABLE_CREATE_FOROS = "CREATE TABLE IF NOT EXISTS Foros (ForoID INTEGER PRIMARY KEY,UserID INTEGER,temaForo TEXT,descripcionForo TEXT,tipo_discapacidad TEXT,FOREIGN KEY (UserID) REFERENCES Usuarios(UserID))";
    private final static String TABLE_CREATE_RESPUESTAS_FOROS = "CREATE TABLE IF NOT EXISTS RespuestaForo (RespuestaForoID INTEGER PRIMARY KEY,ForoID INTEGER,UserID INTEGER,descripcionRespuesta TEXT,FOREIGN KEY (ForoID) REFERENCES Foros(ForoID),FOREIGN KEY (UserID) REFERENCES Usuarios(UserID))";

    //prueba
    private final static String DATOS_LUGARES_PRUEBA = "INSERT INTO RecursoLugares (idLugar,tipo_discapacidad, nombre, descripcion, ubicacion, contacto, horarios, resenas) VALUES\n" +
            "(1,'Auditiva', 'Centro de Apoyo Auditivo \"Sonidos de Vida\"', 'Ofrece evaluaciones auditivas, terapias de lenguaje de señas, clases de lectura labial y acceso a dispositivos auditivos.', 'Calle Silencio 101, Zona Norte', '(+593) 12 345 6789', 'Lunes a Viernes de 9:00 a.m. a 6:00 p.m.', 4.5),\n" +
            "(2,'Física', 'Centro de Rehabilitación Física \"Movimiento Libre\"', 'Proporciona terapias físicas y ocupacionales personalizadas para mejorar la movilidad y la independencia.', 'Avenida Libertad 202, Distrito Salud', '(+593) 12 345 6789', 'Lunes a Viernes de 8:00 a.m. a 7:00 p.m.', 4.0),\n" +
            "(3,'Intelectual', 'Fundación \"Capacidades Especiales\"', 'Ofrece programas educativos y de desarrollo personal, talleres de habilidades para la vida diaria y apoyo educativo.', 'Plaza Inclusión 303, Barrio Educativo', '(+593) 12 345 6789', 'Lunes a Sábado de 9:00 a.m. a 5:00 p.m.', 4.5),\n" +
            "(4,'Lenguaje', 'Instituto de Terapias del Lenguaje \"Voces y Palabras\"', 'Especializado en el tratamiento de trastornos del lenguaje, ofrece terapias individuales y grupales para mejorar las habilidades comunicativas.', 'Calle Comunicación 404, Centro Urbano', '(+593) 12 345 6789', 'Lunes a Viernes de 8:00 a.m. a 6:00 p.m.', 3.5),\n" +
            "(5,'Psicosocial', 'Centro de Apoyo Psicosocial \"Mente Sana\"', 'Ofrece apoyo psicológico y terapéutico, con terapias individuales y grupales, programas de bienestar mental y actividades de integración social.', 'Avenida Armonía 505, Barrio Tranquilo', '(+593) 12 345 6789', 'Lunes a Viernes de 9:00 a.m. a 5:00 p.m.', 4.8),\n" +
            "(6,'Auditiva,Física,Intelectual,Psicosocial,Lenguaje,Visual', 'Comunidad de Viviendas Adaptadas \"Hogar Inclusivo\"', 'Hogar Inclusivo es un complejo residencial diseñado específicamente para personas con discapacidad. Las viviendas cuentan con adaptaciones de accesibilidad y servicios comunitarios que facilitan una vida independiente, con personal de apoyo disponible las 24 horas.', 'Calle Tranquilidad 202, Suburbio Verde', '(+593) 12 345 6789', 'Atención al cliente 24/7.', 4.8),\n" +
            "(7,'Auditiva,Física,Intelectual,Psicosocial,Lenguaje,Visual', 'Fundación \"Puentes de Esperanza\"', 'Puentes de Esperanza trabaja para conectar a personas con discapacidad con oportunidades laborales y educativas. La fundación ofrece programas de capacitación, asesoramiento profesional y una bolsa de trabajo inclusiva, ayudando a crear un entorno laboral accesible y justo.', 'Calle Progreso 101, Zona Industrial', '(+593) 12 345 6789', 'Lunes a Viernes de 8:00 a.m. a 5:00 p.m.', 4.9),\n" +
            "(8,'Física,Intelectual,Lenguaje', 'Club Deportivo Inclusivo \"Juntos en Movimiento\"', 'Juntos en Movimiento es un club deportivo que ofrece actividades y programas de ejercicio adaptados para personas con discapacidad. Con entrenadores capacitados y equipos especializados, el club promueve un estilo de vida activo y saludable.', 'Avenida del Deporte 303, Parque Central', '(+593) 12 345 6789', 'Lunes a Domingo de 6:00 a.m. a 9:00 p.m.', 3.8),\n" +
            "(9,'Visual', 'Centro de Recursos para la Discapacidad Visual \"Luz y Vida\"', 'Proporciona recursos y tecnologías asistivas, entrenamiento en el uso de dispositivos de asistencia, orientación y movilidad, y talleres de habilidades para la vida diaria.', 'Calle Visión 606, Distrito Tecnológico', '(+593) 12 345 6789', 'Lunes a Sábado de 10:00 a.m. a 6:00 p.m.', 3.8)";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DISCAPACIDAD);
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(TABLE_CREATE_LUGARES);
        db.execSQL("INSERT INTO Discapacidades (DiscapacidadID,Tipo, Grado) VALUES (1,'N/A', 'N/A')");
        db.execSQL(DATOS_LUGARES_PRUEBA);
        db.execSQL("INSERT INTO Usuarios (UserID,Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion) VALUES \n" +
                "    (1,'admin', 'admin1234', 'AdminPita', 'Pita', '19-05-2001', 1,'N/A')");
        db.execSQL("INSERT INTO Usuarios (UserID,Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion) VALUES \n" +
                "    (2,'pedro123', 'pedro123', 'Juan', 'Ponce', '18-05-2001', 1,'N/A')");
        db.execSQL("INSERT INTO Usuarios (UserID,Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion) VALUES \n" +
                "    (3,'carlos', 'carlos123', 'Carlos', 'Ortiz', '18-05-2001', 1,'N/A')");
        db.execSQL("INSERT INTO Usuarios (UserID,Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion) VALUES \n" +
                "    (4,'Pepe', 'pepecarlos', 'Pepito', 'Quimiz', '18-05-2001', 1,'N/A')");
        db.execSQL(TABLE_CREATE_FOROS);
        db.execSQL(TABLE_CREATE_RESPUESTAS_FOROS);
        db.execSQL("INSERT INTO Foros (ForoID,UserID, temaForo, descripcionForo, tipo_discapacidad) VALUES\n" +
                "(1,1, 'Cómo mejorar la accesibilidad en el transporte público', '¿Alguien tiene experiencias o recomendaciones sobre cómo hacer que el transporte público sea más accesible para personas con discapacidades auditivas y visuales?', 'Auditiva,Visual'),\n" +
                "(2,2, 'Tecnologías que ayudan a personas con discapacidad psicosocial', 'Estoy buscando recomendaciones sobre apps o dispositivos que puedan ayudar a manejar mejor la vida diaria para personas con discapacidades psicosociales.', 'Psicosocial'),\n" +
                "(3,1, 'Experiencias en educación inclusiva', 'Me gustaría conocer experiencias sobre cómo se está implementando la educación inclusiva para estudiantes con discapacidades físicas y auditivas.', 'Física,Auditiva'),\n" +
                "(4,4, 'Deportes adaptados para personas con discapacidades', 'Quisiera saber más sobre qué deportes adaptados están disponibles para personas con discapacidades físicas y cómo se pueden practicar.', 'Física')");
        db.execSQL("INSERT INTO RespuestaForo (RespuestaForoID,ForoID, UserID, descripcionRespuesta) VALUES\n" +
                "(1,1, 3, 'En mi ciudad, implementaron un sistema de audio y señales visuales en los autobuses. Ha sido muy útil para personas con discapacidades auditivas y visuales.'),\n" +
                "(2,1, 2, 'Recomiendo usar apps de navegación que tienen soporte para personas con discapacidades visuales, como Lazarillo.'),\n" +
                "(3,2, 3, 'He encontrado que la app \"Calm\" es excelente para ayudar a manejar la ansiedad y el estrés, y puede ser útil para personas con discapacidades psicosociales.'),\n" +
                "(4,3, 2, 'En mi escuela, hemos creado materiales en braille y audiolibros para estudiantes con discapacidades auditivas y visuales. Ha sido una gran ayuda.'),\n" +
                "(5,2, 3, 'El baloncesto en silla de ruedas es muy popular y accesible. Puedes encontrar clubs locales que lo practican.'),\n" +
                "(6,4, 1, 'La natación adaptada es otra excelente opción. Es muy beneficiosa y hay muchas instalaciones que la ofrecen.')");
        /*
        Insertamos Prueba para validar el inicio de sesion
        db.execSQL("INSERT INTO Discapacidades (DiscapacidadID,Tipo, Grado) VALUES (1,'N/A', 'N/A')");

        db.execSQL("INSERT INTO Usuarios (Username, Password, Nombre, Apellido, FechaNacimiento, DiscapacidadID,Descripcion) VALUES \n" +
                "    ('admin', 'admin1234', 'David', 'Pita', '19-05-2001', 1,'N/A')");

        db.execSQL("INSERT INTO Discapacidades (Tipo, Descripcion, Grado) VALUES ('Auditiva', 'Discapacidad que afecta la capacidad auditiva.', 'Moderado')");
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

/*
CREATE TABLE Usuarios (
    UserID INTEGER PRIMARY KEY,
    Username TEXT,
    Password TEXT,
    Nombre TEXT,
    Apellido TEXT,
    FechaNacimiento TEXT,
    Discapacidad ID NUMBER,
)


CREATE TABLE Discapacidades (
    DiscapacidadID INTEGER PRIMARY KEY,
    TIPO TEXT,
    Descripción TEXT,
    Grados TEXT,
)

CREATE TABLE Foros (
    ForoID INTEGER PRIMARY KEY,
    UserID INTEGER,
    temaForo TEXT,
    descripcionForo TEXT,
    tipo_discapacidad TEXT,
    FOREIGN KEY (UserID) REFERENCES Usuarios(UserID)
);CREATE TABLE RespuestaForo (
    RespuestaForoID INTEGER PRIMARY KEY,
    ForoID INTEGER,
    UserID INTEGER,
    descripcionRespuesta TEXT,
    FOREIGN KEY (ForoID) REFERENCES Foros(ForoID),
    FOREIGN KEY (UserID) REFERENCES Usuarios(UserID)
)
INSERT INTO Foros (UserID, temaForo, descripcionForo, tipo_discapacidad) VALUES
(1, 'Cómo mejorar la accesibilidad en el transporte público', '¿Alguien tiene experiencias o recomendaciones sobre cómo hacer que el transporte público sea más accesible para personas con discapacidades auditivas y visuales?', 'Auditiva,Visual'),
(2, 'Tecnologías que ayudan a personas con discapacidad psicosocial', 'Estoy buscando recomendaciones sobre apps o dispositivos que puedan ayudar a manejar mejor la vida diaria para personas con discapacidades psicosociales.', 'Psicosocial'),
(3, 'Experiencias en educación inclusiva', 'Me gustaría conocer experiencias sobre cómo se está implementando la educación inclusiva para estudiantes con discapacidades físicas y auditivas.', 'Física,Auditiva'),
(4, 'Deportes adaptados para personas con discapacidades', 'Quisiera saber más sobre qué deportes adaptados están disponibles para personas con discapacidades físicas y cómo se pueden practicar.', 'Física')
INSERT INTO RespuestaForo (ForoID, UserID, descripcionRespuesta) VALUES
(1, 1, 'En mi ciudad, implementaron un sistema de audio y señales visuales en los autobuses. Ha sido muy útil para personas con discapacidades auditivas y visuales.'),
(1, 2, 'Recomiendo usar apps de navegación que tienen soporte para personas con discapacidades visuales, como Lazarillo.'),
(2, 3, 'He encontrado que la app "Calm" es excelente para ayudar a manejar la ansiedad y el estrés, y puede ser útil para personas con discapacidades psicosociales.'),
(3, 2, 'En mi escuela, hemos creado materiales en braille y audiolibros para estudiantes con discapacidades auditivas y visuales. Ha sido una gran ayuda.'),
(4, 3, 'El baloncesto en silla de ruedas es muy popular y accesible. Puedes encontrar clubs locales que lo practican.'),
(4, 1, 'La natación adaptada es otra excelente opción. Es muy beneficiosa y hay muchas instalaciones que la ofrecen.')




CREATE TABLE lugares_inclusion (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo_discapacidad TEXT NOT NULL,
    nombre TEXT NOT NULL,
    descripcion TEXT NOT NULL,
    ubicacion TEXT NOT NULL,
    contacto TEXT NOT NULL,
    horarios TEXT NOT NULL,
    resenas TEXT NOT NULL
)
INSERT INTO Lugares (idLugar,tipo_discapacidad, nombre, descripcion, ubicacion, contacto, horarios, resenas) VALUES
(1,'Auditiva', 'Centro de Apoyo Auditivo "Sonidos de Vida"', 'Ofrece evaluaciones auditivas, terapias de lenguaje de señas, clases de lectura labial y acceso a dispositivos auditivos.', 'Calle Silencio 101, Zona Norte', '(555) 123-4567', 'Lunes a Viernes de 9:00 a.m. a 6:00 p.m.', 4.5'),
(2,'Física', 'Centro de Rehabilitación Física "Movimiento Libre"', 'Proporciona terapias físicas y ocupacionales personalizadas para mejorar la movilidad y la independencia.', 'Avenida Libertad 202, Distrito Salud', '(555) 234-5678', 'Lunes a Viernes de 8:00 a.m. a 7:00 p.m.', 4.0),
(3,'Intelectual', 'Fundación "Capacidades Especiales"', 'Ofrece programas educativos y de desarrollo personal, talleres de habilidades para la vida diaria y apoyo educativo.', 'Plaza Inclusión 303, Barrio Educativo', '(555) 345-6789', 'Lunes a Sábado de 9:00 a.m. a 5:00 p.m.', 4.5),
(4,'Lenguaje', 'Instituto de Terapias del Lenguaje "Voces y Palabras"', 'Especializado en el tratamiento de trastornos del lenguaje, ofrece terapias individuales y grupales para mejorar las habilidades comunicativas.', 'Calle Comunicación 404, Centro Urbano', '(555) 456-7890', 'Lunes a Viernes de 8:00 a.m. a 6:00 p.m.', 3.5),
(5,'Psicosocial', 'Centro de Apoyo Psicosocial "Mente Sana"', 'Ofrece apoyo psicológico y terapéutico, con terapias individuales y grupales, programas de bienestar mental y actividades de integración social.', 'Avenida Armonía 505, Barrio Tranquilo', '(555) 567-8901', 'Lunes a Viernes de 9:00 a.m. a 5:00 p.m.', 4.8),
(6,'Visual', 'Centro de Recursos para la Discapacidad Visual "Luz y Vida"', 'Proporciona recursos y tecnologías asistivas, entrenamiento en el uso de dispositivos de asistencia, orientación y movilidad, y talleres de habilidades para la vida diaria.', 'Calle Visión 606, Distrito Tecnológico', '(555) 678-9012', 'Lunes a Sábado de 10:00 a.m. a 6:00 p.m.', 3.8);
*/