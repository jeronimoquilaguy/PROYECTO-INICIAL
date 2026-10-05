import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas de aceptacion del Ciclo 4 de SlotMachine.
 */
public class SlotMachineC4AcceptanceTest {

    /**
     * Comprueba que una rueda Rebel protege su posicion
     * y no puede ser eliminada.
     */
    @Test
    public void testRebelNoSePuedeEliminar() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel("normal", 1);
        maquina.addWheel("rebel", 2);

        maquina.delWheel(2);

        assertEquals(2, maquina.configuration().length);
    }

    /**
     * Comprueba que una rueda Lefty copia el estado
     * de la rueda que tiene a su izquierda.
     */
    @Test
    public void testLeftyCopiaLaRuedaIzquierda() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel("normal", 1);
        maquina.addWheel("lefty", 2);

        maquina.placeSymbol(1, "red");
        maquina.placeSymbol(2, "blue");

        maquina.spin(2, 1);

        String[] configuracion = maquina.configuration();

        assertEquals(configuracion[0], configuracion[1]);
    }
}