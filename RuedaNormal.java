
/**
 * Representa una rueda normal.
 */
public class RuedaNormal extends Rueda {

    /**
     * Crea una rueda normal con un color.
     *
     * @param color color inicial de la rueda
     */
    public RuedaNormal(String color) {
        super(color);
    }

    /**
     * Muestra visualmente que la rueda es normal.
     */
    public void mostrarTipo() {
        cambiarFondo("magenta");
    }
}