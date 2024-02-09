package org.iesalandalus.programacion.reservashotel.controlador;

import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;

public class Controlador {

    private Modelo modelo;
    private Vista vista;


    //M�TODOS CONSTRUCTOR, COMENZAR Y TERMINAR

    public Controlador(Modelo modelo, Vista vista){

        if (modelo == null || vista == null) {
            throw new IllegalArgumentException("ERROR: El modelo y la vista no pueden ser nulos.");
        }
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);

    }

    public void comenzar(){

        modelo.comenzar();
        vista.comenzar();

    }

    public void terminar(){

        modelo.terminar();
        vista.terminar();


    }

    //M�todos para gestionar huespedes

    public void insertar(Huesped huesped) throws OperationNotSupportedException {

        modelo.insertar(huesped);


    }

    public Huesped buscar (Huesped huesped){

        return modelo.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        modelo.borrar(huesped);

    }

    public Huesped [] getHuespedes(){

        return modelo.getHuespedes();
    }

    //M�todos para gestionar habitaciones

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {

        modelo.insertar(habitacion);

    }

    public Habitacion buscar(Habitacion habitacion){

        return modelo.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {

        modelo.borrar(habitacion);

    }

    public Habitacion [] getHabitaciones(){

        return modelo.getHabitaciones();

    }

    public Habitacion [] getHabitaciones(TipoHabitacion tipoHabitacion){

        return modelo.getHabitaciones(tipoHabitacion);

    }


    //M�todos para gestionar reservas

    public void insertar(Reserva reserva) throws OperationNotSupportedException {

        modelo.insertar(reserva);

    }

    public void borrar (Reserva reserva) throws OperationNotSupportedException {

        modelo.borrar(reserva);

    }

    public Reserva buscar(Reserva reserva){

        return modelo.buscar(reserva);

    }

    public  Reserva [] getReservas(){

        return modelo.getReservas();

    }

    public  Reserva [] getReservas(Huesped huesped){

        return modelo.getReservas(huesped);

    }

    public  Reserva [] getReservas(TipoHabitacion tipoHabitacion){

        return modelo.getReservas(tipoHabitacion);

    }

    public  Reserva [] getReservasFuturas(Habitacion habitacion){

        return modelo.getReservasFuturas(habitacion);

    }

    //M�todos para gestionar checkIn y checkOut

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) throws OperationNotSupportedException {

        modelo.realizarCheckIn(reserva, fecha);

    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) throws OperationNotSupportedException {

        modelo.realizarCheckOut(reserva, fecha);

    }

}
