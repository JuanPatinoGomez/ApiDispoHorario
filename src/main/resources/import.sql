INSERT INTO sedes (municipio, direccion) VALUES ('bucaramanga', 'calle estudiantes');
INSERT INTO sedes (municipio, direccion) VALUES ('barrancabermeja', 'calle estudiantes 2');
INSERT INTO sedes (municipio, direccion) VALUES ('piedecuesta', 'calle estudiantes 3');


INSERT INTO edificios (nombre, sede_id) VALUES ('a', 1);
INSERT INTO edificios (nombre, sede_id) VALUES ('b', 1);
INSERT INTO edificios (nombre, sede_id) VALUES ('c', 1);

INSERT INTO edificios (nombre, sede_id) VALUES ('a', 2);
INSERT INTO edificios (nombre, sede_id) VALUES ('b', 2);
INSERT INTO edificios (nombre, sede_id) VALUES ('c', 2);

INSERT INTO edificios (nombre, sede_id) VALUES ('a', 3);
INSERT INTO edificios (nombre, sede_id) VALUES ('b', 3);
INSERT INTO edificios (nombre, sede_id) VALUES ('c', 3);


INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 1);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 1);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 1);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 2);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 2);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 2);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 3);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 3);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 3);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 4);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 4);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 4);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 5);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 5);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 5);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 6);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 6);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 6);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 7);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 7);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 7);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 8);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 8);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 8);

INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 101, 9);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('salon informatica', 201, 9);
INSERT INTO salones (tipo, numero, edificio_id) VALUES ('aula', 301, 9);


INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('matematicas', 'lunes', '6:00', '7:29', 1);
INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('ingles', 'lunes', '7:30', '8:59', 1);
INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('español', 'lunes', '9:00', '10:30', 1);

INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('matematicas', 'lunes', '6:00', '7:29', 2);
INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('ingles', 'lunes', '7:30', '8:59', 2);
INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('español', 'lunes', '9:00', '10:30', 2);

INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('matematicas', 'lunes', '6:00', '7:29', 3);
INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('ingles', 'lunes', '7:30', '8:59', 3);
INSERT INTO clases (nombre_asignatura, dia, hora_inicio, hora_finalizacion, salon_id) VALUES ('español', 'lunes', '9:00', '10:30', 3);