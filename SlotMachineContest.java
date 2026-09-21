import java.util.ArrayList;

/**
 * Resuelve el problema de la maraton de Slot Machine.
 */
public class SlotMachineContest {

    /**
     * Resuelve el problema de la maquina tragamonedas de manera invisible.
     *
     * @param n numero de ruedas de la maquina.
     * @return secuencia de acciones, donde cada accion contiene la rueda
     *         y la cantidad de pasos que debe girar.
     */
    public int[][] solve(int n) {
        SlotMachine maquina = new SlotMachine(n);
        maquina.makeInvisible();

        return resolver(maquina, n);
    }

    /**
     * Simula visualmente la solucion del problema de la maquina tragamonedas.
     *
     * @param n numero de ruedas de la maquina.
     */
    public void simulate(int n) {
        SlotMachine maquina = new SlotMachine(n);
        maquina.makeVisible();

        resolver(maquina, n);
    }

    /**
     * Calcula la secuencia de acciones necesaria para resolver la maquina.
     *
     * @param maquina maquina tragamonedas que se utiliza como herramienta.
     * @param n numero de ruedas de la maquina.
     * @return lista de acciones necesarias para obtener una configuracion ganadora.
     */
    private int[][] resolver(SlotMachine maquina, int n) {

        ArrayList<int[]> acciones = new ArrayList<>();
        int[] posicionesActuales = new int[n];

        int[] memoria = escanear(
            maquina,
            1,
            n,
            acciones,
            posicionesActuales
        );

        int posicionS = -1;
        int posicionR = -1;

        for (int i = 0; i < n; i++) {
            if (memoria[i] == 0 && posicionS == -1) {
                posicionS = i;
            }

            if (memoria[i] == 1 && posicionR == -1) {
                posicionR = i;
            }
        }

        if (posicionS == -1) {
            posicionS = 0;
        }

        if (posicionR == -1) {
            posicionR = (posicionS + 1) % n;
        }

        girarHasta(
            maquina,
            1,
            posicionS,
            n,
            acciones,
            posicionesActuales
        );

        int[] posiciones = new int[n];
        posiciones[0] = posicionS;

        for (int rueda = 2; rueda <= n; rueda++) {

            int[] conS = escanear(
                maquina,
                rueda,
                n,
                acciones,
                posicionesActuales
            );

            girarHasta(
                maquina,
                1,
                posicionR,
                n,
                acciones,
                posicionesActuales
            );

            int[] conR = escanear(
                maquina,
                rueda,
                n,
                acciones,
                posicionesActuales
            );

            int encontrada = -1;

            for (int i = 0; i < n; i++) {
                if (conS[i] == 1 && conR[i] == 0) {
                    encontrada = i;
                    break;
                }
            }

            if (encontrada == -1) {
                for (int i = 0; i < n; i++) {
                    if (conS[i] != conR[i]) {
                        encontrada = i;
                        break;
                    }
                }
            }

            if (encontrada == -1) {
                encontrada = 0;
            }

            posiciones[rueda - 1] = encontrada;

            girarHasta(
                maquina,
                1,
                posicionS,
                n,
                acciones,
                posicionesActuales
            );
        }

        for (int rueda = 1; rueda <= n; rueda++) {
            girarHasta(
                maquina,
                rueda,
                posiciones[rueda - 1],
                n,
                acciones,
                posicionesActuales
            );
        }

        int[][] resultado = new int[acciones.size()][2];

        for (int i = 0; i < acciones.size(); i++) {
            resultado[i][0] = acciones.get(i)[0];
            resultado[i][1] = acciones.get(i)[1];
        }

        return resultado;
    }

        /**
     * Escanea una rueda girandola una posicion a la vez y registra
     * la cantidad de simbolos distintos observados.
     *
     * @param maquina maquina tragamonedas utilizada para el escaneo.
     * @param rueda numero de la rueda que se va a escanear.
     * @param n numero de posiciones de la rueda.
     * @param acciones lista donde se registran las acciones realizadas.
     * @param posicionesActuales posiciones conocidas de las ruedas.
     * @return arreglo que indica las posiciones que corresponden al minimo
     *         numero de simbolos distintos.
     */
    private int[] escanear(
        SlotMachine maquina,
        int rueda,
        int n,
        ArrayList<int[]> acciones,
        int[] posicionesActuales
    ) {

        int[] resultados = new int[n];

        for (int i = 0; i < n; i++) {

            resultados[i] = maquina.distinctSymbols();

            girar(
                maquina,
                rueda,
                1,
                n,
                acciones,
                posicionesActuales
            );
        }

        int minimo = resultados[0];

        for (int i = 1; i < n; i++) {
            if (resultados[i] < minimo) {
                minimo = resultados[i];
            }
        }

        int[] memoria = new int[n];

        for (int i = 0; i < n; i++) {
            if (resultados[i] == minimo) {
                memoria[i] = 1;
            } else {
                memoria[i] = 0;
            }
        }

        return memoria;
    }

    /**
     * Gira una rueda hasta alcanzar una posicion objetivo utilizando
     * la cantidad de pasos mas conveniente.
     *
     * @param maquina maquina tragamonedas utilizada.
     * @param rueda numero de la rueda que se va a girar.
     * @param objetivo posicion objetivo de la rueda.
     * @param n numero de posiciones de la rueda.
     * @param acciones lista donde se registra la accion realizada.
     * @param posicionesActuales posiciones conocidas de las ruedas.
     */
    private void girarHasta(
        SlotMachine maquina,
        int rueda,
        int objetivo,
        int n,
        ArrayList<int[]> acciones,
        int[] posicionesActuales
    ) {

        int actual = posicionesActuales[rueda - 1];

        int pasos = (objetivo - actual) % n;

        if (pasos < 0) {
            pasos += n;
        }

        if (pasos > n / 2) {
            pasos -= n;
        }

        girar(
            maquina,
            rueda,
            pasos,
            n,
            acciones,
            posicionesActuales
        );
    }

        /**
     * Gira una rueda la cantidad de pasos indicada y registra la accion.
     *
     * @param maquina maquina tragamonedas utilizada.
     * @param rueda numero de la rueda que se va a girar.
     * @param pasos cantidad de pasos que debe girar la rueda.
     * @param n numero de posiciones de la rueda.
     * @param acciones lista donde se registra la accion realizada.
     * @param posicionesActuales posiciones conocidas de las ruedas.
     */
    private void girar(
        SlotMachine maquina,
        int rueda,
        int pasos,
        int n,
        ArrayList<int[]> acciones,
        int[] posicionesActuales
    ) {

        if (pasos == 0) {
            return;
        }

        maquina.spin(rueda, pasos);

        acciones.add(new int[] {rueda, pasos});

        posicionesActuales[rueda - 1] =
            (posicionesActuales[rueda - 1] + pasos) % n;

        if (posicionesActuales[rueda - 1] < 0) {
            posicionesActuales[rueda - 1] += n;
        }
    }
}