package org.iesalandalus.programacion.reservashotel.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

public class Reserva {

    public static final int MAX_NUMERO_MESES_RESERVA=6;
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT=12;
    public static final String FORMATO_FECHA_RESERVA="dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA="dd/MM/yyyy hh:mm:ss";

    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    /*2.8-El atributo precio debe calcularse teniendo en cuenta el coste de la habitación, el régimen de alojamiento y el número de personas alojadas. */
    private double precio;
    private int numeroPersonas;

    // MÉTODO CONSTRUCTOR

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas){

        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        setPrecio();

    }

    //CONSTRUCTOR COPIA

    public Reserva(Reserva reserva){

        if(reserva==null){

            throw new NullPointerException("ERROR: la copia de reserva no puede ser nula.");
        }

        setHuesped(reserva.getHuesped());
        setHabitacion(reserva.getHabitacion());
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());
        setCheckIn(reserva.getCheckIn());
        setCheckOut(reserva.getCheckOut());
        setNumeroPersonas(reserva.getNumeroPersonas());
        setPrecio();

    }

    //MÉTODOS GETTER Y SETTER

    public Huesped getHuesped() {

        return huesped;
    }

    public void setHuesped(Huesped huesped) {

        if(huesped == null){

            throw new NullPointerException("ERROR: El huesped no puede ser nulo.");
        }

        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {

        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {

        if(habitacion == null){

            throw new NullPointerException("ERROR: La habitacion no puede ser nulo.");
        }

        this.habitacion = habitacion;
    }

    public Regimen getRegimen() {

        return regimen;
    }

    public void setRegimen(Regimen regimen) {

        if(regimen == null){

            throw new NullPointerException("ERROR: El regimen no puede ser nulo.");
        }

        this.regimen = regimen;
    }

    public double getPrecio() {

        return precio;
    }

    private void setPrecio() {

        long diasReserva=fechaInicioReserva.until(fechaFinReserva, ChronoUnit.DAYS);


        precio = (habitacion.getPrecio()*diasReserva)+(regimen.getIncrementoPrecio()*numeroPersonas*diasReserva);
    }

    /*2.1-La fecha de inicio de la reserva no puede
    ser anterior al día que se intenta hacer la reserva.
    2.2-La fecha de inicio de la reserva no puede ser posterior
    al número de meses indicado por la constante MAX_NUMERO_MESES_RESERVA.*/

    public void setFechaInicioReserva(LocalDate fechaInicioReserva){

        if(fechaInicioReserva==null){

            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        }

        if(fechaInicioReserva.isBefore(LocalDate.now())){

            throw new IllegalArgumentException("ERROR: La fecha de inicio de reserva no puede ser anterior al día que se intenta hacer la reserva.");

        }

        if(fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))){

            throw new IllegalArgumentException("ERROR: La fecha de inicio de reserva no puede ser posterior al número de meses indicado.");
        }


        this.fechaInicioReserva=fechaInicioReserva;
    }

    public LocalDate getFechaInicioReserva(){

        return fechaInicioReserva;
    }

    /*2.3-La fecha de fin de la reserva debe ser posterior a la de inicio.*/

    public void setFechaFinReserva(LocalDate fechaFinReserva) {

        if(fechaFinReserva==null){

            throw new NullPointerException("ERROR: La fecha de fin de la reserva no puede ser nula.");
        }

        if(fechaFinReserva.isBefore(fechaInicioReserva)){

            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");

        }

        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDate getFechaFinReserva() {

        return fechaFinReserva;
    }

    /*2.4-El número de personas que se van a alojar en la habitación
     no puede superar al número máximo de personas que, por el tipo de habitación reservada, se permiten alojar.*/

    public void setNumeroPersonas(int numeroPersonas){

        if(numeroPersonas<1){

            throw new IllegalArgumentException("El nºde personas no puede ser menor a 1");
        }

        if(numeroPersonas>habitacion.getTipoHabitacion().getNumeroMaximoPersonas()){

            throw new IllegalArgumentException("ERROR: El número de personas no puede superar al número máximo de personas por tipo de habitación.");
        }

        this.numeroPersonas=numeroPersonas;
    }

    public int getNumeroPersonas(){

        return numeroPersonas;
    }

    /*2.5-El checkin no puede ser anterior al inicio de la reserva.*/

    public void setCheckIn(LocalDateTime checkIn){

       if(checkIn==null) {

           throw new NullPointerException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");

       }


        if(checkIn.isBefore(fechaInicioReserva.atStartOfDay())){

            throw new IllegalArgumentException("ERROR: El checkin no puede ser anterior al inicio de la reserva.");
        }

        if(checkIn.isAfter((fechaFinReserva.atStartOfDay()))){

            throw new IllegalArgumentException("ERROR: El checkin no puede ser posterior al inicio de la reserva.");
        }

        this.checkIn=checkIn;

    }

    public LocalDateTime getCheckIn(){

        return checkIn;
    }

    /*2.6-El checkout no puede ser anterior al checkin.
    *2.7- El checkout debe hacerse como máximo a las 12:00 horas del día en que finaliza la reserva.
    * Para esto deberás usar la constante MAX_HORAS_POSTERIOR_CHECKOUT.*/

    public void setCheckOut(LocalDateTime checkOut){

        if(checkOut==null){

            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        }

        if(checkOut.isBefore(getCheckIn())){

            throw new IllegalArgumentException("ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        }

        if(checkOut.isAfter(getFechaFinReserva().atStartOfDay().plusHours(MAX_HORAS_POSTERIOR_CHECKOUT))){

            throw new IllegalArgumentException("ERROR: El check out no puede ser después de las 12:00");
        }

        this.checkOut=checkOut;

    }

    public LocalDateTime getCheckOut(){

        return checkOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(habitacion, reserva.habitacion) && Objects.equals(fechaInicioReserva, reserva.fechaInicioReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA);
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA);


        return String.format("Huesped: %s %s Habitación:%s - %s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s Checkout: %s Precio: %.2f Personas: %d",
                getHuesped().getNombre(), getHuesped().getDni(), getHabitacion().getIdentificador(),
                getHabitacion().getTipoHabitacion(), getFechaInicioReserva().format(formatoFecha),
                getFechaFinReserva().format(formatoFecha), getCheckIn().format(formatoFechaHora),
                getCheckOut().format(formatoFechaHora), getPrecio(), getNumeroPersonas());
    }
}
