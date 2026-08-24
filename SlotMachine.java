
/**
 * Simulador de una Máquina Tragamonedas.
 * 
 * Jerónimo Quilaguy-Juan Roa
 * version 1
 */
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Simulador Visual de una Máquina Tragamonedas.
 */
public class SlotMachine {
    /** Indica si la máquina tragamonedas es visible en el lienzo. */
    private boolean isVisible;
    /** Indica si la última operación ejecutada fue exitosa. */
    private boolean isOk;
    /** Lista que almacena las ruedas (WheelObject) de la máquina. */
    private ArrayList<WheelObject> wheels;
    /** Catálogo de colores (símbolos) disponibles en la máquina. */
    private ArrayList<String> symbolCatalog;
    
    /** Posición X inicial en el lienzo para dibujar la primera rueda. */
    private final int START_X = 50;
    /** Espacio en píxeles entre cada rueda. */
    private final int SPACING = 60;

    /**
     * Constructor para inicializar la máquina.
     */
    public SlotMachine() {
        this.isVisible = false;
        this.isOk = true;
        this.wheels = new ArrayList<>();
        this.symbolCatalog = new ArrayList<>();
        
        // Símbolos base de prueba 
        this.symbolCatalog.add("red");
        this.symbolCatalog.add("blue");
        this.symbolCatalog.add("green");
    }
    /**
     * Adiciona una rueda en la posición indicada.
     * @param pos La posición donde se insertará la rueda.
     */
    public void addWheel(int pos) {
        int index = fixIndex(pos, wheels.size() + 1);
        String initialColor = symbolCatalog.isEmpty() ? "black" : symbolCatalog.get(0);
        WheelObject newWheel = new WheelObject(initialColor);
        wheels.add(index, newWheel);
        refreshVisuals();
        isOk = true;
    }
    /**
     * Elimina una rueda en la posición indicada.
     * @param pos La posición de la rueda a eliminar.
     */
    public void delWheel(int pos) {
        if (wheels.isEmpty()) {
            reportError("No hay ruedas para eliminar.");
            return;
        }
        int index = fixIndex(pos, wheels.size());
        WheelObject removed = wheels.remove(index);
        removed.makeInvisible();
        refreshVisuals();
        isOk = true;
    }
    /**
     * Adiciona un símbolo (color) al catálogo en la posición indicada.
     * @param pos La posición donde se insertará el símbolo.
     * @param color El nombre del color del símbolo.
     */
    public void addSymbol(int pos, String color) {
        int index = fixIndex(pos, symbolCatalog.size() + 1);
        symbolCatalog.add(index, color);
        isOk = true;
    }
    /**
     * Elimina un símbolo del catálogo por su nombre.
     * @param symbol El color del símbolo a eliminar.
     */
    public void delSymbol(String symbol) {
        if (symbolCatalog.contains(symbol)) {
            symbolCatalog.remove(symbol);
            isOk = true;
        } else {
            reportError("El símbolo no existe en la máquina.");
        }
    }
    /**
     * Ubica un símbolo específico en una rueda determinada.
     * @param wheel La posición de la rueda.
     * @param symbol El color del símbolo a ubicar.
     */
    public void placeSymbol(int wheel, String symbol) {
        if (wheels.isEmpty()) {
            reportError("No hay ruedas creadas.");
            return;
        }
        if (!symbolCatalog.contains(symbol)) {
            reportError("El símbolo no es válido.");
            return;
        }
        int index = fixIndex(wheel, wheels.size());
        wheels.get(index).setColor(symbol);
        isOk = true;
    }
    /**
     * Gira una sola rueda de la máquina, asignándole un símbolo aleatorio.
     * @param wheel La posición de la rueda a girar.
     */
    public void spin(int wheel) {
        if (wheels.isEmpty() || symbolCatalog.isEmpty()) {
            reportError("Faltan ruedas o símbolos para girar.");
            return;
        }
        int index = fixIndex(wheel, wheels.size());
        // Simulación básica de giro: selecciona un color aleatorio del catálogo
        int randomSym = (int) (Math.random() * symbolCatalog.size());
        wheels.get(index).setColor(symbolCatalog.get(randomSym));
        checkJackpotVisuals();
        isOk = true;
    }
    /**
     * Gira todas las ruedas de la máquina.
     */
    public void spin() {
        if (wheels.isEmpty()) {
            reportError("No hay ruedas para girar.");
            return;
        }
        for (int i = 1; i <= wheels.size(); i++) {
            spin(i); // Reutiliza el método individual
        }
    }
    /**
     * Retorna los colores de los símbolos disponibles en el catálogo.
     * @return Arreglo de cadenas con los nombres de los colores.
     */
    public String[] symbols() {
        return symbolCatalog.toArray(new String[0]);
    }
    /**
     * Cuenta la cantidad de símbolos distintos configurados en la máquina.
     * @return El número de símbolos únicos.
     */
    public int distinctSymbols() {
        HashSet<String> unique = new HashSet<>(symbolCatalog);
        return unique.size();
    }
    /**
     * Retorna los colores visibles actualmente en todas las ruedas.
     * @return Arreglo de cadenas con los colores ordenados de izquierda a derecha.
     */
    public String[] configuration() {
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            config[i] = wheels.get(i).getColor();
        }
        return config;
    }
    /**
     * Verifica si la configuración actual es ganadora (todos los símbolos iguales).
     * @return true si es ganadora, false en caso contrario.
     */
    public boolean isjackpot() {
        if (wheels.size() < 2) return false;
        String first = wheels.get(0).getColor();
        for (WheelObject w : wheels) {
            if (!w.getColor().equals(first)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Hace visible el simulador en pantalla.
     */
    public void makeVisible() {
        isVisible = true;
        for (WheelObject w : wheels) w.makeVisible();
        checkJackpotVisuals();
        isOk = true;
    }
    /**
     * Hace invisible el simulador.
     */
    public void makeInvisible() {
        isVisible = false;
        for (WheelObject w : wheels) w.makeInvisible();
        isOk = true;
    }
    /**
     * Termina la ejecución del simulador.
     */
    public void exit() {
        System.exit(0);
    }
    /**
     * Consulta si la última operación realizada fue exitosa.
     * @return true si la operación tuvo éxito, false si hubo un error.
     */
    public boolean ok() {
        return isOk;
    }

    // --- MÉTODOS PRIVADOS DE APOYO ---
    
    /**
     * Ajusta el índice ingresado por el usuario al formato 0-based de Java.
     * Si el índice es menor a 1, se ajusta a 1. Si es mayor al máximo, se ajusta al máximo.
     * @param pos La posición indicada por el usuario.
     * @param max El tamaño máximo permitido.
     * @return El índice ajustado para usar en colecciones de Java.
     */
    private int fixIndex(int pos, int max) {
        if (pos < 1) pos = 1;
        if (pos > max) pos = max;
        return pos - 1; 
    }
    /**
     * Reporta un error modificando el estado ok() y mostrando un mensaje en pantalla.
     * El mensaje solo se muestra si la máquina está visible.
     * @param message El mensaje de error a mostrar.
     */
    private void reportError(String message) {
        isOk = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    /**
     * Actualiza la posición de todas las ruedas en el lienzo.
     */
    private void refreshVisuals() {
        int currentX = START_X;
        for (WheelObject w : wheels) {
            w.setPosition(currentX);
            if (isVisible) w.makeVisible();
            currentX += SPACING;
        }
    }
    /**
     * Cambia el aspecto visual de la máquina si se alcanza el estado ganador.
     */
    private void checkJackpotVisuals() {
        if (isjackpot() && isVisible) {
            // Requisito: La máquina debe lucir diferente si ganó
            for (WheelObject w : wheels) {
                w.setBackground("yellow");
            }
        } else if (isVisible) {
            for (WheelObject w : wheels) {
                w.setBackground("magenta"); // Color por defecto del Rectangle en shapes
            }
        }
    }

    // --- CLASE INTERNA PRIVADA ---
    
    /**
     * Clase que representa una rueda individual de la máquina.
     * Encapsula los componentes gráficos (Rectangle y Circle) y su estado.
     */
    private class WheelObject {
        /** Fondo visual de la rueda. */
        private Rectangle bg;
        /** Símbolo visual de la rueda. */
        private Circle sym;
        /** Color actual del símbolo. */
        private String currentColor;
        
        // Guardamos la posición absoluta en X de cada figura
        private int currentBgX;
        private int currentSymX;
        /**
         * Constructor para crear una nueva rueda.
         * @param color El color inicial del símbolo.
         */
        public WheelObject(String color) {
            this.currentColor = color;
            
            bg = new Rectangle();
            bg.changeSize(80, 50); // Alto, Ancho
            this.currentBgX = 70;  // Posición X inicial por defecto de Rectangle
            
            sym = new Circle();
            sym.changeSize(30);
            sym.changeColor(color);
            this.currentSymX = 20; // Posición X inicial por defecto de Circle
        }
        /**
         * Establece la posición horizontal de la rueda en el lienzo.
         * @param targetX La coordenada X destino.
         */
        public void setPosition(int targetX) {
            // Calculamos la distancia exacta que le falta al rectángulo para llegar a targetX
            int distanceBg = targetX - currentBgX;
            bg.moveHorizontal(distanceBg);
            currentBgX = targetX;
            
            // Calculamos la distancia exacta para centrar el círculo (+10 píxeles de margen)
            int targetSymX = targetX + 10; 
            int distanceSym = targetSymX - currentSymX;
            sym.moveHorizontal(distanceSym);
            currentSymX = targetSymX;
        }
        /**
         * Cambia el color del símbolo de la rueda.
         * @param color El nuevo color.
         */
        public void setColor(String color) {
            this.currentColor = color;
            sym.changeColor(color);
        }
        /**
         * Obtiene el color actual del símbolo de la rueda.
         * @return El color como cadena.
         */
        public String getColor() {
            return currentColor;
        }
        /**
         * Cambia el color de fondo de la rueda (el rectángulo).
         * @param color El nuevo color de fondo.
         */
        public void setBackground(String color) {
            bg.changeColor(color);
            // TRUCO DE CAPAS: Al repintar el rectángulo, este se vino al frente.
            // Volvemos a repintar el círculo inmediatamente para que no quede oculto.
            sym.changeColor(this.currentColor);
        }
        /**
         * Hace visible la rueda en el lienzo.
         */
        public void makeVisible() {
            bg.makeVisible();
            sym.makeVisible(); // Llamamos al símbolo de último para que quede arriba
        }
        /**
         * Hace invisible la rueda en el lienzo.
         */
        public void makeInvisible() {
            sym.makeInvisible();
            bg.makeInvisible();
        }
    }
}