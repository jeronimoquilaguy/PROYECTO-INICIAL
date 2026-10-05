/**
 * Representa una rueda Lefty.
 *
 * Una rueda Lefty puede copiar el estado
 * de la rueda que tiene a su izquierda.
 */
public class RuedaLefty extends Rueda {

    /**
     * Crea una rueda Lefty con un color.
     *
     * @param color color inicial de la rueda
     */
    public RuedaLefty(String color) {
        super(color);
    }

    /**
     * Copia el color de la rueda que esta a la izquierda.
     *
     * @param izquierda rueda que esta a la izquierda
     */
    public void copiarEstado(Rueda izquierda) {
        if (izquierda != null) {
            cambiarColor(izquierda.obtenerColor());
        }
    }

    /**
     * Muestra visualmente que la rueda es Lefty.
     */
    public void mostrarTipo() {
        cambiarFondo("blue");
    }
}