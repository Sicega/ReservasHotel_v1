package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consola {

    private Consola(){

    }

    public static void mostrarMenu() { // Método estático para mostrar el menú de opciones

        for (Opcion opcion : Opcion.values()) { //con values devuelvo un array que contiene las constantes del enumerado en el orden declarado

            System.out.println(opcion); //Saca por consola las opciones del enumerado
        }
    }

    public static Opcion elegirOpcion() {

        int opcionElegida; //Variable para almacenar el número de la opción escogida

        do{
            System.out.print("Elige una opción: ");

            opcionElegida = Entrada.entero();

        }while(opcionElegida<0 || opcionElegida > Opcion.values().length); //Verifico que la opción escogida esté dentro del enumerado

        return Opcion.values()[opcionElegida];

    }

    public static Huesped leerHuesped() { //Método para introducir los datos del huésped

        String nombre;

        do{ //Verifico que no sea nulo ni contenga solo espacios
            System.out.print("Introduce el nombre del huésped: ");

            nombre = Entrada.cadena();

        }while(nombre == null || nombre.isBlank());

        String dni;
        String ER_DNI = "([0-9]{8})([A-Za-z])";

        do {

            System.out.print("Introduce el DNI del huésped: ");

            dni = Entrada.cadena();

        }while(dni == null || dni.isBlank() || !dni.matches(ER_DNI));

        String correo;
        String ER_CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$";

        do {

            System.out.print("Introduce el correo del huésped: ");

            correo = Entrada.cadena();

        }while(correo == null || correo.isBlank() || !correo.matches(ER_CORREO));

        String telefono;
        String ER_TELEFONO = "[0-9]{9}";

        do {
            System.out.print("Introduce el teléfono del huésped: ");

            telefono = Entrada.cadena();

        }while(telefono==null || telefono.isBlank() || !telefono.matches(ER_TELEFONO));


        System.out.print("Introduce la fecha de nacimiento del huésped: ");

        LocalDate fechaNacimiento = Consola.leerFecha();

        return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
    }

    public static Huesped leerHuespedPorDni() {

        System.out.print("Introduce el DNI del huésped: ");

        String dni = Entrada.cadena();

        return new Huesped("nombre", dni, "correo@gmail.com", "623456789", LocalDate.of(2000,4,4));
    }

    public static LocalDate leerFecha() { //Método para validar la fecha introducida

        String fecha = null;

        boolean fechaValida = false;

        while (!fechaValida) {

            System.out.print("Formato dd/MM/yyyy: ");

            fecha = Entrada.cadena();



            if (fecha.matches("[0-3][0-9]/[0-1][0-9]/[1-2][0-9]{3}"))
                fechaValida = true;

        }

        DateTimeFormatter formato= DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);

        LocalDate fechaFormato=LocalDate.parse(fecha, formato);

        return fechaFormato;
    }

    public static Habitacion leerHabitacion() {

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

        System.out.print("Introduce la planta de la habitación: ");

        int planta = Entrada.entero();

        System.out.println("Introduce la puerta de la habitación: ");

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

            System.out.println(tipo); // Imprime los tipos de habitación disponibles

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

            System.out.println(regimen); // Imprime los tipos de régimen disponibles
        }

        System.out.print("Elige un tipo de régimen: ");


        int regimenElegido = Entrada.entero();

        return Regimen.values()[regimenElegido];
    }

    public static int leerNumeroPersonas() {

        int numeroPersonas;

        // Bucle do-while para garantizar un número de personas válido

        do {
            System.out.print("Introduce el número de personas: ");

            numeroPersonas = Entrada.entero();

        }while(numeroPersonas<=0);

        return numeroPersonas;
    }


    public static Reserva leerReserva() {

        System.out.println("Introduce los datos de la reserva:");

        // Llama a los métodos de la clase Consola para obtener los objetos Huesped, Habitacion, Regimen, etc.

        Huesped huesped = leerHuesped();
        Habitacion habitacion = leerHabitacion();
        Regimen regimen=leerRegimen();
        LocalDate fechaInicioReserva = leerFecha();
        LocalDate fechaFinReserva = leerFecha();
        int numeroPersonas= leerNumeroPersonas();

        // Crea y devuelve un nuevo objeto Reserva con los datos introducidos

        return new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, numeroPersonas);
    }

    public static LocalDateTime leerFechaHora(String mensaje) {

        // Bucle while para asegurar que se solicita la entrada al menos una vez y se repite si la entrada no es válida

        while (!mensaje.matches(Reserva.FORMATO_FECHA_HORA_RESERVA)) {
            System.out.print(mensaje);
            System.out.println("No es un patrón válido. Inténtalo de nuevo. (dd/MM/yyyy hh:mm:ss)");

            mensaje = Entrada.cadena();


        }

        // Creo un formateador de fecha y hora con el formato de la constante

        DateTimeFormatter formato = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA);

        return LocalDateTime.parse(mensaje, formato); // Convierto la cadena de fecha y hora a un objeto LocalDateTime utilizando el formateador
    }

}
