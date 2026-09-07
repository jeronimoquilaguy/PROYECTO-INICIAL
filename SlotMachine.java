import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Simulador de una Maquina Tragamonedas.
 *
 * Jeronimo Quilaguy - Juan Roa
 * version 1
 */
public class SlotMachine  {
    /** Indica si la maquina esta visible en el lienzo. */
    private boolean visible;
    /** Indica si la ultima operacion realizada fue exitosa. */
    private boolean bandera;
    /** Lista de ruedas de la maquina. */
    private ArrayList<Rueda> ruedas;
    /** Catalogo de colores (simbolos) que puede usar la maquina. */
    private ArrayList<String> catalogoSimbolos;
    /** Indica si cada rueda esta fijada. */
    private ArrayList<Boolean> fijadas;
    /** Posicion en x donde se dibuja la primera rueda. */
    private final int POSICION_INICIAL_X = 50;
    /** Espacio en pixeles entre cada rueda. */
    private final int ESPACIO = 60;

    /**
     * Constructor de la maquina.
     */
    public SlotMachine() {
        visible = false;
        bandera = true;
        ruedas = new ArrayList<>();
        catalogoSimbolos = new ArrayList<>();
        fijadas = new ArrayList<>();

        catalogoSimbolos.add("red");
        catalogoSimbolos.add("blue");
        catalogoSimbolos.add("green");
    }

    /**
     * Agrega una rueda en la posicion indicada.
     * @param pos la posicion donde se va a poner la rueda
     */
    public void addWheel(int pos) {
        int indice = ajustarIndice(pos, ruedas.size() + 1);
        String colorInicial;
        if (catalogoSimbolos.isEmpty()) {
            colorInicial = "black";
        } else {
            colorInicial = catalogoSimbolos.get(0);
        }
        Rueda ruedaNueva = new Rueda(colorInicial);
        ruedas.add(indice, ruedaNueva);
        fijadas.add(indice, false);
        acomodarRuedas();
        bandera = true;
    }

    /**
     * Elimina la rueda que esta en la posicion indicada.
     * @param pos la posicion de la rueda a eliminar
     */
    public void delWheel(int pos) {
    if (ruedas.isEmpty()) {
        mostrarError("No hay ruedas para eliminar.");
        return;
    }

    if (!posicionValida(pos, ruedas.size())) {
        mostrarError("La posicion de la rueda no es valida.");
        return;
    }

    int indice = ajustarIndice(pos, ruedas.size());

    Rueda ruedaEliminada = ruedas.remove(indice);
    ruedaEliminada.hacerInvisible();

    fijadas.remove(indice);

    acomodarRuedas();
    bandera = true;
}
    
       /**
     * Intercambia la posicion de dos ruedas.
     * @param wheel1 posicion de la primera rueda
     * @param wheel2 posicion de la segunda rueda
     */
   public void swap(int wheel1, int wheel2) {
    if (ruedas.isEmpty()) {
        mostrarError("No hay ruedas para intercambiar.");
        return;
    }

    int indice1 = ajustarIndice(wheel1, ruedas.size());
    int indice2 = ajustarIndice(wheel2, ruedas.size());

    if (indice1 == indice2) {
        mostrarError("No se pueden intercambiar la misma rueda.");
        return;
    }

    Rueda ruedaTemporal = ruedas.get(indice1);
    ruedas.set(indice1, ruedas.get(indice2));
    ruedas.set(indice2, ruedaTemporal);
    boolean tempFijada = fijadas.get(indice1);
    fijadas.set(indice1, fijadas.get(indice2));
    fijadas.set(indice2, tempFijada);

    acomodarRuedas();
    bandera = true;
}
    
        /**
     * Fija una rueda para que no pueda girar ni cambiar de simbolo.
     * @param wheel posicion de la rueda
     */
    public void lock(int wheel) {
        if (!posicionValida(wheel, ruedas.size())) {
            mostrarError("La posicion de la rueda no es valida.");
            return;
        }

        fijadas.set(wheel - 1, true);
        bandera = true;
    }
    
     /**
     * Libera una rueda que estaba fijada.
     * @param wheel posicion de la rueda
     */
    public void unlock(int wheel) {
        if (!posicionValida(wheel, ruedas.size())) {
            mostrarError("La posicion de la rueda no es valida.");
            return;
        }

        fijadas.set(wheel - 1, false);
        bandera = true;
    }
    
