package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class Vista {


    private static Controlador controlador;


    public void setControlador(Controlador controlador) {

        if (controlador != null) {

            this.controlador = controlador;
        }
    }

    public void comenzar(){ // Inicia la ejecución de la vista

        Opcion opcion;

        do {
            Consola.mostrarMenu(); // Muestra el menú en la consola

            opcion = Consola.elegirOpcion(); // Lee la opción elegida por el usuario

            ejecutarOpcion(opcion); // Ejecuta la opción seleccionada

        } while (opcion != Opcion.SALIR);

    }

    public void terminar() { // Finaliza la ejecución de la vista

        System.out.println("¡Hasta luego!"); //Mensaje de salida de despedida
    }


//MÉTODOS MOVIDOS DEL MAINAPP

    private static void ejecutarOpcion(Opcion opcion){ // Ejecuta la opción seleccionada

        switch (opcion) {
            case INSERTAR_HUESPED:
                insertarHuesped();
                break;
            case BUSCAR_HUESPED:
                buscarHuesped();
                break;
            case BORRAR_HUESPED:
                borrarHuesped();
                break;
            case MOSTRAR_HUESPEDES:
                mostrarHuespedes();
                break;
            case INSERTAR_HABITACION:
                insertarHabitacion();
                break;
            case BUSCAR_HABITACION:
                buscarHabitacion();
                break;
            case BORRAR_HABITACION:
                borrarHabitacion();
                break;
            case MOSTRAR_HABITACIONES:
                mostrarHabitaciones();
                break;
            case INSERTAR_RESERVA:
                insertarReserva();
                break;
            case ANULAR_RESERVA:
                anularReserva();
                break;
            case MOSTRAR_RESERVAS:
                mostrarReservas();
                break;
            case CONSULTAR_DISPONIBILIDAD:
                consultarDisponibilidad(null,null,null);
                break;
            case REALIZAR_CHECKIN: //Añado realizar checkIn
                realizarCheckin();
                break;
            case REALIZAR_CHECKOUT: //Añado realizar checkOut
                realizarCheckout();
                break;
            case SALIR:
                break;
            default:
                System.out.println("Opción no válida.");
        }

    }

    private static void insertarHuesped(){ // Inserta un nuevo huésped



        Huesped nuevoHuesped = Consola.leerHuesped();

        try {

            controlador.insertar(nuevoHuesped);


            System.out.println("Huésped insertado correctamente.");
        }
        catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void buscarHuesped(){ // Busca un huésped por DNI

        Huesped huesped = Consola.leerHuespedPorDni();

        try {
            Huesped huespedEncontrado = controlador.buscar(huesped);

            System.out.println(huespedEncontrado);

        }catch (IllegalArgumentException|NullPointerException e){

            System.out.println(e.getMessage());

            System.out.println("No se encontró al huésped con DNI " + huesped.getDni());

        }


    }

    private static void borrarHuesped(){ // Borra un huésped por DNI

        Huesped huesped = Consola.leerHuespedPorDni();

        try {

            controlador.borrar(huesped);

            System.out.println("Huésped borrado correctamente.");

        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void mostrarHuespedes(){ // Muestra todos los huéspedes


        Huesped[] listaHuespedes = controlador.getHuespedes();

        if (listaHuespedes.length > 0) {



            for (Huesped huesped : listaHuespedes) {

                System.out.println(huesped);
            }

        } else {

            System.out.println("No hay huéspedes registrados.");
        }

    }

    private static void insertarHabitacion(){ // Inserta una nueva habitación

        Habitacion nuevaHabitacion = Consola.leerHabitacion();

        try {

            controlador.insertar(nuevaHabitacion);

            System.out.println("Habitación insertada correctamente.");

        } catch (IllegalArgumentException|OperationNotSupportedException|NullPointerException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void buscarHabitacion(){ // Busca una habitación por identificador

        Habitacion habitacionEncontrada = Consola.leerHabitacionPorIdentificador();

        if (habitacionEncontrada != null) {

            System.out.println(habitacionEncontrada);

        } else {

            System.out.println("No se encontró la habitación.");
        }

    }

    private static void borrarHabitacion(){ // Borra una habitación por identificador

        try {

            controlador.borrar(Consola.leerHabitacionPorIdentificador());

            System.out.println("Habitación borrada correctamente.");

        } catch (IllegalArgumentException|NullPointerException|OperationNotSupportedException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void mostrarHabitaciones(){ // Muestra todas las habitaciones

        Habitacion[] listaHabitaciones = controlador.getHabitaciones();

        if (listaHabitaciones.length > 0) {

            for (Habitacion habitacion : listaHabitaciones) {

                System.out.println(habitacion);
            }
        } else {

            System.out.println("No hay habitaciones registradas.");
        }

    }

    private static void insertarReserva(){ // Inserta una nueva reserva

        Reserva nuevaReserva = Consola.leerReserva();

        try {

            controlador.insertar(nuevaReserva);

            System.out.println("Reserva insertada correctamente.");

        } catch (IllegalArgumentException | OperationNotSupportedException|NullPointerException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void listarReservas(Huesped huesped){ // Lista las reservas de un huésped

        Reserva[] reservasHuesped =  controlador.getReservas(huesped);

        if (reservasHuesped.length > 0) {

            for (Reserva reserva : reservasHuesped) {

                System.out.println(reserva);
            }
        } else {

            System.out.println("El huésped no tiene reservas.");
        }

    }

    private static void listarReservas(TipoHabitacion tipoHabitacion){ // Lista las reservas de un tipo de habitación


        Reserva[] reservasTipoHabitacion = controlador.getReservas(tipoHabitacion);

        if (reservasTipoHabitacion.length > 0) {

            for (Reserva reserva : reservasTipoHabitacion) {

                System.out.println(reserva);
            }
        } else {

            System.out.println("No hay reservas para el tipo de habitación " + tipoHabitacion);
        }

    }

    private static Reserva [] getReservasAnulables( Reserva [] reservasAnular) { // Obtiene un array de reservas anulables

        Reserva [] arrayNuevo= new Reserva[reservasAnular.length];

        int j=0;

        for(int i=0; i<reservasAnular.length;i++){

            if(reservasAnular[i].getFechaInicioReserva().isAfter(LocalDate.now())){

                arrayNuevo[j]= new Reserva(reservasAnular[i]);

                j++;
            }
        }

        return arrayNuevo;
    }

    private static void anularReserva(){ // Anula una reserva comprobando antes que esa reserva existe


        Huesped huesped = Consola.leerHuespedPorDni();

        Reserva[] reservasAnulables = controlador.getReservas(huesped);

        reservasAnulables = getReservasAnulables(reservasAnulables);

        if (reservasAnulables.length == 0) {

            System.out.println("No hay reservas para anular.");

        } else if (reservasAnulables.length == 1) {

            System.out.println("¿Confirma la anulación de la reserva?" + reservasAnulables[0]);

            if (Entrada.cadena().equalsIgnoreCase("si")){

                try {

                    controlador.borrar(reservasAnulables[0]);
                    System.out.println("Reserva anulada correctamente.");

                } catch (IllegalArgumentException | OperationNotSupportedException|NullPointerException e) {

                    System.out.println(e.getMessage());
                }

            } else {

                System.out.println("Anulación cancelada.");

            }
        } else {

            int contador=0;

            for(Reserva elemento : reservasAnulables){

                System.out.println(contador+ " : " + elemento);

                contador++;

            }

            int indiceReserva;

            do {

                System.out.println("¿Qué reserva desea anular?");

                indiceReserva = Entrada.entero();

            }while(indiceReserva<0 || indiceReserva > reservasAnulables.length);


            try {

                controlador.borrar(reservasAnulables[indiceReserva]);

                System.out.println("Reserva anulada correctamente.");

            } catch (IllegalArgumentException | OperationNotSupportedException| NullPointerException e) {

                System.out.println(e.getMessage());
            }
        }

    }

    private static void mostrarReservas(){ // Muestra todas las reservas

        Reserva [] listaReservas = controlador.getReservas();
        if (listaReservas.length > 0) {
            for (Reserva reserva : listaReservas) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay reservas registradas.");
        }

    }

    private static int getNumElementosNoNulos(Reserva [] reservas){

        // Obtiene el número de elementos no nulos en un array de reservas

        int contador=0;

        for(Reserva elemento : reservas){

            if(elemento !=null){

                contador++;
            }
        }
        return contador;
    }

    private static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        Habitacion[] habitacionesTipoSolicitado= controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado[i]!=null)
            {
                Reserva[] reservasFuturas = controlador.getReservasFuturas(habitacionesTipoSolicitado[i]);
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que está disponible.

                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado[i]);

                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                    Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/

                    if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())) {

                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);

                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                        Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                        if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas están algún hueco existente entre las fechas reservadas

                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.length && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas[j]!=null && reservasFuturas[j-1]!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas[j-1].getFechaFinReserva()) &&

                                        fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva())) {

                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);

                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                }
            }
        }

        return habitacionDisponible;
    }

    private static void realizarCheckin() { // Realiza el checkIn de un huésped comprobado que coincide con una reserva

        System.out.println("Introduce el DNI del huésped:");

        Huesped huesped = Consola.leerHuespedPorDni();

        System.out.println("Introduce la fecha (dd/MM/yyyy) y la hora (hh:mm:ss) del checkin:");

        LocalDateTime fechaCheckin = Consola.leerFechaHora(Entrada.cadena());

        Reserva[] reservasHuesped = controlador.getReservas(huesped);

        if (reservasHuesped.length == 0) { //Verifica si el huesped tiene reservas

            System.out.println("El huésped no tiene reservas.");

            return;
        }

        System.out.println("Reservas del huésped:");

        for (int i = 0; i < reservasHuesped.length; i++) {

            System.out.println(i + ": " + reservasHuesped[i]);
        }

        int indiceReserva;

        do {
            System.out.println("¿Qué reserva desea hacer checkin?");

            indiceReserva = Entrada.entero();

        } while (indiceReserva < 0 || indiceReserva >= reservasHuesped.length);

        try {
            controlador.realizarCheckIn(controlador.buscar(reservasHuesped[indiceReserva]), fechaCheckin);

            System.out.println("Checkin realizado correctamente.");

        } catch (IllegalArgumentException | NullPointerException e) {

            System.out.println(e.getMessage());
        }
    }

    private static void realizarCheckout() {

        System.out.println("Introduce el DNI del huésped:");

        Huesped huesped = Consola.leerHuespedPorDni();

        System.out.println("Introduce la fecha (dd/MM/yyyy) y la hora (hh:mm:ss) del checkout:");

        LocalDateTime fechaCheckout = Consola.leerFechaHora(Entrada.cadena());

        Reserva[] reservasHuesped = controlador.getReservas(huesped);

        if (reservasHuesped.length == 0) { // Verifica si el huésped tiene reservas

            System.out.println("El huésped no tiene reservas.");

            return;
        }

        System.out.println("Reservas del huésped:");

        for (int i = 0; i < reservasHuesped.length; i++) {

            System.out.println(i + ": " + reservasHuesped[i]); // Muestra todas las reservas del huésped
        }

        int indiceReserva;

        do {

            System.out.println("¿Qué reserva desea hacer checkout?"); //Salida de consola para que el usuario elija una reserva

            indiceReserva = Entrada.entero();

        } while (indiceReserva < 0 || indiceReserva >= reservasHuesped.length);

        try { // Realiza el checkout utilizando el controlador

            controlador.realizarCheckOut(controlador.buscar(reservasHuesped[indiceReserva]), fechaCheckout);

            System.out.println("Checkout realizado correctamente.");

        } catch (IllegalArgumentException | NullPointerException e) {

            System.out.println(e.getMessage());
        }
    }

}
