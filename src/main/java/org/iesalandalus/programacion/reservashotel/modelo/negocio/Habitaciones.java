package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import java.util.Arrays;

import javax.naming.OperationNotSupportedException;

public class Habitaciones {

    private int capacidad;
    private int tamano;
    private Habitacion [] coleccionHabitaciones;

    //MÉTODOS

    public Habitaciones(int capacidad){

        if(capacidad<=0){

            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad=capacidad;

        this.tamano=0;

        coleccionHabitaciones = new Habitacion [capacidad];
    }

    public Habitacion [] get(){

        return copiaProfundaHabitaciones();
    }

    private Habitacion [] copiaProfundaHabitaciones(){

        Habitacion[] miHabitacion = new Habitacion[getCapacidad()];

        int indice = 0;

        for (Habitacion habitacion : coleccionHabitaciones) {
            if (habitacion != null) {
                miHabitacion[indice] = new Habitacion(habitacion);
                indice++;
            }
        }

        return Arrays.copyOf(miHabitacion, indice);
    }

    public Habitacion[] get(TipoHabitacion tipoHabitacion) {

        Habitacion[] habitacionesTipo = new Habitacion[capacidad];

        int indice = 0;


        for (int i = 0; i < tamano; i++) {

            if (coleccionHabitaciones[i].getTipoHabitacion() == tipoHabitacion) {

                habitacionesTipo[indice] = new Habitacion(coleccionHabitaciones[i]);

                indice++;
            }
        }

        return Arrays.copyOf(habitacionesTipo, indice);
    }

    public int getTamano() {

        return tamano;
    }

    public int getCapacidad() {

        return capacidad;
    }

    public void insertar (Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) {

            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }

        // Compruebo si la habitación ya existe en la colección

        if (buscar(habitacion) != null) {

            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }


        if (tamano >= capacidad) {

            throw new OperationNotSupportedException ("ERROR: No se aceptan más habitaciones.");
        }

        // Agrego la habitación al final de la colección

        coleccionHabitaciones[getTamano()] = new Habitacion(habitacion);

        tamano++;
    }

    private int buscarIndice(Habitacion habitacion)  {


        for (int i = 0; i < tamano; i++) {

            if (coleccionHabitaciones[i].equals(habitacion)) {

                return i;
            }
        }
        return -1;
    }

    private boolean tamanoSuperado(int indice) {

        return indice >= tamano;
    }

    private boolean capacidadSuperada(int indice) {

        return indice >= capacidad;
    }

    public Habitacion buscar(Habitacion habitacion) {
        if (habitacion==null){

            throw new NullPointerException("error nulo");
        }
        int indice = buscarIndice(habitacion);

        return (indice != -1) ? coleccionHabitaciones[indice] : null;
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException {

        if(habitacion==null){

            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }

        int indice = buscarIndice(habitacion);

        if(indice == -1){

            throw new  OperationNotSupportedException ("ERROR: No existe ninguna habitación como la indicada.");
        }



        desplazarUnaPosicionHaciaIzquierda(indice);

        tamano--;


    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        if(indice<0 || capacidadSuperada(indice)){

            throw new IllegalArgumentException("desplazarunaPosicion no puede ser menor que cero");
        }

        for (int i = indice; i < tamano - 1; i++) {

            coleccionHabitaciones[i] = coleccionHabitaciones[i + 1];
        }
        // Garantiza que el último elemento se establece en null

        coleccionHabitaciones[tamano - 1] = null;
    }




}
