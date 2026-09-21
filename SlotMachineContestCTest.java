import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas compartidas del concurso de SlotMachine.
 */
public class SlotMachineContestCTest {

    /**
     * Verifica de forma compartida que la solucion para tres ruedas
     * genere una secuencia valida de acciones dentro del limite permitido.
     */
    @Test
    public void shouldSolveThreeWheels() {
        SlotMachineContest concurso = new SlotMachineContest();

        int[][] acciones = concurso.solve(3);

        assertNotNull(acciones);
        assertTrue(acciones.length > 0);
        assertTrue(acciones.length <= 10000);

        for (int i = 0; i < acciones.length; i++) {
            assertEquals(2, acciones[i].length);

            assertTrue(
                acciones[i][0] >= 1 &&
                acciones[i][0] <= 3
            );

            assertTrue(acciones[i][1] != 0);
        }
    }

    /**
     * Verifica de forma compartida que la solucion para cuatro ruedas
     * genere una secuencia valida de acciones dentro del limite permitido.
     */
    @Test
    public void shouldSolveFourWheels() {
        SlotMachineContest concurso = new SlotMachineContest();

        int[][] acciones = concurso.solve(4);

        assertNotNull(acciones);
        assertTrue(acciones.length > 0);
        assertTrue(acciones.length <= 10000);

        for (int i = 0; i < acciones.length; i++) {
            assertEquals(2, acciones[i].length);

            assertTrue(
                acciones[i][0] >= 1 &&
                acciones[i][0] <= 4
            );

            assertTrue(acciones[i][1] != 0);
        }
    }
    
    /**
     * Verifica de forma compartida que la solucion para cinco ruedas
     * genere una secuencia valida de acciones dentro del limite permitido.
     */
    @Test
    public void shouldSolveFiveWheels() {
        SlotMachineContest concurso = new SlotMachineContest();
    
        int[][] acciones = concurso.solve(5);
    
        assertNotNull(acciones);
        assertTrue(acciones.length > 0);
        assertTrue(acciones.length <= 10000);
    }
}