    /**
     * Agrega un simbolo (color) al catalogo en la posicion indicada.
     * @param pos la posicion donde se va a poner el simbolo
     * @param color el color del simbolo
     */
    public void addSymbol(int pos, String color) {
        int indice = ajustarIndice(pos, catalogoSimbolos.size() + 1);
        catalogoSimbolos.add(indice, color);
        bandera = true;
    }

    /**
     * Elimina un simbolo del catalogo.
     * @param symbol el color del simbolo a eliminar
     */
    public void delSymbol(String symbol) {
        if (catalogoSimbolos.contains(symbol)) {
            catalogoSimbolos.remove(symbol);
            bandera = true;
        } else {
            mostrarError("El simbolo no existe en la maquina.");
        }
    }

    /**
     * Pone un simbolo especifico en una rueda determinada.
     * @param wheel la posicion de la rueda
     * @param symbol el color del simbolo a poner
     */
    public void placeSymbol(int wheel, String symbol) {
        if (!posicionValida(wheel, ruedas.size())) {
            mostrarError("La posicion de la rueda no es valida.");
            return;
        }

        if (fijadas.get(wheel - 1)) {
            mostrarError("La rueda esta fijada.");
            return;
        }

        if (!catalogoSimbolos.contains(symbol)) {
            mostrarError("El simbolo no es valido.");
            return;
        }

        ruedas.get(wheel - 1).cambiarColor(symbol);
        bandera = true;

        revisarSiGano();
    }


    /**
     * Gira una sola rueda de la maquina y le pone un simbolo al azar.
     * @param wheel la posicion de la rueda a girar
     */
    public void spin(int wheel) {
        if (ruedas.isEmpty() || catalogoSimbolos.isEmpty()) {
            mostrarError("Faltan ruedas o simbolos para girar.");
            return;
        }

        if (!posicionValida(wheel, ruedas.size())) {
            mostrarError("La posicion de la rueda no es valida.");
            return;
        }

        int indice = wheel - 1;

        if (fijadas.get(indice)) {
            mostrarError("La rueda esta fijada.");
            return;
        }

        int numeroAlAzar =
            (int)(Math.random() * catalogoSimbolos.size());

        ruedas.get(indice).cambiarColor(
            catalogoSimbolos.get(numeroAlAzar)
        );

        revisarSiGano();
        bandera = true;
    }
    
    /**
     * Gira una rueda una cantidad determinada de pasos.
     * @param wheel posicion de la rueda
     * @param steps cantidad de pasos
     */
    public void spin(int wheel, int steps) {
        if (ruedas.isEmpty() || catalogoSimbolos.isEmpty()) {
            mostrarError("Faltan ruedas o simbolos para girar.");
            return;
        }

        if (!posicionValida(wheel, ruedas.size())) {
            mostrarError("La posicion de la rueda no es valida.");
            return;
        }

        if (fijadas.get(wheel - 1)) {
            mostrarError("La rueda esta fijada.");
            return;
        }

        if (steps < 0) {
            mostrarError("La cantidad de pasos no puede ser negativa.");
            return;
        }

        int indiceRueda = wheel - 1;

        String colorActual = ruedas.get(indiceRueda).obtenerColor();

        int posicionActual = catalogoSimbolos.indexOf(colorActual);

        if (posicionActual == -1) {
            mostrarError("El simbolo actual no es valido.");
            return;
        }

        for (int i = 0; i < steps; i++) {
            posicionActual =
                (posicionActual + 1) % catalogoSimbolos.size();

            ruedas.get(indiceRueda).cambiarColor(
                catalogoSimbolos.get(posicionActual)
            );

            if (visible) {
                Canvas.getCanvas().wait(100);
            }
        }

        revisarSiGano();
        bandera = true;
    }
    
     /**
     * Fuerza una configuracion determinada de las ruedas.
     * @param setSymbols configuracion deseada
     */
    public void spin(String[] setSymbols) {
        if (setSymbols == null) {
            mostrarError("La configuracion no puede ser nula.");
            return;
        }

        if (setSymbols.length != ruedas.size()) {
            mostrarError(
                "La configuracion debe tener el mismo numero de simbolos que ruedas."
            );
            return;
        }

        for (String simbolo : setSymbols) {
            if (!catalogoSimbolos.contains(simbolo)) {
                mostrarError("La configuracion contiene un simbolo no valido.");
                return;
            }
        }

        for (int i = 0; i < ruedas.size(); i++) {
            if (fijadas.get(i)
                    && !ruedas.get(i).obtenerColor().equals(setSymbols[i])) {
                mostrarError("No se puede cambiar una rueda fijada.");
                return;
            }
        }

        for (int i = 0; i < ruedas.size(); i++) {
            ruedas.get(i).cambiarColor(setSymbols[i]);
        }

        revisarSiGano();
        bandera = true;
    }


