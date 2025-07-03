-- SI YA EXISTEN LAS TABLAS Y QUIERES HACER UN REINICIO


DO $$
DECLARE
r RECORD;
    tablas_existentes INTEGER;
BEGIN
    -- Verificar si existen tablas
SELECT COUNT(*) INTO tablas_existentes
FROM pg_tables
WHERE schemaname = 'public';

IF tablas_existentes > 0 THEN
        -- Drop all tables
        FOR r IN (
            SELECT tablename FROM pg_tables WHERE schemaname = 'public'
        ) LOOP
            EXECUTE format('DROP TABLE IF EXISTS public.%I CASCADE;', r.tablename);
END LOOP;

        -- Drop all sequences
FOR r IN (
            SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema = 'public'
        ) LOOP
            EXECUTE format('DROP SEQUENCE IF EXISTS public.%I CASCADE;', r.sequence_name);
END LOOP;
END IF;
END
$$;



-- Tabla: ROL
CREATE TABLE roles (
    id_rol SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(100)
);

-- Tabla: PERFIL
CREATE TABLE perfiles (
  id_perfil Serial PRIMARY KEY,
  nom_perfil VARCHAR(50) NOT NULL,
  des_perfil VARCHAR(100),
  per_estado VARCHAR(10) NOT NULL CHECK (per_estado IN ('INACTIVO', 'ACTIVO')),
  fecha_creacion TIMESTAMP, 
  usuario_creador VARCHAR(50), 
  fecha_actualizacion TIMESTAMP, 
  usuario_actualizador VARCHAR(50)
);

-- Tabla: PAIS
CREATE TABLE paises(
	id_pais Serial PRIMARY KEY,
	nom_pais VARCHAR(20) UNIQUE NOT NULL
);

-- Tabla: DEPARTAMENTO
CREATE TABLE departamentos(
	id_departamento Serial PRIMARY KEY,
	nom_departamento VARCHAR(20) UNIQUE NOT NULL,
    id_pais INTEGER,
    FOREIGN KEY (id_pais) REFERENCES paises(id_pais)
);

-- Tabla: CIUDAD
CREATE TABLE ciudades(
	id_ciudad Serial PRIMARY KEY,
	nom_ciudad VARCHAR(40) UNIQUE NOT NULL,
    id_departamento INTEGER,
    FOREIGN KEY (id_departamento) REFERENCES departamentos(id_departamento)
);

