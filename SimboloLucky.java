
/**
 * Representa un simbolo Lucky.
 *
 * El simbolo cambia su estado de suerte cada vez
 * que es seleccionado.
 */
public class SimboloLucky extends Simbolo {

    /** Indica si el simbolo tiene suerte. */
    private boolean suerte;

    /**
     * Crea un simbolo Lucky con un color.
     *
     * @param color color inicial del simbolo
     */
    public SimboloLucky(String color) {
        super(color);
        suerte = false;
    }

    /**
     * Cambia el estado de suerte del simbolo.
     *
     * Si tenia suerte, deja de tenerla.
     * Si no tenia suerte, pasa a tenerla.
     */
    @Override
    public void seleccionado() {
        suerte = !suerte;
    }

    /**
     * Indica si el simbolo tiene suerte actualmente.
     *
     * @return true si tiene suerte
     */
    public boolean tieneSuerte() {
        return suerte;
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
     * Indica que el simbolo Lucky siempre es visible.
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
     * @return "lucky"
     */
    @Override
    public String obtenerTipo() {
        return "lucky";
    }
}