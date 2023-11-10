package com.example.appvideojuegosfinal;

import com.example.appvideojuegosfinal.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private Label txtHeader;
    @FXML
    private TableView<Videojuego> tablaVideojuegos;
    @FXML
    private TableColumn<?, ?> colCategoria;
    @FXML
    private TableColumn<?, ?> colPlataforma;
    @FXML
    private TableColumn<?, ?> colVideojuego;
    @FXML
    private RadioButton rbCategoria;
    @FXML
    private RadioButton rbPlataforma;
    @FXML
    private RadioButton rbSinFiltros;
    @FXML
    private ToggleGroup filtro;
    @FXML
    private ComboBox<String> cbCategoriaF;
    @FXML
    private ComboBox<String> cbPlataformaF;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnModoAddVideojuego;
    @FXML
    private Button btnModoEliminarVideojuego;
    @FXML
    private Button btnModoModificarVideojuego;
    @FXML
    private Label labelNombre;
    @FXML
    private TextField txtNombre;
    @FXML
    private Label labelCategoria;
    @FXML
    private ComboBox<String> cbCategoria;
    @FXML
    private Label labelPlataforma;
    @FXML
    private ComboBox<String> cbPlataforma;
    @FXML
    private Button btnAddVideojuego;
    @FXML
    private Label labelEliminarVideojuego;
    @FXML
    private Button btnDelVideojuego;
    @FXML
    private Label labelModificarVideojuego;
    @FXML
    private Button btnModoModificarVideojuego2;
    @FXML
    private Button btnMod;
    @FXML
    private Button btnCancelar;

    private String[] categorias, plataformas;
    private Videojuego videojuegoseleccionado;
    private DAO d = new DAO();

    /**
     * Metodo que llama al modo "AddVideojuego" al pulsar el boton "Añadir" de la App
     * @param event
     */
    @FXML
    void AddVideojuego(ActionEvent event) {
        ModoAddVideojuego(true);
    }

    /**
     * Metodo que llama al modo "DeleteVideojuego" al pulsar el boton "Eliminar" de la App
     * @param event
     */
    @FXML
    void EliminarVideojuego(ActionEvent event) {
        ModoDeleteVideojuego(true);
    }

    /**
     * Metodo que llama al modo "ModificarVideojuego" al pulsar el boton "Modificar" de la App
     * @param event
     */
    @FXML
    void ModificarVideojuego(ActionEvent event) {
        ModoModificarVideojuego(true);
    }

    /**
     * Metodo que llama al modo "ModificarVideojuego2"  al pulsar el boton "Modificar Videojuego"
     * en el modo "ModificarVideojuego"
     * @param event
     */
    @FXML
    void ModificarVideojuego2(ActionEvent event) {
        ModoModificarVideojuego2(true);
    }

    /**
     * Metodo para mostrar nuestros videojuegos en la lista filtrados por
     * categoria, plataforma o sin filtros segun las opciones que escojamos
     *
     * @param event
     */
    @FXML
    void Filtrar(ActionEvent event) {
        try{
            // SI HEMOS PULSADO EL RADIOBUTTON DE CATEGORIA
            if(rbCategoria.isSelected()){
                // SI NO HEMOS ESCOGIDO NINGUNA CATEGORIA
                if(cbCategoriaF.getValue() == null){
                    AlertUtil.mostrarError("POR FAVOR, SELECCIONA UNA CATEGORIA.");
                }
                // EN CASO CONTRARIO, GUARDAREMOS EN UNA VARIABLE LA CATEGORIA ESCOGIDA, VACIAREMOS
                // LOS REGISTROS QUE HAYA EN LA TABLA, CONECTAREMOS A LA BASE DE DATOS Y HAREMOS UN
                // SELECT FILTRANDO POR LA CATEGORIA ESCOGIDA
                else{
                    String cat = cbCategoriaF.getValue().toString();
                    d.conectar();
                    tablaVideojuegos.getItems().clear();
                    tablaVideojuegos.setItems(d.obtenerVideojuegosCategoria(cat));
                    d.desconectar();
                }
            }
            // SI HEMOS PULSADO EL RADIOBUTTON DE PLATAFORMA
            else if(rbPlataforma.isSelected()){
                // SI NO HEMOS ESCOGIDO NINGUNA PLATAFORMA
                if(cbPlataformaF.getValue() == null){
                    AlertUtil.mostrarError("POR FAVOR, SELECCIONA UNA PLATAFORMA.");
                }
                // EN CASO CONTRARIO, GUARDAREMOS EN UNA VARIABLE LA PLATAFORMA ESCOGIDA, VACIAREMOS
                // LOS REGISTROS QUE HAYA EN LA TABLA, CONECTAREMOS A LA BASE DE DATOS Y HAREMOS UN
                // SELECT FILTRANDO POR LA PLATAFORMA ESCOGIDA
                else{
                    String pla = cbPlataformaF.getValue().toString();
                    d.conectar();
                    tablaVideojuegos.getItems().clear();
                    tablaVideojuegos.setItems(d.obtenerVideojuegosPlataforma(pla));
                    d.desconectar();
                }
            }
            // SI HEMOS PULSADO EL RADIOBUTTON DE SIN FILTROS (MUESTRA TODOS LOS RESULTADOS DE LA BBDD)
            else{
                ModoPantallaInicio(true);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que nos devuelve a la pantalla de inicio, "cancelando" asi cualquier
     * accion en curso
     * @param event
     */
    @FXML
    void Cancelar(ActionEvent event) {
        ModoPantallaInicio(true);
    }

    /**
     * Metodo para mostrar en pantalla el CheckBox de categorias si activamos el
     * RadioButton "Categoria" en la pantalla de inicio.
     * Tambien ocultamos el CheckBox de plataformas.
     * @param event
     */
    @FXML
    void rbCategoriaPulsado(MouseEvent event) {
        cbCategoriaF.setOpacity(1);
        cbCategoriaF.setDisable(false);
        cbPlataformaF.setOpacity(0);
        cbPlataformaF.setDisable(true);
    }

    /**
     * Metodo para mostrar en pantalla el CheckBox de plataformas si activamos el
     * RadioButton "Plataforma" en la pantalla de inicio.
     * Tambien ocultamos el CheckBox de categorias.
     * @param event
     */
    @FXML
    void rbPlataformaPulsado(MouseEvent event) {
        cbCategoriaF.setOpacity(0);
        cbCategoriaF.setDisable(true);
        cbPlataformaF.setOpacity(1);
        cbPlataformaF.setDisable(false);
    }

    /**
     * Metodo para ocultar los CheckBox de categoria y plataforma referentes a los
     * filtros.
     * @param event
     */
    @FXML
    void rbSinFiltrosPulsado(MouseEvent event) {
        cbCategoriaF.setOpacity(0);
        cbCategoriaF.setDisable(true);
        cbPlataformaF.setOpacity(0);
        cbPlataformaF.setDisable(true);
    }

    /**
     * Metodo que comprobara si hemos rellenado todos los campos a la hora de añadir un
     * videojuego y que, una vez rellenados, compruebe tambien si el juego ya se encuentra
     * en la BBDD. En caso contrario, lo añade a la BBDD.
     * @param event
     */
    @FXML
    void Add(ActionEvent event) {
        if(txtNombre.getText().equals("")){
            AlertUtil.mostrarError("Debes escribir el nombre del videojuego.");
        }
        else if(cbCategoria.getValue().equals("")){
            AlertUtil.mostrarError("Debes indicar la categoria del videojuego.");
        }
        else if(cbPlataforma.getValue().equals("")){
            AlertUtil.mostrarError("Debes indicar la plataforma del videojuego.");
        }
        else{
            try{
                // RECOGEMOS LOS DATOS INTRODUCIDOS DEL JUEGO Y NOS CONECTAMOS A LA BBDD
                String nom = txtNombre.getText();
                String cat = cbCategoria.getValue();
                String pla = cbPlataforma.getValue();
                d.conectar();

                /**
                 * BUCLE FOR-EACH PARA COMPROBAR SI EL JUEGO
                 * QUE INTENTAMOS INTRODUCIR YA ESTA EN LA LISTA
                 */
                int contador = 0;
                boolean repe = false;

                for(Videojuego v: d.obtenerVideojuegos()){
                    contador = 0;
                    if(v.getNombre().equals(nom)){
                        contador++;
                    }
                    if(v.getCategoria().equals(cat)){
                        contador++;
                    }
                    if(v.getPlataforma().equals(pla)){
                        contador++;
                    }

                    if(contador == 3){
                        repe = true;
                    }
                }
                // SI EL JUEGO YA ESTA EN LA BBDD (NO SE INTRODUCE Y SE DESCONECTA DE LA BBDD)
                if(repe){
                    AlertUtil.mostrarError("EL JUEGO QUE INTENTAS INTRODUCIR YA ESTA EN LA LISTA.");
                    d.desconectar();
                }
                // EN CASO CONTRARIO, LO AÑADE A LA BBDD Y VUELVE A LA PANTALLA DE INICIO
                else{
                    d.AddVideojuego(nom,cat,pla);
                    // AQUI SE DESCONECTA DE LA BBDD PARA VOLVER A CONECTARSE ABAJO
                    ModoPantallaInicio(true);
                }
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Metodo que primero comprobara si hemos pulsado un videojuego de la tabla para despues
     * mostrar una alerta pidiendo confirmacion. En caso afirmativo, borra el videojuego de
     * la BBDD.
     * @param event
     */
    @FXML
    void Delete(ActionEvent event) {
        try{
            // RECOGEMOS EL VIDEOJUEGO SELECCIONADO EN LA TABLA
            videojuegoseleccionado = tablaVideojuegos.getSelectionModel().getSelectedItem();

            // SI NO HEMOS SELECCIONADO NINGUN VIDEOJUEGO
            if(videojuegoseleccionado == null){
                AlertUtil.mostrarError("DEBES SELECCIONAR UN JUEGO");
            }
            // EN CASO CONTRARIO, PEDIRA CONFIRMACION DE SI BORRAR O NO EL JUEGO
            // SI = LO BORRA DE LA BBDD. NO = NO HACE NADA.
            else{
                if(AlertUtil.confirmacion("¿Deseas borrar el videojuego \"" + videojuegoseleccionado.getNombre() + "\" de la lista?")){
                    d.conectar();
                    d.DelVideojuego(videojuegoseleccionado);
                    ModoPantallaInicio(true);
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que recoge los datos de los campos y si no se han cambiado al intentar modificar
     * el videojuego, nos saldra un aviso. En caso contrario, modifica el videojuego en la BBDD.
     * @param event
     */
    @FXML
    void Modificar(ActionEvent event) {
        try{
            String nom = txtNombre.getText();
            String cat = cbCategoria.getValue();
            String pla = cbPlataforma.getValue();

            // SI LOS DATOS DE LOS CAMPOS SON LOS MISMOS
            // QUE LOS DEL VIDEOJUEGO SELECCIONADO EN LA TABLA
            if(nom.equals(videojuegoseleccionado.getNombre())
                    && cat.equals(videojuegoseleccionado.getCategoria())
                    && pla.equals(videojuegoseleccionado.getPlataforma())){
                AlertUtil.mostrarError("¡NO HAS MODIFICADO NADA!");
            }
            // SI NO, MODIFICA EL VIDEOJUEGO EN LA BBDD
            else{
                d.conectar();
                d.ModVideojuego(videojuegoseleccionado,nom,cat,pla);
                ModoPantallaInicio(true);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodos para activar u desactivar unos elementos u otros en la aplicacion segun la
     * accion que queramos realizar
     */

    public void ModoPantallaInicio(boolean b){
        txtHeader.setText("MIS VIDEOJUEGOS");

        // MODO PANTALLA INICIO
        tablaVideojuegos.setOpacity(1);
        tablaVideojuegos.setDisable(!b);
        rbCategoria.setOpacity(1);
        rbCategoria.setDisable(!b);
        rbPlataforma.setOpacity(1);
        rbPlataforma.setDisable(!b);
        rbSinFiltros.setOpacity(1);
        btnFiltrar.setOpacity(1);

        rbSinFiltros.setSelected(b);
        cbCategoriaF.setOpacity(0);
        cbCategoriaF.setDisable(true);
        cbPlataformaF.setOpacity(0);
        cbPlataformaF.setDisable(true);

        // MODO AÑADIR VIDEOJUEGO
        labelNombre.setOpacity(0);
        labelNombre.setDisable(b);
        labelCategoria.setOpacity(0);
        labelCategoria.setDisable(b);
        labelPlataforma.setOpacity(0);
        labelPlataforma.setDisable(b);
        txtNombre.setOpacity(0);
        txtNombre.setDisable(b);
        cbCategoria.setOpacity(0);
        cbCategoria.setDisable(b);
        cbPlataforma.setOpacity(0);
        cbPlataforma.setDisable(b);
        btnAddVideojuego.setOpacity(0);
        btnAddVideojuego.setDisable(b);

        // MODO ELIMINAR VIDEOJUEGO
        labelEliminarVideojuego.setOpacity(0);
        labelEliminarVideojuego.setDisable(b);
        btnDelVideojuego.setOpacity(0);
        btnDelVideojuego.setDisable(b);

        // MODO MODIFICAR JUEGO (CON TABLA)
        labelModificarVideojuego.setOpacity(0);
        labelModificarVideojuego.setDisable(b);
        btnModoModificarVideojuego2.setOpacity(0);
        btnModoModificarVideojuego2.setDisable(b);

        // MODO MODIFICAR JUEGO (SIN TABLA)
        btnMod.setOpacity(0);
        btnMod.setDisable(b);

        // BOTON CANCELAR
        btnCancelar.setOpacity(0);
        btnCancelar.setDisable(b);

        //////////////////////////////////////////

        // NOS CONECTAMOS A LA BASE DE DATOS, RECOGEMOS LOS REGISTROS Y LOS INSERTAMOS
        // EN LA TABLA
        try{
            d.conectar();
            tablaVideojuegos.getItems().clear();
            tablaVideojuegos.setItems(d.obtenerVideojuegos());
            d.desconectar();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println("Error al iniciar la aplicación");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Error al cargar la configuración");
        }
    }

    public void ModoAddVideojuego(boolean b){
        txtHeader.setText("AÑADIR VIDEOJUEGO");

        // MODO PANTALLA INICIO
        tablaVideojuegos.setOpacity(0);
        tablaVideojuegos.setDisable(b);
        rbCategoria.setOpacity(0);
        rbCategoria.setDisable(b);
        rbPlataforma.setOpacity(0);
        rbPlataforma.setDisable(b);
        rbSinFiltros.setOpacity(0);
        btnFiltrar.setOpacity(0);

        rbSinFiltros.setSelected(!b);
        cbCategoriaF.setOpacity(0);
        cbCategoriaF.setDisable(true);
        cbPlataformaF.setOpacity(0);
        cbPlataformaF.setDisable(true);

        // MODO AÑADIR VIDEOJUEGO
        labelNombre.setOpacity(1);
        labelNombre.setDisable(!b);
        labelCategoria.setOpacity(1);
        labelCategoria.setDisable(!b);
        labelPlataforma.setOpacity(1);
        labelPlataforma.setDisable(!b);
        txtNombre.setOpacity(1);
        txtNombre.setDisable(!b);
        cbCategoria.setOpacity(1);
        cbCategoria.setDisable(!b);
        cbPlataforma.setOpacity(1);
        cbPlataforma.setDisable(!b);
        btnAddVideojuego.setOpacity(1);
        btnAddVideojuego.setDisable(!b);

        txtNombre.setText("");
        cbCategoria.setValue("");
        cbPlataforma.setValue("");

        // MODO ELIMINAR VIDEOJUEGO
        labelEliminarVideojuego.setOpacity(0);
        labelEliminarVideojuego.setDisable(b);
        btnDelVideojuego.setOpacity(0);
        btnDelVideojuego.setDisable(b);

        // MODO MODIFICAR JUEGO (CON TABLA)
        labelModificarVideojuego.setOpacity(0);
        labelModificarVideojuego.setDisable(b);
        btnModoModificarVideojuego2.setOpacity(0);
        btnModoModificarVideojuego2.setDisable(b);

        // MODO MODIFICAR JUEGO (SIN TABLA)
        btnMod.setOpacity(0);
        btnMod.setDisable(b);

        // BOTON CANCELAR
        btnCancelar.setOpacity(1);
        btnCancelar.setDisable(!b);
    }

    public void ModoDeleteVideojuego(boolean b){
        txtHeader.setText("ELIMINAR VIDEOJUEGO");

        // MODO PANTALLA INICIO
        tablaVideojuegos.setOpacity(1);
        tablaVideojuegos.setDisable(!b);
        rbCategoria.setOpacity(0);
        rbCategoria.setDisable(b);
        rbPlataforma.setOpacity(0);
        rbPlataforma.setDisable(b);
        rbSinFiltros.setOpacity(0);
        btnFiltrar.setOpacity(0);

        rbSinFiltros.setSelected(!b);
        cbCategoriaF.setOpacity(0);
        cbCategoriaF.setDisable(true);
        cbPlataformaF.setOpacity(0);
        cbPlataformaF.setDisable(true);

        // MODO AÑADIR VIDEOJUEGO
        labelNombre.setOpacity(0);
        labelNombre.setDisable(b);
        labelCategoria.setOpacity(0);
        labelCategoria.setDisable(b);
        labelPlataforma.setOpacity(0);
        labelPlataforma.setDisable(b);
        txtNombre.setOpacity(0);
        txtNombre.setDisable(b);
        cbCategoria.setOpacity(0);
        cbCategoria.setDisable(b);
        cbPlataforma.setOpacity(0);
        cbPlataforma.setDisable(b);
        btnAddVideojuego.setOpacity(0);
        btnAddVideojuego.setDisable(b);

        // MODO ELIMINAR VIDEOJUEGO
        labelEliminarVideojuego.setOpacity(1);
        labelEliminarVideojuego.setDisable(!b);
        btnDelVideojuego.setOpacity(1);
        btnDelVideojuego.setDisable(!b);

        // MODO MODIFICAR JUEGO (CON TABLA)
        labelModificarVideojuego.setOpacity(0);
        labelModificarVideojuego.setDisable(b);
        btnModoModificarVideojuego2.setOpacity(0);
        btnModoModificarVideojuego2.setDisable(b);

        // MODO MODIFICAR JUEGO (SIN TABLA)
        btnMod.setOpacity(0);
        btnMod.setDisable(b);

        // BOTON CANCELAR
        btnCancelar.setOpacity(1);
        btnCancelar.setDisable(!b);
    }

    public void ModoModificarVideojuego(boolean b){
        txtHeader.setText("MODIFICAR VIDEOJUEGO");

        // MODO PANTALLA INICIO
        tablaVideojuegos.setOpacity(1);
        tablaVideojuegos.setDisable(!b);
        rbCategoria.setOpacity(0);
        rbCategoria.setDisable(b);
        rbPlataforma.setOpacity(0);
        rbPlataforma.setDisable(b);
        rbSinFiltros.setOpacity(0);
        btnFiltrar.setOpacity(0);

        rbSinFiltros.setSelected(!b);
        cbCategoriaF.setOpacity(0);
        cbCategoriaF.setDisable(true);
        cbPlataformaF.setOpacity(0);
        cbPlataformaF.setDisable(true);

        // MODO AÑADIR VIDEOJUEGO
        labelNombre.setOpacity(0);
        labelNombre.setDisable(b);
        labelCategoria.setOpacity(0);
        labelCategoria.setDisable(b);
        labelPlataforma.setOpacity(0);
        labelPlataforma.setDisable(b);
        txtNombre.setOpacity(0);
        txtNombre.setDisable(b);
        cbCategoria.setOpacity(0);
        cbCategoria.setDisable(b);
        cbPlataforma.setOpacity(0);
        cbPlataforma.setDisable(b);
        btnAddVideojuego.setOpacity(0);
        btnAddVideojuego.setDisable(b);

        // MODO ELIMINAR VIDEOJUEGO
        labelEliminarVideojuego.setOpacity(0);
        labelEliminarVideojuego.setDisable(b);
        btnDelVideojuego.setOpacity(0);
        btnDelVideojuego.setDisable(b);

        // MODO MODIFICAR JUEGO (CON TABLA)
        labelModificarVideojuego.setOpacity(1);
        labelModificarVideojuego.setDisable(!b);
        btnModoModificarVideojuego2.setOpacity(1);
        btnModoModificarVideojuego2.setDisable(!b);

        // MODO MODIFICAR JUEGO (SIN TABLA)
        btnMod.setOpacity(0);
        btnMod.setDisable(b);

        // BOTON CANCELAR
        btnCancelar.setOpacity(1);
        btnCancelar.setDisable(!b);
    }

    public void ModoModificarVideojuego2(boolean b){
        videojuegoseleccionado = tablaVideojuegos.getSelectionModel().getSelectedItem();
        if(videojuegoseleccionado == null){
            AlertUtil.mostrarError("DEBES SELECCIONAR UN JUEGO");
        }
        else{
            // MODO PANTALLA INICIO
            tablaVideojuegos.setOpacity(0);
            tablaVideojuegos.setDisable(b);
            rbCategoria.setOpacity(0);
            rbCategoria.setDisable(b);
            rbPlataforma.setOpacity(0);
            rbPlataforma.setDisable(b);
            rbSinFiltros.setOpacity(0);
            btnFiltrar.setOpacity(0);

            rbSinFiltros.setSelected(!b);
            cbCategoriaF.setOpacity(0);
            cbCategoriaF.setDisable(true);
            cbPlataformaF.setOpacity(0);
            cbPlataformaF.setDisable(true);

            // MODO AÑADIR VIDEOJUEGO
            labelNombre.setOpacity(1);
            labelNombre.setDisable(!b);
            labelCategoria.setOpacity(1);
            labelCategoria.setDisable(!b);
            labelPlataforma.setOpacity(1);
            labelPlataforma.setDisable(!b);
            txtNombre.setOpacity(1);
            txtNombre.setDisable(!b);
            cbCategoria.setOpacity(1);
            cbCategoria.setDisable(!b);
            cbPlataforma.setOpacity(1);
            cbPlataforma.setDisable(!b);
            btnAddVideojuego.setOpacity(0);
            btnAddVideojuego.setDisable(b);

            // MODO ELIMINAR VIDEOJUEGO
            labelEliminarVideojuego.setOpacity(0);
            labelEliminarVideojuego.setDisable(b);
            btnDelVideojuego.setOpacity(0);
            btnDelVideojuego.setDisable(b);

            // MODO MODIFICAR JUEGO (CON TABLA)
            labelModificarVideojuego.setOpacity(0);
            labelModificarVideojuego.setDisable(b);
            btnModoModificarVideojuego2.setOpacity(0);
            btnModoModificarVideojuego2.setDisable(b);

            // MODO MODIFICAR JUEGO (SIN TABLA)
            btnMod.setOpacity(1);
            btnMod.setDisable(!b);

            // BOTON CANCELAR
            btnCancelar.setOpacity(1);
            btnCancelar.setDisable(!b);

            ////////////////////////////////////////////

            txtNombre.setText(videojuegoseleccionado.getNombre());
            cbCategoria.setValue(videojuegoseleccionado.getCategoria());
            cbPlataforma.setValue(videojuegoseleccionado.getPlataforma());

        }
    }

    /**
     * Metodo de la clase padre Initializable que inicializa elementos de JavaFX con unos valores
     * y ejecuta acciones nada mas cargar la clase.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.colVideojuego.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        this.colPlataforma.setCellValueFactory(new PropertyValueFactory("plataforma"));

        d.listavideojuegos = FXCollections.observableArrayList();

        categorias = new String[]{"Arcade","Accion","Disparos","Peleas","RPG","Plataformas","Puzzles","Musical","Carreras","Deportes"};
        cbCategoria.setItems(FXCollections.observableArrayList(categorias));
        cbCategoriaF.setItems(FXCollections.observableArrayList(categorias));

        plataformas = new String[]{"Nintendo Switch","PS4","Xbox One"};
        cbPlataforma.setItems(FXCollections.observableArrayList(plataformas));
        cbPlataformaF.setItems(FXCollections.observableArrayList(plataformas));

        ModoPantallaInicio(true);
    }
}