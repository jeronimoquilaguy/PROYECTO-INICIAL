
/**
 * Representa un simbolo Shy.
 *
 * El simbolo cambia entre visible e invisible
 * cada vez que es seleccionado.
 */
public class SimboloShy extends Simbolo {

    /** Indica si el simbolo esta visible. */
    private boolean visible;


    /**
     * Crea un simbolo Shy con un color.
     *
     * @param color color inicial del simbolo
     */
    public SimboloShy(String color) {
        super(color);
        visible = true;
    }


    /**
     * Cambia la visibilidad del simbolo.
     *
     * Si estaba visible pasa a invisible,
     * y si estaba invisible pasa a visible.
     */
    @Override
    public void seleccionado() {
        visible = !visible;
    }


    /**
     * Devuelve el tamaño del simbolo.
     *
     * @return 20
     */
    @Override
    public int obtenerTamaño() {
        return 20;
    }


    /**
     * Indica si el simbolo esta visible.
     *
     * @return true si esta visible
     */
    @Override
    public boolean estaVisible() {
        return visible;
    }


    /**
     * Devuelve el tipo del simbolo.
     *
     * @return "shy"
     */
    @Override
    public String obtenerTipo() {
        return "shy";
    }
}