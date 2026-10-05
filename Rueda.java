
/**
 * Representa una rueda individual de la maquina tragamonedas.
 * Cada rueda tiene un fondo (un Rectangle) y un simbolo (un Circle).
 *
 * Jeronimo Quilaguy - Juan Roa
 * version 1
 */
public class Rueda {
    /** Fondo de la rueda. */
    private Rectangle fondo;
    /** Simbolo que se muestra en la rueda. */
    private Circle simbolo;
    /** Color actual del simbolo. */
    private String colorActual;

    /** Posicion en x del fondo. */
    private int posicionFondoX;
    /** Posicion en x del simbolo. */
    private int posicionSimboloX;

    /**
     * Crea una rueda nueva con un color inicial.
     * @param color el color inicial del simbolo
     */
    public Rueda(String color) {
        colorActual = color;

        fondo = new Rectangle();
        fondo.changeSize(80, 50);
        posicionFondoX = 70;

        simbolo = new Circle();
        simbolo.changeSize(30);
        simbolo.changeColor(color);
        posicionSimboloX = 20;
    }

    /**
     * Mueve la rueda hasta una posicion horizontal del lienzo.
     * @param x la posicion en x a la que se debe mover la rueda
     */
    public void moverA(int x) {
        int distanciaFondo = x - posicionFondoX;
        fondo.moveHorizontal(distanciaFondo);
        posicionFondoX = x;

        int xSimbolo = x + 10;
        int distanciaSimbolo = xSimbolo - posicionSimboloX;
        simbolo.moveHorizontal(distanciaSimbolo);
        posicionSimboloX = xSimbolo;
    }

    /**
     * Cambia el color del simbolo de la rueda.
     * @param color el nuevo color
     */
    public void cambiarColor(String color) {
        colorActual = color;
        simbolo.changeColor(color);
    }

    /**
     * Retorna el color actual del simbolo de la rueda.
     * @return el color actual
     */
    public String obtenerColor() {
        return colorActual;
    }

    /**
     * Cambia el color de fondo de la rueda.
     * @param color el nuevo color de fondo
     */
    public void cambiarFondo(String color) {
        fondo.changeColor(color);
        simbolo.changeColor(colorActual);
    }

    /**
     * Hace visible la rueda en el lienzo.
     */
    public void hacerVisible() {
        fondo.makeVisible();
        simbolo.makeVisible();
    }

    /**
     * Hace invisible la rueda en el lienzo.
     */
    public void hacerInvisible() {
        simbolo.makeInvisible();
        fondo.makeInvisible();
    }
    
    /**
     * Dice si la rueda puede bloquearse.
     *
     * @return true porque una rueda normal puede bloquearse
     */
    public boolean puedeBloquear() {
        return true;
    }

    /**
     * Dice si la rueda puede intercambiarse.
     *
     * @return true porque una rueda normal puede intercambiarse
     */
    public boolean puedeIntercambiar() {
        return true;
    }

    /**
     * Dice si la rueda puede eliminarse.
     *
     * @return true porque una rueda normal puede eliminarse
     */
    public boolean puedeEliminar() {
        return true;
    }

    /**
     * Cambia el tamaño del simbolo.
     *
     * @param tamaño nuevo tamaño del simbolo
     */
    public void cambiarTamaño(int tamaño) {
        simbolo.changeSize(tamaño);
    }

    /**
     * Cambia la visibilidad del simbolo.
     *
     * @param visible indica si el simbolo debe ser visible
     */
    public void cambiarVisibilidad(boolean visible) {
        if (visible) {
            simbolo.makeVisible();
        } else {
            simbolo.makeInvisible();
        }
    }
}