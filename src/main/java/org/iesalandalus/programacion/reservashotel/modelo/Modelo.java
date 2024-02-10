package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;

public class Modelo {

    private static int CAPACIDAD=10; //Inicializo la constante CAPACIDAD

    private Huespedes huespedes;
    private Habitaciones habitaciones;
    private Reservas reservas;

    //MÉTODOS CONSTRUCTOR, COMENZAR Y TERMINAR

    public Modelo(){ // Creo el constructor que inicializa las instancias de las clases de negocio

        huespedes = new Huespedes(CAPACIDAD);
        habitaciones = new Habitaciones(CAPACIDAD);
        reservas = new Reservas(CAPACIDAD);

    }

    public void comenzar(){

        new Modelo(); // Llama al constructor para inicializar las instancias

    }

    public void terminar(){ //Simplemente avisa por pantalla que el modelo ha terminado

        System.out.println("El modelo ha terminado.");

    }

    // Métodos para gestionar huespedes

    public void insertar(Huesped huesped) throws OperationNotSupportedException {

        huespedes.insertar(huesped);

    }

    public Huesped buscar(Huesped huesped){

        return huespedes.buscar(huesped);

    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        huespedes.borrar(huesped);

    }

    public Huesped[] getHuespedes(){

        return huespedes.get();
    }

    // Métodos para gestionar habitaciones

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {

        habitaciones.insertar(habitacion);

    }

    public Habitacion buscar(Habitacion habitacion){

        return habitaciones.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {

        habitaciones.borrar(habitacion);
    }

    public Habitacion [] getHabitaciones(){

        return habitaciones.get();
    }

    public Habitacion [] getHabitaciones(TipoHabitacion tipoHabitacion){

        return habitaciones.get(tipoHabitacion);
    }

    // Métodos para gestionar reservas

    public void insertar(Reserva reserva) throws OperationNotSupportedException {

        reservas.insertar(reserva);

    }

    public Reserva buscar(Reserva reserva){

        return reservas.buscar(reserva);

    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {

        reservas.borrar(reserva);

    }

    public Reserva [] getReservas(){

        return reservas.get();

    }

    public Reserva [] getReservas(Huesped huesped){

        return reservas.getReservas(huesped);
    }

    public Reserva [] getReservas(TipoHabitacion tipoHabitacion){

        return reservas.getReservas(tipoHabitacion);
    }

    public Reserva [] getReservasFuturas(Habitacion habitacion){

        return reservas.getReservasFuturas(habitacion);
    }

    //Métodos para gestionar checkIn y checkOut

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {

        reservas.realizarCheckin(reserva, fecha);

    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {

        reservas.realizarCheckout(reserva, fecha);

    }

}
