package org.iesalandalus.programacion.reservashotel.dominio;

/**1-Creo la clase tipo enum en el paquete dominio y le establezco valores a los argumentos del enumerado*/

public enum TipoHabitacion {

    //Paso como parámetros los atributos establecidos en el método constructor

    SUITE("Suite",4),
    SIMPLE("Simple",1),
    DOBLE("Doble",2),
    TRIPLE("Triple",3);

    /**2-Creo el atributo privado de tipo String cadenaAMostrar. */

    private String cadenaAMostrar; //ambos atributos son de visibilidad privada
    private int numeroMaximoPersonas;

    /**3-Creo el constructor privado con el mismo nombre que la clase le paso por parámetros los atributos declarados.*/

    private TipoHabitacion(String cadenaAMostrar, int numeroMaximoPersonas){

        this.cadenaAMostrar=cadenaAMostrar;
        this.numeroMaximoPersonas=numeroMaximoPersonas;
    }

    /**4- Creo el método público getter que devuelve el número máximo de personas que pueden alojarse en según qué tipo de habitación*/
    public int getNumeroMaximoPersonas() {

        return numeroMaximoPersonas;
    }

    /**5-Creo el método toString que devuelve la cadena que esperan los tests.*/

    @Override
    public String toString() {

        return cadenaAMostrar;
    }

    /**6- Tras pasar los tests correctamente hago commit and push*/

}
