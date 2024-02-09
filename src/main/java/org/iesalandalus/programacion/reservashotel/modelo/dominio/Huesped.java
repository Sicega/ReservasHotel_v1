package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**1-Creo la clase Huesped dentro del paquete dominio, que a su vez está dentro del paquete principal
 * org.iesalandalus.programacion.reservashotel*/
public class Huesped {

    /**
     * 2- Creo los atributos con el modificador de acceso apropiado, para el atributo de tipo LocalDate, importo java.time.LocalDate
     */

    private static final String ER_TELEFONO = "[0-9]{9}"; //Valida números de teléfono que tienen 9 dígitos
    private static final String ER_CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$"; //La primera parte de la ER representa el formato válido de la parte local del correo, seguido de una @ y la parte del dominio, tras ello un punto y la extensión del dominio que debe tener al menos 2 letras y como máximo 4
    private static final String ER_DNI = "([0-9]{8})([A-Za-z])"; //Valida 8 dígitos del 0 al 9 seguidos de una letra
    public static final String FORMATO_FECHA = "dd/MM/yyyy"; // Establezco un formato de día/mes/año

    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    //MÉTODOS

    /**
     * 3-Creo el método privado formateaNombre, que recibe de parámetro un dato de tipo String y devuelve un String.
     * Creo un array de tipo String que:
     * Con el método trim() se eliminan los espacios en blanco al principio y al final de la cadena de caracteres
     * Con el método split se divide el nombre en palabras
     * Creo un objeto de tipo StringBuilder que almacenará el resultado
     * Con un bucle for each recorro la cadena de caracteres haciendo que con el método substring
     * lleve a la posición indicada de la cadena de caracteres y haga que ese caracter se convierta en mayúscula
     * con toUppercase, lo mismo para las minúscuñas con toLowercase en la posición de caracter indicada.
     */

    private String formateaNombre(String nombre) {

        if(nombre==null){

            throw new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");
        }

        if(nombre.isBlank()){

            throw new IllegalArgumentException("ERROR: El nombre de un huésped no puede estar vacío.");
        }

        String[] nombre_apellidos = nombre.trim().toLowerCase().split("\\s+"); //La expresión regular \\s se interpreta como un espacio en blanco

        StringBuilder nombre_completo = new StringBuilder();

        for (String palabra : nombre_apellidos) {
            nombre_completo.append(palabra.substring(0, 1).toUpperCase()).append(palabra.substring(1)).append(" ");
        }

        return nombre_completo.toString().trim();
    }

    /**
     * 4- para comprobar la letra del DNI hay que trabajar con expresiones regulares, para ello importo las clases Matcher
     * y Pattern del paquete java.util.regex e implemento antes un método que calcule si la letra del DNI es correcta en base
     * a los números introducidos
     */

    private static char calcularLetraDni(int numeroDni) {
        // Este método calcula la letra del DNI con las letras permitidas y dividiendo la parte numérica del DNI entre 23
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indiceLetra = numeroDni % 23;
        return letras.charAt(indiceLetra);
    }

    private boolean comprobarLetraDni(String dni) {

        // Defino la expresión regular para extraer el número y la letra del DNI
        String regex = ER_DNI;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dni);

