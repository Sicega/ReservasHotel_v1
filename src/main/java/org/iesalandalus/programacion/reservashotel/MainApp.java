package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class MainApp {

    public static final int CAPACIDAD = 10; //Inicializo la constante

    public static void main(String[] args) {

        // Creo instancias de modelo, vista y controlador

        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);

        // Configuro la vista con el controlador

        vista.setControlador(controlador);

        // Inicio la aplicación invocando el método comenzar del controlador

        controlador.comenzar();
    }

}

