package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;

public class Vista {

    private Controlador controlador;

    public void setControlador(Controlador controlador) {

        if (controlador != null) {

            this.controlador = controlador;
        }
    }

    public void comenzar(){


    }

    public void terminar() {
        System.out.println("¡Hasta luego!");
    }
}