        if (matcher.matches()) {
            // Para obtener el número y la letra del DNI usando grupos:
            String numeroDni = matcher.group(1);
            String letraDni = matcher.group(2).toUpperCase(); // Convierto a mayúsculas la letra del DNI

            // Calcula la letra esperada usando el método implementado antes, calcularLetraDni
            char letraCalculada = calcularLetraDni(Integer.parseInt(numeroDni));

            // Compara la letra calculada con la letra introducida
            if(letraCalculada == letraDni.charAt(0))

                return true;

            else{
                throw new IllegalArgumentException("ERROR: La letra del dni del huésped no es correcta.");
            }
        } else {
            // El formato del DNI no es válido si no cumple lo anterior
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");

        }


    }

    /**
     * 5- Como ya está implementado que el nombre se introduzca correctamente con el método formateaNombre, y que la letra
     * del DNI se compruebe con el método calcularLetraDNI, paso a implementar el resto de métodos y sus comprobaciones de formato
     * utilizando las expresiones regulares declaradas
     */

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getTelefono() {

        return telefono;
    }

    public void setTelefono(String telefono) {

        if(telefono==null){

            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        }

        if(telefono.isBlank()){

            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        }

        if (telefono.matches(ER_TELEFONO)) {

            this.telefono = telefono;

        } else {

            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");

        }

    }

    public String getCorreo() {

        return correo;
    }

    public void setCorreo(String correo) {

        if(correo==null){

            throw new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");
        }

        if(correo.isBlank()){

            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        }

        if (correo.matches(ER_CORREO)) {

            this.correo = correo;

        } else {

            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");

        }
    }

    public String getDni() {

        return dni;
    }

    private void setDni(String dni) {

        if(dni==null){

            throw new NullPointerException("ERROR: El dni de un huésped no puede ser nulo.");
        }

        if(dni.isBlank()){

            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        }

        if (comprobarLetraDni(dni)) {

            this.dni = dni;


        }
    }

    public LocalDate getFechaNacimiento() {

        return fechaNacimiento;
    }

    /**Controlo con un condicional if-else que la fecha de nacimiento no sea posterior a la fecha actual
     * ni que tenga una diferencia de más de 120 años con la fecha actual, ya que es la edad máxima registrada de longevidad*/
    public void setFechaNacimiento(LocalDate fechaNacimiento) {

        if(fechaNacimiento==null){

            throw new NullPointerException("ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        }

        if (fechaNacimiento.isAfter(LocalDate.now()) || fechaNacimiento.plusYears(120).isBefore(LocalDate.now())) {

            throw new IllegalArgumentException("Fecha de nacimiento no válida.");

        } else {

            this.fechaNacimiento = fechaNacimiento;
        }
    }

    /**6-Implemento el método getIniciales que devuelve las iniciales del huésped aprovechando método ya implementado
     * formateaNombre*/

    private String getIniciales() {
            // Llamo al método formateaNombre y divido en palabras con un espacio entre ellas
            String[] palabras = formateaNombre(nombre).split(" ");
            StringBuilder iniciales = new StringBuilder(); //Creo un String Builder para almacenar las iniciales

            // Con un bucle for each, recorro la cadena de caracteres y tomo la primera letra de cada palabra
            for (String palabra : palabras) {

                    iniciales.append(palabra.charAt(0)); //Agrega la primera letra de cada palabra al StringBuilder
            }

            return iniciales.toString(); //Devuelve las iniciales en mayúsculas
        }

        /**7- Construyo el método constructor con parámetros para hacer uso de los métodos de modificación (los setters)*/
        public Huesped (String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento){

            setNombre(formateaNombre(nombre));
            setDni(dni);
            setCorreo(correo);
            setTelefono(telefono);
            setFechaNacimiento(fechaNacimiento);

        }

        /**8- Creo el constructor copia asignandole los valores del huésped como parámetros a los atributos*/

        public Huesped (Huesped huesped){

            if(huesped==null){

                throw new NullPointerException("ERROR: No es posible copiar un huésped nulo.");
            }

            this.nombre = huesped.nombre;
            this.dni = huesped.dni;
            this.correo = huesped.correo;
            this.telefono = huesped.telefono;
            this.fechaNacimiento = huesped.fechaNacimiento;

        }

        /** 9-Importo java.util.Objects para comparar con el método equals entre objetos, en este caso, para ver si
         * el nombre de la persona coincide con el DNI. Sobreescribo el método equals, de tipo boolean.*/

    @Override
    public boolean equals(Object comprobarHuesped) { //Creo un objeto llamado comprobarHuesped

        if (this == comprobarHuesped) return true;  // Para comprobar si el objeto es el mismo que el del parámetro

        if (comprobarHuesped == null || getClass() != comprobarHuesped.getClass()) return false; // Comprueba si el objeto es nulo o si pertenece a otra clase

        Huesped huesped = (Huesped) comprobarHuesped; // Realiza un casting del objeto pasado como parámetro a la clase Huesped

        return Objects.equals(dni, huesped.dni); // Compara los DNIs de los dos objetos Huesped
    }

    @Override
    public int hashCode() {

        return Objects.hash(dni); // Utilizo Objects.hash para calcular un código hash basado en el dni como identificador

    }

    /**10- Creo el método toString para devolver información sobre los huéspedes, donde pone %s se sustituye por los valores
     * declarados (nombre, dni, telefono, etc)*/

    @Override
    public String toString() {
        return String.format("nombre=%s ("+ getIniciales() +"), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s",

                nombre, dni, correo, telefono, fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }
}
