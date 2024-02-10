package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import java.util.Arrays;

import javax.naming.OperationNotSupportedException;

public class Habitaciones {

    private int capacidad;
    private int tamano;
    private Habitacion [] coleccionHabitaciones;

    //MÉTODOS

    public Habitaciones(int capacidad){

        if(capacidad<=0){ // Validación de la capacidad

            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        // Inicialización de los atributos

        this.capacidad=capacidad;

        this.tamano=0;

        coleccionHabitaciones = new Habitacion [capacidad];
    }

    public Habitacion [] get(){ //Método que devuelve la copia profunda de las habitaciones

        return copiaProfundaHabitaciones();
    }

    private Habitacion [] copiaProfundaHabitaciones(){ //Método que realiza la copia profunda de las habitaciones

        Habitacion[] miHabitacion = new Habitacion[getCapacidad()];

        int indice = 0;

        for (Habitacion habitacion : coleccionHabitaciones) { // Itero sobre la colección de habitaciones

            if (habitacion != null) {

                miHabitacion[indice] = new Habitacion(habitacion); // Creao una copia profunda de la habitación y lo almaceno en el nuevo array

                indice++; //Se actualiza el índice
            }
        }

        return Arrays.copyOf(miHabitacion, indice); // Devuelve un nuevo array con las habitaciones copiadas
    }

    // Método para obtener las habitaciones de un tipo (Simple, doble, etc...)

    public Habitacion[] get(TipoHabitacion tipoHabitacion) {

        Habitacion[] habitacionesTipo = new Habitacion[capacidad];

        int indice = 0;


        for (int i = 0; i < tamano; i++) { //Recorro la colección de habitaciones

            if (coleccionHabitaciones[i].getTipoHabitacion() == tipoHabitacion) {

                habitacionesTipo[indice] = new Habitacion(coleccionHabitaciones[i]); //Creo una copia profunda de la habitacion

                indice++;
            }
        }

        return Arrays.copyOf(habitacionesTipo, indice); //Devuelvo un nuevo array con la copia de las habitaciones por tipo
    }

    public int getTamano() { // Método para obtener el tamaño actual de la colección

        return tamano;
    }

    public int getCapacidad() { // Método para obtener la capacidad de la colección

        return capacidad;
    }

    // Método para insertar una nueva habitación

    public void insertar (Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) { //Verifico que la habitación no sea nula

            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }

        // Compruebo si la habitación ya existe en la colección

        if (buscar(habitacion) != null) {

            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }


        if (tamano >= capacidad) {  // Compruebo si se ha alcanzado la capacidad máxima

            throw new OperationNotSupportedException ("ERROR: No se aceptan más habitaciones.");
        }

        // Agrego la habitación al final de la colección

        coleccionHabitaciones[getTamano()] = new Habitacion(habitacion);

        tamano++; //El incremental actualiza el tamaño
    }

    private int buscarIndice(Habitacion habitacion)  { // Método privado para buscar el índice de una habitación


        for (int i = 0; i < tamano; i++) {

            if (coleccionHabitaciones[i].equals(habitacion)) {

                return i;
            }
        }
        return -1;
    }

    private boolean tamanoSuperado(int indice) { // Método para verificar si un índice está fuera del tamaño actual

        return indice >= tamano;
    }

    private boolean capacidadSuperada(int indice) { // Método para verificar si un índice está fuera de la capacidad

        return indice >= capacidad;
    }

    public Habitacion buscar(Habitacion habitacion) { // Método para buscar una habitación en la colección
        if (habitacion==null){

            throw new NullPointerException("error nulo");
        }
        int indice = buscarIndice(habitacion);   //Busca el índice de la habitación

        return (indice != -1) ? coleccionHabitaciones[indice] : null; // Devuelve la habitación encontrada o null si no se encontró
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException { // Método para borrar una habitación de la colección

        if(habitacion==null){ //Verifico que la habitación no sea nula

            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        }

        int indice = buscarIndice(habitacion); //Busca el índice de la habitación

        if(indice == -1){ //Comprueba si se encontró la habitación

            throw new  OperationNotSupportedException ("ERROR: No existe ninguna habitación como la indicada.");
        }



        desplazarUnaPosicionHaciaIzquierda(indice); // Desplaza las habitaciones una posición a la izquierda

        tamano--; // Decrementa el tamaño de la colección


    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) { // Método para desplazar las habitaciones una posición a la izquierda

        if(indice<0 || capacidadSuperada(indice)){ // Valida que el índice no sea negativo y no esté fuera de la capacidad

            throw new IllegalArgumentException("desplazarunaPosicion no puede ser menor que cero");
        }

        for (int i = indice; i < tamano - 1; i++) { // Itera desde el índice hasta el tamaño - 1

            coleccionHabitaciones[i] = coleccionHabitaciones[i + 1];  // Desplaza la habitación actual a la posición anterior
        }
        // Garantiza que el último elemento se establece en null para evitar duplicados

        coleccionHabitaciones[tamano - 1] = null;
    }

}