-- Tabla: USUARIO
CREATE TABLE usuarios (
    id_usuario           SERIAL PRIMARY KEY,
    nro_documento        VARCHAR(10) UNIQUE NOT NULL,
    pri_nombre           VARCHAR(50) NOT NULL,
    seg_nombre           VARCHAR(50),
    pri_apellido         VARCHAR(50) NOT NULL,
    seg_apellido         VARCHAR(50),
    tipo_doc             VARCHAR(10) NOT NULL CHECK (tipo_doc IN ('CI', 'DNI', 'PASAPORTE', 'OTRO')),
    fec_nacimiento       DATE NOT NULL,
    mail                 VARCHAR(100) UNIQUE NOT NULL,
    contrasena_hash      VARCHAR(255) NOT NULL,
    usu_estado           VARCHAR(12) NOT NULL CHECK (usu_estado IN ('SIN VALIDAR', 'ACTIVO', 'INACTIVO')),
    nro_socio            INTEGER GENERATED ALWAYS AS IDENTITY UNIQUE,
    calle                VARCHAR(100) NOT NULL,
    nro_puerta           VARCHAR(10) NOT NULL,
    nro_apartamento      VARCHAR(10),
    bis                  BOOLEAN DEFAULT FALSE,
    cod_postal           VARCHAR(10),
    id_perfil            INTEGER NOT NULL,
    id_ciudad            INTEGER NOT NULL,
    fecha_creacion       TIMESTAMP,
    usuario_creador      VARCHAR(50),
    fecha_actualizacion  TIMESTAMP,
    usuario_actualizador VARCHAR(50),
    id_rol               INTEGER NOT NULL,
    FOREIGN KEY (id_perfil) REFERENCES perfiles(id_perfil),
    FOREIGN KEY (id_ciudad) REFERENCES ciudades(id_ciudad),
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Tabla: FUNCIONALIDAD
CREATE TABLE funcionalidades (
  id_funcionalidad Serial PRIMARY KEY,
  nom_funcionalidad VARCHAR(10) UNIQUE NOT NULL,
  des_funcionalidad VARCHAR(50),
  fun_estado VARCHAR(10) NOT NULL CHECK (fun_estado IN ('INACTIVO', 'ACTIVO'))
);

-- Tabla: ACCESO_FUNCIONALIDAD
CREATE TABLE acceso_funcionalidades (
  id_funcionalidad INTEGER NOT NULL,
  id_perfil INTEGER NOT NULL,
  PRIMARY KEY (id_funcionalidad, id_perfil),
  FOREIGN KEY (id_funcionalidad) REFERENCES funcionalidades(id_funcionalidad),
  FOREIGN KEY (id_perfil) REFERENCES perfiles(id_perfil)
);

-- Tabla: AUD_USUARIO
CREATE TABLE aud_usuarios (
  id_aud_usuario SERIAL PRIMARY KEY,
  fecha_hora TIMESTAMP NOT NULL,
  operacion VARCHAR(20) NOT NULL,
  terminal VARCHAR(20) NOT NULL,
  id_usuario INTEGER NOT NULL,
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Tabla: Telefono
CREATE TABLE telefonos(
    id_usuario INTEGER NOT NULL,
    nro_telefono VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    PRIMARY KEY (id_usuario, nro_telefono)
);

-- Tabla: AUX_ADMINISTRATIVO
CREATE TABLE aux_administrativos (
    id_aux_administrativo SERIAL PRIMARY KEY,
    id_usuario            INTEGER NOT NULL UNIQUE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Tabla: SUBCOMISION
CREATE TABLE subcomisiones (
    id_subcomision  SERIAL PRIMARY KEY,
    nom_subcomision VARCHAR(20) NOT NULL UNIQUE,
    des_subcomision VARCHAR(50)
);

-- Tabla: SOCIO
CREATE TABLE socios (
    id_socio              SERIAL PRIMARY KEY,
    cat_socio             VARCHAR(50) NOT NULL UNIQUE,
    dif_auditiva          BOOLEAN NOT NULL,
    len_sena              BOOLEAN NOT NULL,
    id_usuario            INTEGER,
    id_subcomision        INTEGER,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_subcomision) REFERENCES subcomisiones(id_subcomision)
);

-- Tabla: ESPACIO
CREATE TABLE espacios (
    id_espacio            SERIAL PRIMARY KEY,
    nom_espacio           VARCHAR(50) UNIQUE NOT NULL,
    capacidad             INT NOT NULL,
    precio_socio          NUMERIC(10,2),
    precio_no_socio       NUMERIC(10,2),
    vig_precio            DATE,
    esp_observacion       VARCHAR(150),
    esp_estado            VARCHAR(10) NOT NULL CHECK (esp_estado IN ('INACTIVO', 'ACTIVO'))
);

-- Tabla: TIPO_ACTIVIDAD
CREATE TABLE tipo_actividades (
    id_tipo_actividad     SERIAL PRIMARY KEY,
    tipo_act_nombre       VARCHAR(20) UNIQUE NOT NULL,
    tipo_act_descripcion  VARCHAR(100),
    fec_baja              DATE,
    raz_baja              VARCHAR(100), 
    comentario            VARCHAR(200),
    tipo_act_estado       VARCHAR(10) NOT NULL CHECK (tipo_act_estado IN ('INACTIVO', 'ACTIVO'))
);

-- Tabla: ACTIVIDAD
CREATE TABLE actividades (
    id_actividad          SERIAL PRIMARY KEY,
    actividad_nom         VARCHAR(100) UNIQUE NOT NULL,
    acrividad_descripcion VARCHAR(255),
    objetivo              VARCHAR(255),
    fec_actividad         DATE NOT NULL,
    hora_activdad         TIME NOT NULL,
    duracion              TIME,
    inscripcion           BOOLEAN,
    costo                 INTEGER,
    fec_inscripcion       DATE,
    fec_apertura_inscripcion DATE NOT NULL,
    fec_cierre_inscripcion   DATE NOT NULL,
    tipo_pago             VARCHAR(10) NOT NULL CHECK (tipo_pago IN ('EFECTIVO','TRANSFERENCIA','DEBITO','CREDITO')),
    act_observaciones     VARCHAR(100),
    act_estado            VARCHAR(10) NOT NULL CHECK (act_estado IN ('INACTIVO', 'ACTIVO')),
    id_tipo_actividad     INTEGER,
    id_aux_administrativo INTEGER,
    id_espacio            INTEGER,
    FOREIGN KEY (id_tipo_actividad) REFERENCES tipo_actividades(id_tipo_actividad),
    FOREIGN KEY (id_aux_administrativo) REFERENCES aux_administrativos(id_aux_administrativo),
    FOREIGN KEY (id_espacio) REFERENCES espacios(id_espacio),
    CHECK (fec_apertura_inscripcion <= fec_cierre_inscripcion)
);

-- Tabla: INSCRIPCION_ACTIVIDAD
CREATE TABLE inscripcion_actividades (
    id_usuario            INTEGER NOT NULL,
    id_actividad          INTEGER NOT NULL,
    fec_inscripcion       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ins_cancelada         BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id_usuario, id_actividad),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_actividad) REFERENCES actividades(id_actividad)
);

