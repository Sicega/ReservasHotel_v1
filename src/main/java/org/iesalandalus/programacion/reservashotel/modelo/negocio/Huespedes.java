package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;

public class Huespedes {

    /*1-Crea la clase Huespedes, en el paquete adecuado, con los atributos
     y métodos especificados en el diagrama y la visibilidad adecuada.*/

    private int capacidad;
    private int tamano;
    private Huesped [] coleccionHuespedes;

    /*1.1-Crea el constructor con parámetros que creará una lista de la capacidad indicada en el
     parámetro e inicializará los atributos de la clase a los valores correspondientes.*/

    public Huespedes(int capacidad){

        if(capacidad<=0){

            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad=capacidad;

        this.tamano=0;

        coleccionHuespedes = new Huesped[capacidad];

    }

    /*1.2-El método get devolverá una copia profunda de
    la colección haciendo uso del método copiaProfundaHuespedes.*/

    public Huesped [] get(){

        return copiaProfundaHuespedes();
    }

    private Huesped [] copiaProfundaHuespedes(){

        Huesped [] miHuesped = new Huesped[getCapacidad()];

        int indice=0;

        for (Huesped huesped : coleccionHuespedes){

            miHuesped [indice]= new Huesped(huesped);

            indice++;
        }

        return miHuesped;
    }

    public int getCapacidad() {

        return capacidad;
    }

    public int getTamano() {

        return tamano;
    }

    /*1.3-Se permitirán insertar huéspedes no
    nulos al final de la colección sin admitir repetidos.*/

    public void insertar (Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {

            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }

        // Compruebo si el huésped ya existe en la colección

        if (buscar(huesped) != null) {

            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }

        if(capacidad<0){

            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        if (tamano >= capacidad) {

            throw new OperationNotSupportedException ("ERROR: No se aceptan más huéspedes.");
        }

        // Agrego el huésped al final de la colección

        coleccionHuespedes[getTamano()] = new Huesped(huesped);

        tamano++;

    }

    private int buscarIndice(Huesped huesped)  {

        if(huesped==null){

            throw new NullPointerException("huesped null buscarindice");

        }

        for (int i = 0; i < tamano; i++) {

            if (coleccionHuespedes[i].equals(huesped)) {

                return i;
            }
        }

        return -1;
    }

    private boolean tamanoSuperado(int indice) {

        return indice > tamano;
    }

    private boolean capacidadSuperada(int indice) {

        return indice >= capacidad;
    }

    /*1.4-El método buscar devolverá un huésped si éste
    se encuentra en la colección y null en caso contrario.*/

    public Huesped buscar(Huesped huesped) {

        if(huesped==null){

            throw new NullPointerException("huesped buscar null");
        }

        int indice = buscarIndice(huesped);

        return (indice != -1) ? coleccionHuespedes[indice] : null;
    }

    /*1.5-El método borrar, si el huésped se encuentra en la colección, lo borrará y desplazará
    los elementos hacia la izquierda para dejar el array compactado.*/

    public void borrar (Huesped huesped) throws OperationNotSupportedException {

        if(huesped==null){

            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        int indice = buscarIndice(huesped);

        if(indice == -1){

            throw new  OperationNotSupportedException ("ERROR: No existe ningún huésped como el indicado.");
        }


        desplazarUnaPosicionHaciaIzquierda(indice);

        tamano--;


    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {

        if(indice<0 || capacidadSuperada(indice)){

            throw new IllegalArgumentException("desplazar izquierda out of bounds");
        }

        for (int i = indice; i < tamano - 1; i++) {

            coleccionHuespedes[i] = coleccionHuespedes[i + 1];
        }
        // Garantiza que el último elemento se establece en null

        coleccionHuespedes[tamano - 1] = null;
    }
}
