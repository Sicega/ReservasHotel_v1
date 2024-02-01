package org.iesalandalus.programacion.reservashotel.negocio;

import org.iesalandalus.programacion.reservashotel.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;
import java.time.LocalDate;
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
            throw new IllegalArgumentException("ERROR: No se puede insertar una reserva nula.");
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

        coleccionReservas[tamano] = new Reserva(reserva);

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
        for (int i = indice; i < tamano - 1; i++) {
            coleccionReservas[i] = coleccionReservas[i + 1];
        }
        // Garantiza que el último elemento se establece en null
        coleccionReservas[tamano - 1] = null;
    }

    // Método para obtener reservas por huésped o tipo de habitación
    public Reserva[] getReservas(Huesped huesped) {

        return Arrays.stream(coleccionReservas)
                .filter(reserva -> reserva.getHuesped().equals(huesped))
                .toArray(Reserva[]::new);
    }

    public Reserva[] getReservas(TipoHabitacion tipoHabitacion) {

        return Arrays.stream(coleccionReservas)
                .filter(reserva -> reserva.getHabitacion().getTipoHabitacion() == tipoHabitacion)
                .toArray(Reserva[]::new);
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

}
