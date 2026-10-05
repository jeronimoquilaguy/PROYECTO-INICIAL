/**
 * Representa un simbolo de la maquina tragamonedas.
 *
 * Los diferentes tipos de simbolos pueden tener
 * comportamientos diferentes cuando son seleccionados.
 */
public abstract class Simbolo {

    /** Color actual del simbolo. */
    protected String color;

    /**
     * Crea un simbolo con un color.
     *
     * @param color color inicial del simbolo
     */
    public Simbolo(String color) {
        this.color = color;
    }

    /**
     * Devuelve el color actual del simbolo.
     *
     * @return color del simbolo
     */
    public String obtenerColor() {
        return color;
    }

    /**
     * Cambia el color del simbolo.
     *
     * @param color nuevo color
     */
    public void cambiarColor(String color) {
        this.color = color;
    }

    /**
     * Ejecuta el comportamiento del simbolo cuando
     * es seleccionado.
     */
    public abstract void seleccionado();

    /**
     * Devuelve el tamaño actual del simbolo.
     *
     * @return tamaño del simbolo
     */
    public abstract int obtenerTamaño();

    /**
     * Indica si el simbolo esta visible.
     *
     * @return true si el simbolo es visible
     */
    public abstract boolean estaVisible();

    /**
     * Devuelve el tipo de simbolo.
     *
     * @return tipo del simbolo
     */
    public abstract String obtenerTipo();
}