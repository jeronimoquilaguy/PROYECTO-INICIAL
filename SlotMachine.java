import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Simulador de una Maquina Tragamonedas.
 *
 * Jeronimo Quilaguy - Juan Roa
 * Version 4
 */
public class SlotMachine {

    /** Indica si la maquina esta visible en el lienzo. */
    private boolean visible;

    /** Indica si la ultima operacion realizada fue exitosa. */
    private boolean bandera;

    /** Lista de ruedas de la maquina. */
    private ArrayList<Rueda> ruedas;

    /** Catalogo de colores (simbolos) que puede usar la maquina. */
    private ArrayList<String> catalogoSimbolos;

    /** Tipo de cada simbolo del catalogo. */
    private ArrayList<String> tiposSimbolos;

    /** Indica si cada rueda esta fijada. */
    private ArrayList<Boolean> fijadas;

    /** Simbolo que tiene actualmente cada rueda. */
    private ArrayList<Simbolo> simbolosActuales;

    /** Posicion en x donde se dibuja la primera rueda. */
    private final int POSICION_INICIAL_X = 50;

    /** Espacio en pixeles entre cada rueda. */
    private final int ESPACIO = 60;

    /**
     * Constructor de la maquina con tres simbolos iniciales.
     */
    public SlotMachine() {
        visible = false;
        bandera = true;

        ruedas = new ArrayList<>();
        catalogoSimbolos = new ArrayList<>();
        tiposSimbolos = new ArrayList<>();
        fijadas = new ArrayList<>();
        simbolosActuales = new ArrayList<>();

        catalogoSimbolos.add("red");
        tiposSimbolos.add("normal");

        catalogoSimbolos.add("blue");
        tiposSimbolos.add("normal");

        catalogoSimbolos.add("green");
        tiposSimbolos.add("normal");
    }

    /**
     * Crea una maquina con n ruedas y n simbolos, todo inicializado al azar.
     *
     * @param n la cantidad de ruedas y de simbolos
     */
    public SlotMachine(int n) {
        visible = false;
        bandera = true;

        ruedas = new ArrayList<>();
        catalogoSimbolos = new ArrayList<>();
        tiposSimbolos = new ArrayList<>();
        fijadas = new ArrayList<>();
        simbolosActuales = new ArrayList<>();

        if (n < 1) {
            n = 1;
        }

        String[] coloresBase = {
            "red", "blue", "green", "yellow", "orange",
            "cyan", "magenta", "pink", "black", "gray"
        };

        for (int i = 0; i < n; i++) {
            String color = (i < coloresBase.length) ? coloresBase[i] : "color" + i;
            catalogoSimbolos.add(color);
            tiposSimbolos.add("normal");
        }

        for (int i = 0; i < n; i++) {
            int indiceAlAzar = (int) (Math.random() * catalogoSimbolos.size());
            String colorAlAzar = catalogoSimbolos.get(indiceAlAzar);

            Rueda ruedaNueva = new RuedaNormal(colorAlAzar);
            ruedas.add(ruedaNueva);
            fijadas.add(false);

            Simbolo simbolo = crearSimbolo("normal", colorAlAzar);
            simbolosActuales.add(simbolo);
        }

        acomodarRuedas();
    }

    /**
     * Agrega una rueda normal.
     *
     * @param pos posicion donde se agrega la rueda
     */
    public void addWheel(int pos) {
        addWheel("normal", pos);
    }

    /**
     * Agrega una rueda del tipo indicado.
     *
     * @param tipo tipo de rueda: normal, lefty o rebel
     * @param pos posicion donde se agrega
     */
    public void addWheel(String tipo, int pos) {
        if (!tipoRuedaValido(tipo)) {
            mostrarError("Tipo de rueda invalido.");
            return;
        }

        int indice = ajustarIndice(pos, ruedas.size() + 1);
        String colorInicial = catalogoSimbolos.isEmpty() ? "black" : catalogoSimbolos.get(0);

        Rueda ruedaNueva;
        if (tipo.equals("normal")) {
            ruedaNueva = new RuedaNormal(colorInicial);
        } else if (tipo.equals("lefty")) {
            ruedaNueva = new RuedaLefty(colorInicial);
        } else {
            ruedaNueva = new RuedaRebel(colorInicial);
        }

        ruedas.add(indice, ruedaNueva);
        fijadas.add(indice, false);

        Simbolo simbolo = crearSimbolo("normal", colorInicial);
        simbolosActuales.add(indice, simbolo);

        acomodarRuedas();
        bandera = true;
    }

