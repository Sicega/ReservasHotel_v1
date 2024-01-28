package org.iesalandalus.programacion.reservashotel.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva {

    public static final int MAX_NUMERO_MESES_RESERVA=6; //Establezco 6 meses ya que así lo indican los tests
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT=12; //Hasta 12:00 pm es el máximo en el que un huesped puede quedarse tras el checkout
    public static final String FORMATO_FECHA_RESERVA="dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA="dd/MM/yyyy hh:mm:ss";

    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    // 3-Crea el constructor con parámetros que harán uso de los métodos de modificación.

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas){

        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        setPrecio();

    }

    //4-Crea el constructor copia.

    public Reserva(Reserva reserva){

        if(reserva==null){

            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");
        }

        setHuesped(reserva.getHuesped());
        setHabitacion(reserva.getHabitacion());
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());

        //Compruebo que el check in y el check out no son nulos antes de asignarlos

        if (reserva.getCheckIn() != null) {

            setCheckIn(reserva.getCheckIn());
        }

        if(reserva.getCheckOut() != null){

            setCheckOut(reserva.getCheckOut());
        }

        setNumeroPersonas(reserva.getNumeroPersonas());
        setPrecio();

    }

    //MÉTODOS GETTER Y SETTER

    public Huesped getHuesped() {

        return huesped;
    }

    public void setHuesped(Huesped huesped) {

        if(huesped == null){

            throw new NullPointerException("ERROR: El huésped de una reserva no puede ser nulo.");
        }

        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {

        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {

        if(habitacion == null){

            throw new NullPointerException("ERROR: La habitación de una reserva no puede ser nula.");
        }

        this.habitacion = habitacion;
    }

    public Regimen getRegimen() {

        return regimen;
    }

    public void setRegimen(Regimen regimen) {

        if(regimen == null){

            throw new NullPointerException("ERROR: El régimen de una reserva no puede ser nulo.");
        }

        this.regimen = regimen;
    }

    public double getPrecio() {

        return precio;
    }

    /*2.8-El atributo precio debe calcularse teniendo en cuenta el coste de la habitación, el régimen de alojamiento y el número de personas alojadas. */

    private void setPrecio() {

        //Con una variable auxiliar (diasReserva) almaceno la diferencia de dias entre la fecha de inicio y de fin de reserva usando el método until

        long diasReserva=fechaInicioReserva.until(fechaFinReserva, ChronoUnit.DAYS);

        //Para calcular el precio total de la reserva, tengo en cuenta el tipo de habitación multiplicado por el nº de días y sumo el tipo de regimen, multiplicado por el nº de personas alojadas en la habitación y de nuevo por el nº de días de la reserva

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

            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");

        }

        if(fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))){

            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        }


        this.fechaInicioReserva=fechaInicioReserva;
    }

    public LocalDate getFechaInicioReserva(){

        return fechaInicioReserva;
    }

    /*2.3-La fecha de fin de la reserva debe ser posterior a la de inicio.*/

    public void setFechaFinReserva(LocalDate fechaFinReserva) {

        if(fechaFinReserva==null){

            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        }

        //Añado la comprobación de que la fecha de fin de reserva no es igual a la de inicio de reserva porque lo requiere un test

        if(fechaFinReserva.isBefore(fechaInicioReserva) || fechaFinReserva.isEqual(fechaInicioReserva)){

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

        //Para que haya una reserva como mínimo debería haber 1 persona, por lo que puse numeroPersonas<1, pero el test requería que fuese <=0

        if(numeroPersonas<=0){

            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede ser menor o igual a 0.");
        }

        if(numeroPersonas>habitacion.getTipoHabitacion().getNumeroMaximoPersonas()){

            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede superar al máximo de personas establacidas para el tipo de habitación reservada.");
        }

        this.numeroPersonas=numeroPersonas;
    }

    public int getNumeroPersonas(){

        return numeroPersonas;
    }

    /*2.5-El checkin no puede ser anterior al inicio de la reserva.*/

    public void setCheckIn(LocalDateTime checkIn){

       if(checkIn==null) {

           throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");

       }

       //Con el método atStartofDay() establezco el inicio del día en las 00:00 am


        if(checkIn.isBefore(fechaInicioReserva.atStartOfDay())){

            throw new IllegalArgumentException("ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");
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

            throw new IllegalArgumentException("ERROR: El checkout de una reserva puede ser como máximo 12 horas después de la fecha de fin de la reserva.");
        }

        this.checkOut=checkOut;

    }

    public LocalDateTime getCheckOut(){

        return checkOut;
    }

    //5-Una reserva será igual a otra si se realiza sobre la misma habitación y en la misma fecha de inicio, basandome en ello creo el método equals y hashcode

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

    //6-Creo el método toString que devuelva la cadena que esperan los tests.

    @Override
    public String toString() {

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA);

        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA);

        //Verifica si la fecha de check in o check out es nula, si no es nula obtiene el formato de fecha y hora, si es nula, por defecto saldrá el mensaje "No registrado"

        String checkInString = getCheckIn() != null ? getCheckIn().format(formatoFechaHora) : "No registrado";

        String checkOutString = getCheckOut() != null ? getCheckOut().format(formatoFechaHora) : "No registrado";

        return String.format("Huesped: %s %s Habitación:%s - %s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s Checkout: %s Precio: %.2f Personas: %d",
                getHuesped().getNombre(), getHuesped().getDni(), getHabitacion().getIdentificador(),
                getHabitacion().getTipoHabitacion(), getFechaInicioReserva().format(formatoFecha),
                getFechaFinReserva().format(formatoFecha), checkInString, checkOutString, getPrecio(), getNumeroPersonas());
    }

    //7-Compruebo que la clase pasa los test para la misma y realizo un commit.
}
