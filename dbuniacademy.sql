-- 3 procedimientos:
-- 1. recuperar los alumnos registrados hoy
	-- E: fecha
    -- S: alumnos
use dbuniacademiy;
DELIMITER $$
DROP PROCEDURE IF EXISTS obtenerAlumnosRegistradosHoy$$
CREATE PROCEDURE obtenerAlumnosRegistradosHoy ()
BEGIN
    SELECT * FROM dbuniacademy.alumnos 
    WHERE DATE(`creado_en`) = CURDATE();
END
$$
CALL obtenerAlumnosRegistradosHoy();
--
-- 2. devuelva edad mínima, máxima y la media
	-- E: nada
    -- S: 3 números: 2 int y un real
DELIMITER $$
DROP PROCEDURE IF EXISTS calcular_max_min_media_edad$$
CREATE PROCEDURE calcular_max_min_media_edad(
	OUT edadmax INT(11),
    OUT edadmin INT(11),
    OUT edadmedia DECIMAL(15, 2)
)
BEGIN
  SELECT 
    MAX(edad),
    MIN(edad),
    AVG(edad)
    FROM alumnos
 
    INTO edadmax, edadmin, edadmedia;
END
$$


DELIMITER $$
DROP PROCEDURE IF EXISTS obtenerAlumnosConNombreComo$$
CREATE PROCEDURE obtenerAlumnosConNombreComo ( IN patron VARCHAR(50))
BEGIN
    SELECT * FROM dbuniacademy.alumnos 
    WHERE nombre like patron;
END
$$

-- CALL obtenerAlumnosRegistradosHoy();
-- CALL calcular_max_min_media_edad(@edadmax, @edadmin, @edadmedia);
-- SELECT @edadmax, @edadmin, @edadmedia;
-- CALL obtenerAlumnosConNombreComo('%F%');


--
-- 3. obtener alumnos dado un patrón de nombre
	-- E: patrón
    -- S: alumnos
DELIMITER $$
DROP PROCEDURE IF EXISTS obtenerAlumnosConNombreComo$$
CREATE PROCEDURE obtenerAlumnosConNombreComo ( IN patron VARCHAR(50))
BEGIN
    SELECT * FROM dbuniacademy.alumnos 
    WHERE nombre like patron;
END
$$

-- CALL obtenerAlumnosRegistradosHoy();
-- CALL calcular_max_min_media_edad(@edadmax, @edadmin, @edadmedia);
-- SELECT @edadmax, @edadmin, @edadmedia;
-- CALL obtenerAlumnosConNombreComo('%F%');