    /**
     * Elimina la rueda en la posicion indicada si lo permite.
     *
     * @param pos posicion de la rueda
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
        Rueda rueda = ruedas.get(indice);

        if (!rueda.puedeEliminar()) {
            mostrarError("Esta rueda no se puede eliminar.");
            return;
        }

        Rueda ruedaEliminada = ruedas.remove(indice);
        ruedaEliminada.hacerInvisible();

        fijadas.remove(indice);
        simbolosActuales.remove(indice);

        acomodarRuedas();
        bandera = true;
    }

    /**
     * Intercambia la posicion de dos ruedas si ambas lo permiten.
     *
     * @param wheel1 primera rueda
     * @param wheel2 segunda rueda
     */
    public void swap(int wheel1, int wheel2) {
        if (ruedas.isEmpty()) {
            mostrarError("No hay ruedas para intercambiar.");
            return;
        }

        if (!posicionValida(wheel1, ruedas.size()) || !posicionValida(wheel2, ruedas.size())) {
            mostrarError("Las posiciones de las ruedas no son validas.");
            return;
        }

        int indice1 = ajustarIndice(wheel1, ruedas.size());
        int indice2 = ajustarIndice(wheel2, ruedas.size());

        if (indice1 == indice2) {
            mostrarError("No se pueden intercambiar la misma rueda.");
            return;
        }

        if (fijadas.get(indice1) || fijadas.get(indice2)) {
            mostrarError("No se pueden intercambiar ruedas fijadas.");
            return;
        }

        if (!ruedas.get(indice1).puedeIntercambiar() || !ruedas.get(indice2).puedeIntercambiar()) {
            mostrarError("Una rueda Rebel no se puede intercambiar.");
            return;
        }

        Rueda ruedaTemporal = ruedas.get(indice1);
        ruedas.set(indice1, ruedas.get(indice2));
        ruedas.set(indice2, ruedaTemporal);

        boolean tempFijada = fijadas.get(indice1);
        fijadas.set(indice1, fijadas.get(indice2));
        fijadas.set(indice2, tempFijada);

        Simbolo simboloTemporal = simbolosActuales.get(indice1);
        simbolosActuales.set(indice1, simbolosActuales.get(indice2));
        simbolosActuales.set(indice2, simboloTemporal);

        acomodarRuedas();
        bandera = true;
    }

    /**
     * Fija una rueda para que no pueda girar ni cambiar.
     *
     * @param wheel posicion de la rueda
     * @return posicion de la rueda fijada o -1 si falla
     */
    public int lock(int wheel) {
        if (!posicionValida(wheel, ruedas.size())) {
            mostrarError("La posicion de la rueda no es valida.");
            return -1;
        }

        int indice = wheel - 1;
        if (!ruedas.get(indice).puedeBloquear()) {
            mostrarError("Esta rueda no se puede bloquear.");
            return -1;
        }

        fijadas.set(indice, true);
        bandera = true;
        return wheel;
    }

    /**
     * Libera una rueda fijada.
     *
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
     * Agrega un simbolo normal al catalogo.
     *
     * @param pos posicion del simbolo
     * @param color color del simbolo
     */
    public void addSymbol(int pos, String color) {
        addSymbol("normal", pos, color);
    }

    /**
     * Agrega un simbolo del tipo indicado al catalogo.
     *
     * @param tipo tipo de simbolo
     * @param pos posicion del simbolo
     * @param color color del simbolo
     */
    public void addSymbol(String tipo, int pos, String color) {
        if (!tipoSimboloValido(tipo)) {
            mostrarError("Tipo de simbolo invalido.");
            return;
        }

        int indice = ajustarIndice(pos, catalogoSimbolos.size() + 1);
        catalogoSimbolos.add(indice, color);
        tiposSimbolos.add(indice, tipo);
        bandera = true;
    }

