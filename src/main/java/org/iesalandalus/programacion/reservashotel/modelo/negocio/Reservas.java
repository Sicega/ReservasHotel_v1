package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
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

            if(reserva != null) {

                misReservas[indice] = new Reserva(reserva);

                indice++;
            }
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
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }

        if (capacidad < 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        if (tamano >= capacidad) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más reservas.");
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

            throw new NullPointerException("error nulo");
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
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }

        desplazarUnaPosicionHaciaIzquierda(indice);

        tamano--;

    }

    // Método para desplazar una posición hacia la izquierda
    private void desplazarUnaPosicionHaciaIzquierda(int indice) {

        if(indice<0 || capacidadSuperada(indice)){

            throw new IllegalArgumentException("desplazarunaPosicion no puede ser menor que cero");
        }

        for (int i = indice; i < tamano - 1; i++) {
            coleccionReservas[i] = coleccionReservas[i + 1];
        }
        // Garantiza que el último elemento se establece en null
        coleccionReservas[tamano - 1] = null;
    }

    // Métodos para obtener reservas por huésped o tipo de habitación
    public Reserva[] getReservas(Huesped huesped) {

        if(huesped==null){ //Verifica que el huesped no sea nulo

            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");
        }

        int posicion=0;

        Reserva[] miReserva= new Reserva[capacidad];

        for(Reserva elemento : coleccionReservas){ //Recorro la colección de reservas para encontrar las reservas del huesped indicado

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

        if (habitacion == null) { //Verifico que la habitación no sea nula

            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }

        LocalDate fechaActual = LocalDate.now(); // Obtiene la fecha actual

        Reserva[] reservasFuturas = new Reserva[tamano]; // Array para almacenar las reservas futuras

        int contador = 0;

        for (int i = 0; i < tamano; i++) { // Itera sobre las reservas para encontrar las futuras de la habitación especificada

            if (coleccionReservas[i].getHabitacion().equals(habitacion)
                    && coleccionReservas[i].getFechaInicioReserva().isAfter(fechaActual)) {

                reservasFuturas[contador] = new Reserva(coleccionReservas[i]); // Almacena una copia profunda de la reserva en el nuevo array

                contador++;
            }
        }

        return Arrays.copyOf(reservasFuturas, contador);
    }

    //El método realizar checkIn debe buscar el índice de la reserva y si verifica que la reserva está en la colección aplica el setCheckIn con parámetro fecha

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) {

        //Compruebo si la reserva o la fecha son nulas

        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }

        if(fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay())){

            throw new IllegalArgumentException("ERROR: La fecha del checkIn no puede ser anterior a la reserva.");
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

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha)  {

        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }

        if(reserva.getCheckIn()==null){

            throw new NullPointerException("ERROR: No puedes hacer checkOut si el checkIn es nulo.");
        }

        if(fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay()) || fecha.isBefore(reserva.getCheckIn())){

            throw new IllegalArgumentException("ERROR: la fecha del checkOut no puede ser anterior a la de " +
                    "inicio de reserva o antes del checkIn.");

        }

        int indice = buscarIndice(reserva);

        if (indice == -1) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }

        coleccionReservas[indice].setCheckOut(fecha);
    }

}
