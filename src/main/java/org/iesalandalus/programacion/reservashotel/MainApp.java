package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class MainApp {

    public static void main(String[] args) {


        // Crea instancias de modelo, vista y controlador

        Reservas reservas = new Reservas(10);
        Huespedes huespedes = new Huespedes(10);
        Habitaciones habitaciones = new Habitaciones(10);
        Controlador controlador = new Controlador(null, null);
        Vista vista = new Vista();

        // Configura la vista con el controlador

        vista.setControlador(controlador);

        // Inicia la aplicación invocando el método comenzar del controlador

        controlador.comenzar();
    }


    }

