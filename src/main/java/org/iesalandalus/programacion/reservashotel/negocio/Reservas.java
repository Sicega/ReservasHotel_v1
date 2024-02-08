package org.iesalandalus.programacion.reservashotel.negocio;

import org.iesalandalus.programacion.reservashotel.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.naming.OperationNotSupportedException;

public class Reservas {

    private int capacidad;
    private int tamano;
    private Reserva [] coleccionReservas;


    public Reservas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionReservas = new Reserva[capacidad];
    }


    public Reserva[] get() {

        return copiaProfundaReservas();
    }

    private Reserva[] copiaProfundaReservas() {

        Reserva[] misReservas = new Reserva[getCapacidad()];

        int indice = 0;

        for (Reserva reserva : coleccionReservas) {

            misReservas[indice] = new Reserva(reserva);

            indice++;
        }

        return misReservas;
    }

    public int getTamano() {

        return tamano;
    }

    public int getCapacidad() {

        return capacidad;
    }

    public void insertar (Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }

        // Compruebo si la reserva ya existe en la colección

        if (buscar(reserva) != null) {
            throw new IllegalArgumentException("ERROR: La reserva ya existe en la colección.");
        }

        if (capacidad < 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        if (tamano >= capacidad) {
            throw new IllegalArgumentException("ERROR: No se aceptan más reservas.");
        }

        // Agrego la reserva al final de la colección

        coleccionReservas[getTamano()] = new Reserva(reserva);

        tamano++;
    }


    // Método para buscar el índice de una reserva
    private int buscarIndice(Reserva reserva) {

        for (int i = 0; i < tamano; i++) {

            if (coleccionReservas[i].equals(reserva)) {

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

    public Reserva buscar(Reserva reserva)  {
        if (reserva==null){

            throw new NullPointerException("errorcillo");
        }

        int indice = buscarIndice(reserva);

        return (indice != -1) ? coleccionReservas[indice] : null;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {

        if (reserva == null) {

            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }

        int indice = buscarIndice(reserva);

        if (indice == -1) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }

        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        }
    }

    // Método para desplazar una posición hacia la izquierda
    private void desplazarUnaPosicionHaciaIzquierda(int indice) {

        //todo hacer if

        if(indice<0 || capacidadSuperada(indice)){

            throw new NullPointerException("desplazarunaPosicion no puede ser menor que cero");
        }

        for (int i = indice; i < tamano - 1; i++) {
            coleccionReservas[i] = coleccionReservas[i + 1];
        }
        // Garantiza que el último elemento se establece en null
        coleccionReservas[tamano - 1] = null;
    }

    // Método para obtener reservas por huésped o tipo de habitación
    public Reserva[] getReservas(Huesped huesped) {

        if(huesped==null){

            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");
        }

        int posicion=0;

        Reserva[] miReserva= new Reserva[capacidad];

        for(Reserva elemento : coleccionReservas){

            if(elemento.getHuesped().equals(huesped)) {

                miReserva[posicion]= new Reserva(elemento);

                posicion++;
            }

        }

        return miReserva;
    }

    public Reserva[] getReservas(TipoHabitacion tipoHabitacion) {

        if(tipoHabitacion==null){

            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }

        int posicion=0;

        Reserva[] miReserva= new Reserva[capacidad];

        for(Reserva elemento : coleccionReservas){

            if(elemento.getHabitacion().getTipoHabitacion().equals(tipoHabitacion)) {

                miReserva[posicion]= new Reserva(elemento);

                posicion++;
            }

        }

        return miReserva;
    }

    // Método para obtener reservas futuras para una habitación
    public Reserva[] getReservasFuturas(Habitacion habitacion) {

        if (habitacion == null) {

            throw new IllegalArgumentException("ERROR: La habitación no puede ser nula.");
        }

        LocalDate fechaActual = LocalDate.now();

        Reserva[] reservasFuturas = new Reserva[tamano];

        int contador = 0;

        for (int i = 0; i < tamano; i++) {

            if (coleccionReservas[i].getHabitacion().equals(habitacion)
                    && coleccionReservas[i].getFechaInicioReserva().isAfter(fechaActual)) {

                reservasFuturas[contador] = new Reserva(coleccionReservas[i]);

                contador++;
            }
        }

        return Arrays.copyOf(reservasFuturas, contador);
    }

    //El método realizar checkIn debe buscar el índice de la reserva y si verifica que la reserva está en la colección aplica el setCheckIn con parámetro fecha

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) throws OperationNotSupportedException {

        //Compruebo si la reserva o la fecha son nulas

        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }

        //busco el indice de la reserva para ver si existe

        int indice = buscarIndice(reserva);

        //Si devuelve -1 es porque la reserva no existe y lanza una excepción

        if (indice == -1) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }

        //Si la reserva existe se le aplica a la colección el método setCheckIn

        coleccionReservas[indice].setCheckIn(fecha);
    }

    //El mismo procedimiento de realizarCheckIn se aplica al checkOut

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha) throws OperationNotSupportedException {

        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }

        int indice = buscarIndice(reserva);

        if (indice == -1) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }

        coleccionReservas[indice].setCheckOut(fecha);
    }

}
