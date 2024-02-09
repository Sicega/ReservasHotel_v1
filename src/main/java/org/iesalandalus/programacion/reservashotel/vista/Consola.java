package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Regimen;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consola {

    private Consola(){

    }

    public static void mostrarMenu() {

        for (Opcion opcion : Opcion.values()) {

            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {

        int opcionElegida;

        do{
            System.out.print("Elige una opción: ");

            opcionElegida = Entrada.entero();

        }while(opcionElegida<0 || opcionElegida > Opcion.values().length);

        return Opcion.values()[opcionElegida];

    }

    public static Huesped leerHuesped() {

        String nombre;

        do{
            System.out.print("Introduce el nombre del huésped: ");

            nombre = Entrada.cadena();

        }while(nombre == null || nombre.isBlank());

        //todo hacer el do while con todo

        System.out.print("Introduce el DNI del huésped: ");

        String dni = Entrada.cadena();

        System.out.print("Introduce el correo del huésped: ");

        String correo = Entrada.cadena();

        System.out.print("Introduce el teléfono del huésped: ");

        String telefono = Entrada.cadena();

        System.out.print("Introduce la fecha de nacimiento del huésped ddMMyyyy: ");


        LocalDate fechaNacimiento = Consola.leerFecha();

        return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
    }

    public static Huesped leerHuespedPorDni() {

        System.out.print("Introduce el DNI del huésped: ");

        String dni = Entrada.cadena();

        return new Huesped("nombre", dni, "correo@gmail.com", "623456789", LocalDate.of(2000,4,4));
    }

    public static LocalDate leerFecha() {

        String fecha = null;

        boolean fechaValida = false;

        while (!fechaValida) {

            System.out.print("Introduce la fecha (formato dd/mm/yyyy): ");

            fecha = Entrada.cadena();



            if (fecha.matches("[0-3][0-9]/[0-1][0-9]/[1-2][0-9]{3}"))
                fechaValida = true;

        }

        DateTimeFormatter formato= DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);

        LocalDate fechaFormato=LocalDate.parse(fecha, formato);

        return fechaFormato;
    }

    public static Habitacion leerHabitacion() {

        //todo pon los do while, poner las constantes de los valores min y max

        System.out.print("Introduce el número de planta de la habitación: ");

        int planta = Entrada.entero();

        System.out.print("Introduce el número de puerta de la habitación: ");

        int puerta = Entrada.entero();

        System.out.print("Introduce el precio de la habitación: ");

        double precio = Entrada.realDoble();

        System.out.println("Introduce el tipo de habitacion: ");

        TipoHabitacion tipoHabitacion = leerTipoHabitacion();

        return new Habitacion(planta, puerta, precio, tipoHabitacion);
    }

    public static Habitacion leerHabitacionPorIdentificador() {

        System.out.print("Introduce el identificador de la habitación: ");

        int planta = Entrada.entero();
        int puerta = Entrada.entero();


        try {

            return new Habitacion(planta,puerta,50);

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

            return null;
        }
    }

    public static TipoHabitacion leerTipoHabitacion() {

        System.out.println("Tipos de habitación:");

        for (TipoHabitacion tipo : TipoHabitacion.values()) {

            System.out.println(tipo);

        }

        System.out.print("Elige un tipo de habitación: ");

        int tipoElegido;

        do {

            tipoElegido = Entrada.entero();

        }while(tipoElegido <0 || tipoElegido >= TipoHabitacion.values().length);

        return TipoHabitacion.values()[tipoElegido];
    }

    public static Regimen leerRegimen() {

        System.out.println("Tipos de régimen:");

        for (Regimen regimen : Regimen.values()) {

            System.out.println(regimen);
        }

        System.out.print("Elige un tipo de régimen: ");

        //todo hacer el do while
        int regimenElegido = Entrada.entero();

        return Regimen.values()[regimenElegido];
    }

    public static int leerNumeroPersonas() {

        int numeroPersonas;


        do {
            System.out.print("Introduce el número de personas: ");

            numeroPersonas = Entrada.entero();

        }while(numeroPersonas<=0);

        return numeroPersonas;
    }


    public static Reserva leerReserva() {

        System.out.println("Introduce los datos de la reserva:");

        Huesped huesped = leerHuesped();
        Habitacion habitacion = leerHabitacion();
        Regimen regimen=leerRegimen();
        LocalDate fechaInicioReserva = leerFecha();
        LocalDate fechaFinReserva = leerFecha();
        int numeroPersonas= leerNumeroPersonas();

        return new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, numeroPersonas);
    }

    public static LocalDateTime leerFechaHora(String mensaje) {

        // Variable para almacenar la entrada del usuario (fecha y hora)

        String fechaHora = null;

        // Bucle do-while para asegurar que se solicita la entrada al menos una vez y se repite si la entrada no es válida

        do {
            System.out.print(mensaje);

            fechaHora = Entrada.cadena();

            // Para verificar si la entrada coincide con el formato esperado

            if (!fechaHora.matches(Reserva.FORMATO_FECHA_HORA_RESERVA)) {
                System.out.println("ERROR: Formato de fecha y hora incorrecto.");
            }
        } while (!fechaHora.matches(Reserva.FORMATO_FECHA_HORA_RESERVA));

        // Cre0 un formateador de fecha y hora con el formato de la constante

        DateTimeFormatter formato = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA);

        return LocalDateTime.parse(fechaHora, formato); // Convierto la cadena de fecha y hora a un objeto LocalDateTime utilizando el formateador
    }

}
