
/**
 * Representa un simbolo normal.
 *
 * Un simbolo normal no tiene un comportamiento
 * especial cuando es seleccionado.
 */
public class SimboloNormal extends Simbolo {

    /**
     * Crea un simbolo normal con un color.
     *
     * @param color color inicial del simbolo
     */
    public SimboloNormal(String color) {
        super(color);
    }

    /**
     * No realiza ninguna accion especial al ser seleccionado.
     */
    @Override
    public void seleccionado() {
    }

    /**
     * Devuelve el tamaño normal del simbolo.
     *
     * @return 20
     */
    @Override
    public int obtenerTamaño() {
        return 20;
    }

    /**
     * Indica que el simbolo normal siempre es visible.
     *
     * @return true
     */
    @Override
    public boolean estaVisible() {
        return true;
    }

    /**
     * Devuelve el tipo del simbolo.
     *
     * @return "normal"
     */
    @Override
    public String obtenerTipo() {
        return "normal";
    }
}