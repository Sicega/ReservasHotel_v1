package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public class Habitacion {

    /*1-Creo la clase Habitación con los atributos y visibilidad adecuados
     * dentro del paquete dominio.*/

    public static final double MIN_PRECIO_HABITACION=40;
    public static final double MAX_PRECIO_HABITACION=150;
    public static final int MIN_NUMERO_PUERTA=0; //Pongo 0 en mínimo de puerta porque así lo establece el test
    public static final int MAX_NUMERO_PUERTA=14; //Pongo 14 de máximo en puerta ya que inicializa en 0 y son 15 puertas
    public static final int MIN_NUMERO_PLANTA=1;
    public static final int MAX_NUMERO_PLANTA=3;

    private String identificador;
    private int planta;
    private int puerta;
    private double precio;
    private TipoHabitacion tipoHabitacion;


    /*2-Creo los métodos de acceso y modificación con la visibilidad adecuada,
     * en el método setIdentificador concateno la planta y la puerta para que lo almacene
     * en la variable identificador*/

    // MÉTODOS CONSTRUCTORES

    /*3-Creo los métodos constructores con sus parámetros, haciendo uso de los métodos de modificación.*/

    public Habitacion (int planta, int puerta, double precio){

        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador(identificador); // Llamo al método para actualizar el identificador
        setTipoHabitacion(TipoHabitacion.SIMPLE); //Inicializo el tipo de habitación en cualquier tipo válido
    }

    public Habitacion (int planta, int puerta, double precio, TipoHabitacion tipoHabitacion){


        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setTipoHabitacion(tipoHabitacion);
        setIdentificador(identificador); // Llamo al método para actualizar el identificador

    }

    /*4-Creo el constructor copia.*/

    public Habitacion(Habitacion habitacion){

        if(habitacion==null){

            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }

        this.planta = habitacion.planta;
        this.puerta = habitacion.puerta;
        this.precio = habitacion.precio;
        this.tipoHabitacion = habitacion.tipoHabitacion;
        setIdentificador(); // Actualizo el identificador
    }

    // MÉTODOS GETTER Y SETTER

    public String getIdentificador() {

        return identificador;
    }

    private void setIdentificador(){


        if (planta <= 0 || puerta < 0) {

            throw new IllegalArgumentException("ERROR: La planta y la puerta deben ser mayores que cero.");
        }
        this.identificador = String.format("%d%d", this.planta, this.puerta);//Pongo el formato de identificador que requiere el test
    }

    private void setIdentificador(String identificador){


        if (identificador == null || identificador.isBlank()) {

            throw new IllegalArgumentException("ERROR: El identificador no puede ser nulo o vacío.");
        }

        this.identificador = identificador;


    }

    public int getPlanta(){

        return planta;
    }

    private void setPlanta(int planta){



        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {

            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");
        }

        this.planta = planta;

        try {

            setIdentificador();

        }catch (NullPointerException e){

            System.out.println("ERROR: La planta de una habitación no puede ser nula.");
        }


    }

    public int getPuerta(){

        return puerta;

    }

    private void setPuerta(int puerta){



        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA) {

            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 0 ni mayor que 14.");
        }

        this.puerta = puerta;

        setIdentificador();

    }

    public double getPrecio(){

        return precio;
    }

    public void setPrecio(double precio){

        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {

            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");

        } else {

            this.precio = precio;

        }

    }

    public TipoHabitacion getTipoHabitacion() {

        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {

        if (tipoHabitacion == null) {

            throw new NullPointerException("ERROR: No se puede establecer un tipo de habitación nula.");

        }

        if (tipoHabitacion != TipoHabitacion.SIMPLE && tipoHabitacion != TipoHabitacion.DOBLE &&
                tipoHabitacion != TipoHabitacion.SUITE && tipoHabitacion != TipoHabitacion.TRIPLE) {

            throw new IllegalArgumentException("ERROR: Tipo de habitación no válido.");
        }

        this.tipoHabitacion = tipoHabitacion;
    }


    /*5-Creo los métodos equals y hashCode teniendo en cuenta que una habitación será igual a otra si su identificador es el mismo.
     * Para hacer esta comparación importo java.util.Objects*/

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return planta == that.planta && puerta == that.puerta && Double.compare(precio, that.precio) == 0 && Objects.equals(identificador, that.identificador) && tipoHabitacion == that.tipoHabitacion;
    }

    @Override
    public int hashCode() {

        return Objects.hash(identificador, planta, puerta, precio, tipoHabitacion);
    }

    /*6-Creo el método toString que devuelve la cadena que esperan los tests.*/

    @Override
    public String toString() {

        return String.format("identificador=%s (%d-%d), precio habitación=%s, tipo habitación=%s",

                identificador, planta, puerta, precio, tipoHabitacion);
    }

    /*7-Compruebo que la clase pasa los tests y hago un commit.*/
}
