package com.example.appvideojuegosfinal.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class AlertUtil {
    public static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    /**
     * Mostrara una ventana de confirmacion. Si = true, No = false.
     * @param mensaje
     * @return
     */
    public static boolean confirmacion(String mensaje){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("BORRAR VIDEOJUEGO");
//        a.setHeaderText("");
        a.setContentText(mensaje);
        Optional<ButtonType> r = a.showAndWait();
        if(r.get() == ButtonType.OK){
            return true;
        }
        return false;
    }
}
