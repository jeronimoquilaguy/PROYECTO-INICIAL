/**
 * Representa una rueda Rebel.
 *
 * Una rueda Rebel no puede bloquearse,
 * intercambiarse ni eliminarse.
 */
public class RuedaRebel extends Rueda {

    /**
     * Crea una rueda Rebel con un color.
     *
     * @param color color inicial de la rueda
     */
    public RuedaRebel(String color) {
        super(color);
    }

    /**
     * Una rueda Rebel no se puede bloquear.
     *
     * @return false porque una Rebel no puede bloquearse
     */
    @Override
    public boolean puedeBloquear() {
        return false;
    }

    /**
     * Una rueda Rebel no se puede intercambiar.
     *
     * @return false porque una Rebel no puede intercambiarse
     */
    @Override
    public boolean puedeIntercambiar() {
        return false;
    }

    /**
     * Una rueda Rebel no se puede eliminar.
     *
     * @return false porque una Rebel no puede eliminarse
     */
    @Override
    public boolean puedeEliminar() {
        return false;
    }

    /**
     * Muestra visualmente que la rueda es Rebel.
     */
    public void mostrarTipo() {
        cambiarFondo("red");
    }
}