-- Tabla: RESERVA_ESPACIO
CREATE TABLE reserva_espacios (
id_reserva             SERIAL PRIMARY KEY,
fec_reserva_actividad  DATE,
hora_reserva_actividad TIME,
duracion               TIME,
cantidad_personas      INTEGER,
fec_vto_sena           DATE GENERATED ALWAYS AS (fec_reserva_actividad + INTERVAL '7 days') STORED,
    cst_actividad          NUMERIC(10,2),
    fec_pago_senia         DATE,
    imp_pagado             NUMERIC(10,2),
    imp_a_pagar            NUMERIC(10,2),
    sdo_pendiente          NUMERIC(10,2) GENERATED ALWAYS AS (cst_actividad - imp_pagado) STORED,
    fec_conf_reserva       DATE,
    hora_conf_reserva      TIME,
    res_cancelada          BOOLEAN DEFAULT FALSE,
    id_usuario             INT NOT NULL,
    id_espacio             INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_espacio) REFERENCES espacios(id_espacio)
);


-- Tabla: PAGO
CREATE TABLE pago (
    id_pago               SERIAL PRIMARY KEY,
    fecha_cobro           DATE NOT NULL,
    monto                 NUMERIC(10,2) NOT NULL,
    forma_cobro           VARCHAR(10) NOT NULL CHECK (forma_cobro IN ('EFECTIVO','TRANSFERENCIA','DEBITO','CREDITO')),
    id_actividad          INT,
    id_reserva            INT,
    id_usuario            INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_actividad) REFERENCES actividades(id_actividad),
    FOREIGN KEY (id_reserva) REFERENCES reserva_espacios(id_reserva)
);

-- Tabla: REGISTRO_ACTIVIDAD
CREATE TABLE registro_actividad (
   id_registro SERIAL PRIMARY KEY,
   usuario VARCHAR(100),
   metodo VARCHAR(10),
   endpoint VARCHAR(255),
   fecha_hora TIMESTAMP
);

-- INSERTS ZONAS GEOGRAFICAS

INSERT INTO paises (nom_pais) VALUES
('Uruguay'),
('Argentina'),
('Brasil');

INSERT INTO departamentos (nom_departamento, id_pais) VALUES
('Montevideo', 1),
('Canelones', 1),
('Maldonado', 1),
('Buenos Aires', 2),
('Córdoba', 2),
('Santa Fe', 2),
('São Paulo', 3),
('Rio de Janeiro', 3),
('Bahia', 3);

INSERT INTO ciudades (nom_ciudad, id_departamento) VALUES
('Montevideo', 1),
('Las Piedras', 2),
('Punta del Este', 3),
('La Plata', 4),
('Córdoba Capital', 5),
('Rosario', 6),
('São Paulo', 7),
('Rio de Janeiro', 8),
('Salvador', 9);

-- INSERTS ROLES

INSERT INTO roles (nombre, descripcion) VALUES
('ADMIN', 'Usuario con permisos totales'),
('AUXADMIN', 'Personal administrativo que gestiona tareas'),
('SOCIO', 'Miembro activo del sistema con acceso limitado'),
('NO_SOCIO', 'Usuario externo sin afiliación activa'),
('SIN_VERIFICAR', 'Usuario externo sin afiliación activa');

--INSERT PERFIL

insert into perfiles (
    nom_perfil, des_perfil, per_estado
)
values (
    'PRUEBA', 'Prueba', 'ACTIVO'
);


-- INSERT USUARIO ADMIN

insert into usuarios (
    nro_documento, pri_nombre, seg_nombre, pri_apellido, seg_apellido, tipo_doc,
    fec_nacimiento, mail, contrasena_hash, usu_estado, calle, nro_puerta, nro_apartamento,
    bis, cod_postal, id_perfil, id_ciudad, id_rol
)
values (
    '12345678',                 -- nro_documento
    'Admin',                   -- pri_nombre
    'Admin',                   -- seg_nombre
    'Token',                   -- pri_apellido
    'Token',                   -- seg_apellido
    'CI',                      -- tipo_doc
    '1990-01-01',              -- fec_nacimiento
    'admin@token.com.uy',      -- mail
    '$2a$10$6Tb2wwb3DgobglUPs2.wPelOOIeWj6hrAJuIUKvC2TqjYxMNPRLHW', -- contrasena_hash
    'ACTIVO',                  -- usu_estado
    'Calle Falsa',             -- calle
    '1234',                    -- nro_puerta
    3,                         -- nro_apartamento
    false,                     -- bis
    '11200',                   -- cod_postal
    1,                         -- id_perfil
    1,                         -- id_ciudad
    1                          -- id_rol
);













usuario correo admin@token.com.uy

pass : admin123
