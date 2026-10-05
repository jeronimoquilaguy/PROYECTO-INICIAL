
/**
 * Representa un simbolo Ephemeral.
 *
 * El tamaño del simbolo disminuye cada vez
 * que es seleccionado.
 */
public class SimboloEphemeral extends Simbolo {

    /** Tamaño actual del simbolo. */
    private int tamaño;

    /**
     * Crea un simbolo Ephemeral con un color.
     *
     * @param color color inicial del simbolo
     */
    public SimboloEphemeral(String color) {
        super(color);
        tamaño = 20;
    }

    /**
     * Disminuye el tamaño del simbolo cuando
     * es seleccionado.
     */
    @Override
    public void seleccionado() {
        if (tamaño > 1) {
            tamaño--;
        }
    }

    /**
     * Devuelve el tamaño actual del simbolo.
     *
     * @return tamaño actual
     */
    @Override
    public int obtenerTamaño() {
        return tamaño;
    }

    /**
     * Indica que el simbolo Ephemeral permanece visible.
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
     * @return "ephemeral"
     */
    @Override
    public String obtenerTipo() {
        return "ephemeral";
    }
}