package org.iesalandalus.programacion.reservashotel.dominio;

public enum Regimen {

    //1-Creo el enumerado Regimen con cada tipo de regimen y su incremento de precio

    SOLO_ALOJAMIENTO("Solo Alojamiento",0),
    ALOJAMIENTO_DESAYUNO("Alojamiento y Desayuno", 15),
    MEDIA_PENSION("Media Pensión", 30),
    PENSION_COMPLETA("Pensión Completa", 50);

    //2-Creo los atributos, ambos de visibilidad privada
    private String cadenaAMostrar;
    private double incrementoPrecio;

    //MÉTODOS

    //4-Creo el método constructor del enumerado Regimen

    private Regimen (String cadenaAMostrar, int incrementoPrecio){

        this.cadenaAMostrar=cadenaAMostrar;

        this.incrementoPrecio=incrementoPrecio;


    }

    //3-Creo el getter de visibilidad pública y que devuelve un dato de tipo double

    public double getIncrementoPrecio() {

        return incrementoPrecio;
    }

    //5-Creo el método ToString que devuelve la cadena a mostrar

    @Override
    public String toString() {

        //return "La opción de " + cadenaAMostrar + " conlleva un incremento de precio de " + incrementoPrecio;

        return ordinal() + " .- " + cadenaAMostrar;
    }

    //6-Tras pasar los tests hago commit and push
}