    /**
     * Gira todas las ruedas de la maquina.
     */
    public void spin() {
        if (ruedas.isEmpty()) {
            mostrarError("No hay ruedas para girar.");
            return;
        }

        boolean operacionValida = true;

        for (int i = 1; i <= ruedas.size(); i++) {
            spin(i);

            if (!bandera) {
                operacionValida = false;
            }
        }

        bandera = operacionValida;
        revisarSiGano();
    }

    /**
     * Retorna los colores de los simbolos que hay en el catalogo.
     * @return arreglo con los nombres de los colores
     */
    public String[] symbols() {
        return catalogoSimbolos.toArray(new String[0]);
    }

    /**
     * Cuenta cuantos simbolos distintos hay configurados en la maquina.
     * @return el numero de simbolos distintos
     */
    public int distinctSymbols() {
        ArrayList<String> distintos = new ArrayList<>();
        for (String simbolo : catalogoSimbolos) {
            if (!distintos.contains(simbolo)) {
                distintos.add(simbolo);
            }
        }
        return distintos.size();
    }

    /**
     * Retorna los colores que se ven actualmente en todas las ruedas.
     * @return arreglo con los colores de izquierda a derecha
     */
    public String[] configuration() {
        String[] colores = new String[ruedas.size()];
        for (int i = 0; i < ruedas.size(); i++) {
            colores[i] = ruedas.get(i).obtenerColor();
        }
        return colores;
    }

    /**
     * Revisa si la configuracion actual es ganadora (todos los simbolos iguales).
     * @return true si es ganadora, false si no
     */
    public boolean isJackpot() {
        if (ruedas.size() < 2) {
            return false;
        }
        String primerColor = ruedas.get(0).obtenerColor();
        for (Rueda r : ruedas) {
            if (!r.obtenerColor().equals(primerColor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Hace visible el simulador en la pantalla.
     */
    public void makeVisible() {
        visible = true;
        for (Rueda r : ruedas) {
            r.hacerVisible();
        }
        revisarSiGano();
        bandera = true;
    }

    /**
     * Hace invisible el simulador.
     */
    public void makeInvisible() {
        visible = false;
        for (Rueda r : ruedas) {
            r.hacerInvisible();
        }
        bandera = true;
    }

    /**
     * Termina la ejecucion del simulador.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Dice si la ultima operacion que se hizo fue exitosa.
     * @return true si funciono bien, false si hubo un error
     */
    public boolean ok() {
        return bandera;
    }

    // metodos privados de apoyo

    /**
     * Ajusta la posicion que escribe el usuario al indice que usa Java (0-based).
     * @param pos la posicion que escribio el usuario
     * @param maximo el maximo permitido
     * @return el indice ya ajustado
     */
    private int ajustarIndice(int pos, int maximo) {
        if (pos < 1) {
            pos = 1;
        }
        if (pos > maximo) {
            pos = maximo;
        }
        return pos - 1;
    }
    
     /**
     * Verifica si una posicion se encuentra dentro del rango.
     * @param pos posicion a verificar
     * @param maximo cantidad maxima
     * @return true si la posicion es valida
     */
    private boolean posicionValida(int pos, int maximo) {
        return pos >= 1 && pos <= maximo;
    }

    /**
     * Muestra un mensaje de error y marca la operacion como no exitosa.
     * @param mensaje el mensaje a mostrar
     */
    private void mostrarError(String mensaje) {
        bandera = false;
        if (visible) {
            JOptionPane.showMessageDialog(null, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Acomoda todas las ruedas una al lado de la otra en el lienzo.
     */
    private void acomodarRuedas() {
        int x = POSICION_INICIAL_X;
        for (Rueda r : ruedas) {
            r.moverA(x);
            if (visible) {
                r.hacerVisible();
            }
            x = x + ESPACIO;
        }
    }

    /**
     * Cambia el fondo de las ruedas segun si se gano o no.
     */
    private void revisarSiGano() {
        if (isJackpot() && visible) {
            for (Rueda r : ruedas) {
                r.cambiarFondo("yellow");
            }
        } else if (visible) {
            for (Rueda r : ruedas) {
                r.cambiarFondo("magenta");
            }
        }
    }
}
