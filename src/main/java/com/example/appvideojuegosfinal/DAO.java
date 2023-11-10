package com.example.appvideojuegosfinal;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static com.example.appvideojuegosfinal.util.R.getProperties;

public class DAO {
    private Connection conexion;
    ObservableList<Videojuego> listavideojuegos;

    public void conectar() throws ClassNotFoundException, SQLException, IOException {
//        =========== CONEXION POR ARCHIVO "database.properties" ==========
        Properties configuration = new Properties();
        configuration.load(getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

//        =========== CONEXION POR DATOS ESCRITOS A MANO ==========
//        String host = "127.0.0.1";
//        int port = 3306;
//        String name = "videojuegos";
//        String username = "root";
//        String password = "root";

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC", username, password);

        System.out.println("Conectado a la base de datos correctamente.");
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public ObservableList<Videojuego> obtenerVideojuegos() throws SQLException{
        String sql = "SELECT * FROM videojuegos";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            String nombre = resultado.getString("nombre");
            String categoria = resultado.getString("categoria");
            String plataforma = resultado.getString("plataforma");
            Videojuego r = new Videojuego(nombre,categoria,plataforma);
            listavideojuegos.add(r);
        }

        return listavideojuegos;
    }

    /**
     * Obtener videojuegos filtrados por X categoria
     * @param cat
     * @return
     * @throws SQLException
     */
    public ObservableList<Videojuego> obtenerVideojuegosCategoria(String cat) throws SQLException {
        String sql = "SELECT * FROM videojuegos WHERE categoria = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1,cat);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            String nombre = resultado.getString("nombre");
            String categoria = resultado.getString("categoria");
            String plataforma = resultado.getString("plataforma");
            Videojuego r = new Videojuego(nombre,categoria,plataforma);
            listavideojuegos.add(r);
        }

        return listavideojuegos;
    }

    /**
     * Obtener videojuegos filtrados por X plataforma
     * @param pla
     * @return
     * @throws SQLException
     */
    public ObservableList<Videojuego> obtenerVideojuegosPlataforma(String pla) throws SQLException {
        String sql = "SELECT * FROM videojuegos WHERE plataforma = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1,pla);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            String nombre = resultado.getString("nombre");
            String categoria = resultado.getString("categoria");
            String plataforma = resultado.getString("plataforma");
            Videojuego r = new Videojuego(nombre,categoria,plataforma);
            listavideojuegos.add(r);
        }

        return listavideojuegos;
    }

    public void AddVideojuego(String nom, String cat, String pla){
        try{
            String sql = "INSERT INTO videojuegos (nombre, categoria, plataforma) VALUES (?, ?, ?)";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nom);
            sentencia.setString(2, cat);
            sentencia.setString(3, pla);
            sentencia.executeUpdate();
            conexion.close();
            System.out.println("Videojuego añadido correctamente.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void DelVideojuego(Videojuego v){
        try{
            String nom = v.getNombre();
            String cat = v.getCategoria();
            String pla = v.getPlataforma();

            String sql = "DELETE FROM videojuegos WHERE nombre = ? AND categoria = ? AND plataforma = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nom);
            sentencia.setString(2, cat);
            sentencia.setString(3, pla);
            sentencia.executeUpdate();
            conexion.close();
            System.out.println("Videojuego eliminado correctamente.");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void ModVideojuego(Videojuego v, String nom, String cat, String pla){
        try{
            String sql = "UPDATE videojuegos SET nombre = ?, categoria = ?, plataforma = ? WHERE nombre = ? AND categoria = ? AND plataforma = ?";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nom);
            sentencia.setString(2, cat);
            sentencia.setString(3, pla);
            sentencia.setString(4, v.getNombre());
            sentencia.setString(5, v.getCategoria());
            sentencia.setString(6, v.getPlataforma());
            sentencia.executeUpdate();
            conexion.close();
            System.out.println("Videojuego modificado correctamente.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
