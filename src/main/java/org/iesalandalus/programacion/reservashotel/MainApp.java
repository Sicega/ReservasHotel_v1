package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.dominio.Regimen;
import org.iesalandalus.programacion.reservashotel.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.negocio.Reservas;
import org.iesalandalus.programacion.reservashotel.vista.Opcion;
import org.iesalandalus.programacion.reservashotel.vista.Consola;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MainApp {

    public static final int CAPACIDAD = 10;
    private static Reservas reservas;
    private static Huespedes huespedes;
    private static Habitaciones habitaciones;


    private static void ejecutarOpcion(Opcion opcion){

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
            case SALIR:
                break;
            default:
                System.out.println("Opción no válida.");
        }

    }

    private static void insertarHuesped(){

        Huesped nuevoHuesped = Consola.leerHuesped();
        try {

            huespedes.insertar(nuevoHuesped);


            System.out.println("Huésped insertado correctamente.");
        }
        catch (IllegalArgumentException|NullPointerException|OperationNotSupportedException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void buscarHuesped(){

        Huesped huesped = Consola.leerHuespedPorDni();

        try {
            Huesped huespedEncontrado = huespedes.buscar(huesped);

            System.out.println(huespedEncontrado);

        }catch (IllegalArgumentException|NullPointerException e){

            System.out.println(e.getMessage());
            System.out.println("No se encontró al huésped con DNI " + huesped.getDni());

        }


    }

    private static void borrarHuesped(){

        Huesped huesped = Consola.leerHuespedPorDni();

        try {

            huespedes.borrar(huesped);

            System.out.println("Huésped borrado correctamente.");

        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void mostrarHuespedes(){


        Huesped[] listaHuespedes = huespedes.get();

        if (listaHuespedes.length > 0) {



            for (Huesped huesped : listaHuespedes) {

                System.out.println(huesped);
            }

        } else {

            System.out.println("No hay huéspedes registrados.");
        }

    }

    private static void insertarHabitacion(){

        Habitacion nuevaHabitacion = Consola.leerHabitacion();

        try {

            habitaciones.insertar(nuevaHabitacion);

            System.out.println("Habitación insertada correctamente.");

        } catch (IllegalArgumentException|OperationNotSupportedException|NullPointerException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void buscarHabitacion(){



        Habitacion habitacionEncontrada = Consola.leerHabitacionPorIdentificador();

        if (habitacionEncontrada != null) {

            System.out.println(habitacionEncontrada);

        } else {

            System.out.println("No se encontró la habitación.");
        }

    }

    private static void borrarHabitacion(){

        try {

            habitaciones.borrar(Consola.leerHabitacionPorIdentificador());

            System.out.println("Habitación borrada correctamente.");

        } catch (IllegalArgumentException|NullPointerException|OperationNotSupportedException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void mostrarHabitaciones(){

        Habitacion[] listaHabitaciones = habitaciones.get();

        if (listaHabitaciones.length > 0) {

            for (Habitacion habitacion : listaHabitaciones) {

                System.out.println(habitacion);
            }
        } else {

            System.out.println("No hay habitaciones registradas.");
        }

    }

    private static void insertarReserva(){

        Reserva nuevaReserva = Consola.leerReserva();

        try {

            reservas.insertar(nuevaReserva);

            System.out.println("Reserva insertada correctamente.");

        } catch (IllegalArgumentException | OperationNotSupportedException|NullPointerException e) {

            System.out.println(e.getMessage());
        }

    }

    private static void listarReservas(Huesped huesped){

        Reserva[] reservasHuesped = reservas.getReservas(huesped);

        if (reservasHuesped.length > 0) {

            for (Reserva reserva : reservasHuesped) {

                System.out.println(reserva);
            }
        } else {

            System.out.println("El huésped no tiene reservas.");
        }

    }

    private static void listarReservas(TipoHabitacion tipoHabitacion){


        Reserva[] reservasTipoHabitacion = reservas.getReservas(tipoHabitacion);

        if (reservasTipoHabitacion.length > 0) {

            for (Reserva reserva : reservasTipoHabitacion) {

                System.out.println(reserva);
            }
        } else {

            System.out.println("No hay reservas para el tipo de habitación " + tipoHabitacion);
        }

    }

    private static Reserva [] getReservasAnulables( Reserva [] reservasAnular) {

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

    private static void anularReserva(){


        Huesped huesped = Consola.leerHuespedPorDni();

        Reserva[] reservasAnulables = reservas.getReservas(huesped);
        reservasAnulables = getReservasAnulables(reservasAnulables);

        if (reservasAnulables.length == 0) {
            System.out.println("No hay reservas para anular.");
        } else if (reservasAnulables.length == 1) {

            System.out.println("¿Confirma la anulación de la reserva?" + reservasAnulables[0]);

            if (Entrada.cadena().equalsIgnoreCase("si")){
                try {

                    reservas.borrar(reservasAnulables[0]);
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

                reservas.borrar(reservasAnulables[indiceReserva]);

                System.out.println("Reserva anulada correctamente.");

            } catch (IllegalArgumentException | OperationNotSupportedException| NullPointerException e) {

                System.out.println(e.getMessage());
            }
        }

    }

    private static void mostrarReservas(){

        Reserva [] listaReservas = reservas.get();
        if (listaReservas.length > 0) {
            for (Reserva reserva : listaReservas) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay reservas registradas.");
        }

    }

    private static int getNumElementosNoNulos(Reserva [] reservas){

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

        Habitacion[] habitacionesTipoSolicitado= habitaciones.get(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado[i]!=null)
            {
                Reserva[] reservasFuturas = reservas.getReservasFuturas(habitacionesTipoSolicitado[i]);
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

    public static void main(String[] args) {

        reservas=new Reservas(CAPACIDAD);
        huespedes= new Huespedes(CAPACIDAD);
        habitaciones= new Habitaciones(CAPACIDAD);


        Opcion opcion;

        do {
            Consola.mostrarMenu();

            opcion = Consola.elegirOpcion();

            ejecutarOpcion(opcion);

        } while (opcion != Opcion.SALIR);

        System.out.println("Hasta luego!!!!");

    }

}