    /**
     * Elimina un simbolo del catalogo.
     *
     * @param symbol color del simbolo
     */
    public void delSymbol(String symbol) {
        if (catalogoSimbolos.contains(symbol)) {
            int indice = catalogoSimbolos.indexOf(symbol);
            catalogoSimbolos.remove(indice);
            tiposSimbolos.remove(indice);
            bandera = true;
        } else {
            mostrarError("El simbolo no existe en la maquina.");
        }
    }

    /**
     * Coloca un simbolo en una rueda especifica.
     *
     * @param wheel posicion de la rueda
     * @param symbol color del simbolo
     */
    public void placeSymbol(int wheel, String symbol) {
        if (!posicionValida(wheel, ruedas.size())) {
            mostrarError("La posicion de la rueda no es valida.");
            return;
        }

        int indiceRueda = wheel - 1;
        if (fijadas.get(indiceRueda)) {
            mostrarError("La rueda esta fijada.");
            return;
        }

        if (!catalogoSimbolos.contains(symbol)) {
            mostrarError("El simbolo no es valido.");
            return;
        }

        int indiceSimbolo = catalogoSimbolos.indexOf(symbol);
        String tipo = tiposSimbolos.get(indiceSimbolo);

        ruedas.get(indiceRueda).cambiarColor(symbol);
        actualizarSimbolo(indiceRueda, tipo, symbol, false);

        bandera = true;
        revisarSiGano();
    }

    /**
     * Gira una rueda al azar.
     *
     * @param wheel posicion de la rueda
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

        int numeroAlAzar = (int) (Math.random() * catalogoSimbolos.size());
        String color = catalogoSimbolos.get(numeroAlAzar);
        String tipo = tiposSimbolos.get(numeroAlAzar);

        actualizarSimbolo(indice, tipo, color, true);
        aplicarComportamientoRueda(indice);
        revisarSiGano();

        bandera = true;
    }

    /**
     * Gira una rueda una cantidad determinada de pasos.
     *
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

        int indiceRueda = wheel - 1;
        if (fijadas.get(indiceRueda)) {
            mostrarError("La rueda esta fijada.");
            return;
        }

        if (steps < 0) {
            mostrarError("La cantidad de pasos no puede ser negativa.");
            return;
        }

        String colorActual = ruedas.get(indiceRueda).obtenerColor();
        int posicionActual = catalogoSimbolos.indexOf(colorActual);

        if (posicionActual == -1) {
            mostrarError("El simbolo actual no es valido.");
            return;
        }

        for (int i = 0; i < steps; i++) {
            posicionActual = (posicionActual + 1) % catalogoSimbolos.size();
            String nuevoColor = catalogoSimbolos.get(posicionActual);
            String tipo = tiposSimbolos.get(posicionActual);

            actualizarSimbolo(indiceRueda, tipo, nuevoColor, true);
            aplicarComportamientoRueda(indiceRueda);

            if (visible) {
                Canvas.getCanvas().wait(100);
            }
        }

        revisarSiGano();
        bandera = true;
    }

    /**
     * Fuerza una configuracion determinada mediante un arreglo.
     *
     * @param setSymbols configuracion deseada
     */
    public void spin(String[] setSymbols) {
        if (setSymbols == null) {
            mostrarError("La configuracion no puede ser nula.");
            return;
        }

        if (setSymbols.length != ruedas.size()) {
            mostrarError("La configuracion debe tener el mismo numero de simbolos que ruedas.");
            return;
        }

        for (String simbolo : setSymbols) {
            if (!catalogoSimbolos.contains(simbolo)) {
                mostrarError("La configuracion contiene un simbolo no valido.");
                return;
            }
        }

        for (int i = 0; i < ruedas.size(); i++) {
            if (fijadas.get(i) && !ruedas.get(i).obtenerColor().equals(setSymbols[i])) {
                mostrarError("No se puede cambiar una rueda fijada.");
                return;
            }
        }

        for (int i = 0; i < ruedas.size(); i++) {
            String color = setSymbols[i];
            int indiceSimbolo = catalogoSimbolos.indexOf(color);
            String tipo = tiposSimbolos.get(indiceSimbolo);

            actualizarSimbolo(i, tipo, color, false);
        }

        revisarSiGano();
        bandera = true;
    }

