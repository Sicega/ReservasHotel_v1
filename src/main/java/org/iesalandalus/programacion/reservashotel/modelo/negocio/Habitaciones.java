package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import java.util.Arrays;

import javax.naming.OperationNotSupportedException;

public class Habitaciones {

    private int capacidad;
    private int tamano;
    private Habitacion [] coleccionHabitaciones;

    //M�TODOS

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

        Habitacion [] miHabitacion = new Habitacion [getCapacidad()];

        int indice=0;

        for (Habitacion habitacion : coleccionHabitaciones){

            miHabitacion [indice]= new Habitacion(habitacion);

            indice++;
        }

        return miHabitacion;
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

            throw new IllegalArgumentException("ERROR: No es posible copiar una habitaci�n nula.");
        }

        // Compruebo si la habitaci�n ya existe en la colecci�n

        if (buscar(habitacion) == null) {

            throw new IllegalArgumentException("ERROR: Ya existe esa habitaci�n..");
        }


        if (tamano >= capacidad) {

            throw new OperationNotSupportedException ("ERROR: No se aceptan m�s habitaciones.");
        }

        // Agrego la habitaci�n al final de la colecci�n

        coleccionHabitaciones[tamano] = new Habitacion(habitacion);

        tamano++;
    }

    private int buscarIndice(Habitacion habitacion)  {

        if (habitacion==null){

            throw new NullPointerException("Error");
        }

        int indice = -1;

        for (int i = 0; i < tamano; i++) {

            if (coleccionHabitaciones[i].equals(habitacion)) {

                indice=i;
            }
        }

        return indice;
    }

    private boolean tamanoSuperado(int indice) {

        return indice >= tamano;
    }

    private boolean capacidadSuperada(int indice) {

        return indice >= capacidad;
    }

    public Habitacion buscar(Habitacion habitacion) {

        int indice = buscarIndice(habitacion);

        return (indice != -1) ? coleccionHabitaciones[indice] : null;
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException {

        if(habitacion==null){

            throw new NullPointerException("ERROR: No se puede borrar una habitaci�n nula.");
        }

        int indice = buscarIndice(habitacion);

        if(indice == -1){

            throw new  OperationNotSupportedException ("ERROR: No existe ninguna habitaci�n como la indicada.");
        }

        if (indice != -1) {

            desplazarUnaPosicionHaciaIzquierda(indice);

            tamano--;

        }
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {

        for (int i = indice; i < tamano - 1; i++) {

            coleccionHabitaciones[i] = coleccionHabitaciones[i + 1];
        }
        // Garantiza que el �ltimo elemento se establece en null

        coleccionHabitaciones[tamano - 1] = null;
    }




}
