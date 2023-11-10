CREATE DATABASE IF NOT EXISTS videojuegos;
USE videojuegos;
CREATE TABLE IF NOT EXISTS videojuegos(
	nombre VARCHAR(50),
    categoria VARCHAR(20),
    plataforma VARCHAR(15)
);

-- CATEGORIAS DE VIDEOJUEGOS: Arcade, Accion, Disparos, Peleas, RPG, Plataformas, Puzzles, Musical, Carreras, Deportes
-- PLATAFORMAS DE VIDEOJUEGOS: SOLO Nintendo Switch, PS4 y Xbox One.
INSERT INTO videojuegos VALUES ("Hollow Knight","Plataformas","Nintendo Switch");
INSERT INTO videojuegos VALUES ("Final Fantasy XV","RPG","PS4");
INSERT INTO videojuegos VALUES ("Forza Horizon 5","Carreras","Xbox One");