    /**
     * Gira todas las ruedas.
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
     * Retorna todos los simbolos del catalogo.
     *
     * @return arreglo con los colores de los simbolos
     */
    public String[] symbols() {
        return catalogoSimbolos.toArray(new String[0]);
    }

    /**
     * Cuenta cuantos simbolos diferentes existen.
     *
     * @return cantidad de simbolos diferentes
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
     * Retorna la configuracion actual de las ruedas.
     *
     * @return arreglo con los colores
     */
    public String[] configuration() {
        String[] colores = new String[ruedas.size()];
        for (int i = 0; i < ruedas.size(); i++) {
            colores[i] = ruedas.get(i).obtenerColor();
        }
        return colores;
    }

    /**
     * Indica si hay jackpot (todos los simbolos iguales).
     *
     * @return true si hay jackpot
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
     * Hace visible la maquina.
     */
    public void makeVisible() {
        visible = true;
        for (Rueda rueda : ruedas) {
            rueda.hacerVisible();
        }
        acomodarRuedas();
        revisarSiGano();
        bandera = true;
    }

    /**
     * Hace invisible la maquina.
     */
    public void makeInvisible() {
        visible = false;
        for (Rueda r : ruedas) {
            r.hacerInvisible();
        }
        bandera = true;
    }

    /**
     * Termina la ejecucion.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Indica si la ultima operacion fue exitosa.
     *
     * @return true si funciono bien
     */
    public boolean ok() {
        return bandera;
    }

    /**
     * Retorna el tamaño actual del simbolo de una rueda.
     *
     * @param wheel posicion de la rueda
     * @return tamaño del simbolo
     */
    public int tamañoSimbolo(int wheel) {
        if (!posicionValida(wheel, simbolosActuales.size())) {
            return -1;
        }
        return simbolosActuales.get(wheel - 1).obtenerTamaño();
    }

    /**
     * Indica si el simbolo de una rueda esta visible.
     *
     * @param wheel posicion de la rueda
     * @return true si el simbolo esta visible
     */
    public boolean simboloVisible(int wheel) {
        if (!posicionValida(wheel, simbolosActuales.size())) {
            return false;
        }
        return simbolosActuales.get(wheel - 1).estaVisible();
    }

    // --- Métodos privados auxiliares ---

   /**
     * Crea un simbolo del tipo indicado y con el color especificado.
     *
     * @param tipo tipo de simbolo que se desea crear
     * @param color color inicial del simbolo
     * @return simbolo creado
     */
    private Simbolo crearSimbolo(String tipo, String color) {
        if (tipo.equals("ephemeral")) {
            return new SimboloEphemeral(color);
        } else if (tipo.equals("shy")) {
            return new SimboloShy(color);
        } else if (tipo.equals("lucky")) {
            return new SimboloLucky(color);
        } else {
            return new SimboloNormal(color);
        }
    }

    /**
     * Actualiza el simbolo de una posicion con un tipo y color.
     *
     * Si el tipo cambia, crea un nuevo simbolo.
     * Si el tipo es el mismo, cambia solamente el color.
     *
     * @param indice posicion del simbolo
     * @param tipo nuevo tipo de simbolo
     * @param color nuevo color del simbolo
     * @param seleccionado indica si el simbolo fue seleccionado
     */
    private void actualizarSimbolo(int indice, String tipo, String color, boolean seleccionado) {
        Simbolo simbolo = simbolosActuales.get(indice);

        if (simbolo == null || !simbolo.obtenerTipo().equals(tipo)) {
            simbolo = crearSimbolo(tipo, color);
            simbolosActuales.set(indice, simbolo);
        } else {
            simbolo.cambiarColor(color);
        }

        if (seleccionado) {
            simbolo.seleccionado();
        }

        ruedas.get(indice).cambiarColor(color);
        ruedas.get(indice).cambiarTamaño(simbolo.obtenerTamaño());
        ruedas.get(indice).cambiarVisibilidad(simbolo.estaVisible());
    }

    /**
     * Aplica el comportamiento especial de una rueda.
     *
     * Si la rueda es Lefty, copia el estado de la rueda
     * que se encuentra inmediatamente a su izquierda.
     *
     * @param indice posicion de la rueda
     */
    private void aplicarComportamientoRueda(int indice) {
        if (indice == 0) {
            return;
        }

        if (ruedas.get(indice) instanceof RuedaLefty) {
            RuedaLefty lefty = (RuedaLefty) ruedas.get(indice);
            lefty.copiarEstado(ruedas.get(indice - 1));

            Simbolo simboloIzquierda = simbolosActuales.get(indice - 1);
            Simbolo simboloActual = simbolosActuales.get(indice);

            if (!simboloActual.obtenerTipo().equals(simboloIzquierda.obtenerTipo())) {
                simbolosActuales.set(indice, crearSimbolo(simboloIzquierda.obtenerTipo(), simboloIzquierda.obtenerColor()));
            } else {
                simbolosActuales.get(indice).cambiarColor(simboloIzquierda.obtenerColor());
            }

            Simbolo simbolo = simbolosActuales.get(indice);
            ruedas.get(indice).cambiarTamaño(simbolo.obtenerTamaño());
            ruedas.get(indice).cambiarVisibilidad(simbolo.estaVisible());
        }
    }

    /**
     * Verifica si el tipo de rueda es valido.
     *
     * @param tipo tipo de rueda
     * @return true si el tipo es normal, lefty o rebel
     */
    private boolean tipoRuedaValido(String tipo) {
        return tipo.equals("normal") || tipo.equals("lefty") || tipo.equals("rebel");
    }

    /**
     * Verifica si el tipo de simbolo es valido.
     *
     * @param tipo tipo de simbolo
     * @return true si el tipo es normal, ephemeral, shy o lucky
     */
    private boolean tipoSimboloValido(String tipo) {
        return tipo.equals("normal") || tipo.equals("ephemeral") || tipo.equals("shy") || tipo.equals("lucky");
    }

    /**
     * Ajusta una posicion para que se encuentre dentro
     * del rango permitido.
     *
     * @param pos posicion indicada
     * @param maximo posicion maxima permitida
     * @return indice ajustado para trabajar con listas
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
     * Verifica si una posicion se encuentra dentro del rango valido.
     *
     * @param pos posicion que se desea verificar
     * @param maximo posicion maxima permitida
     * @return true si la posicion es valida
     */
    private boolean posicionValida(int pos, int maximo) {
        return pos >= 1 && pos <= maximo;
    }

    /**
     * Muestra un mensaje de error y marca la operacion como fallida.
     *
     * @param mensaje mensaje que se mostrara al usuario
     */
    private void mostrarError(String mensaje) {
        bandera = false;
        if (visible) {
            JOptionPane.showMessageDialog(null, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Organiza las ruedas horizontalmente y las hace visibles
     * cuando la maquina esta visible.
     */
    private void acomodarRuedas() {
        int x = POSICION_INICIAL_X;
        for (Rueda rueda : ruedas) {
            rueda.moverA(x);
            if (visible) {
                rueda.hacerVisible();
                mostrarTipoRueda(rueda);
            }
            x = x + ESPACIO;
        }
    }

    /**
     * Verifica si la configuracion actual es un jackpot.
     *
     * Si hay jackpot, muestra las ruedas con fondo amarillo.
     * De lo contrario, muestra el color correspondiente al tipo de rueda.
     */
    private void revisarSiGano() {
        if (isJackpot() && visible) {
            for (Rueda rueda : ruedas) {
                rueda.cambiarFondo("yellow");
            }
        } else if (visible) {
            for (Rueda rueda : ruedas) {
                mostrarTipoRueda(rueda);
            }
        }
    }

    /**
     * Muestra visualmente el tipo de una rueda.
     *
     * Las ruedas Rebel, Lefty y normales tienen diferentes
     * colores de fondo para distinguirlas.
     *
     * @param rueda rueda cuyo tipo se desea mostrar
     */
    private void mostrarTipoRueda(Rueda rueda) {
        if (rueda instanceof RuedaRebel) {
            rueda.cambiarFondo("red");
        } else if (rueda instanceof RuedaLefty) {
            rueda.cambiarFondo("blue");
        } else {
            rueda.cambiarFondo("magenta");
        }
    }
}