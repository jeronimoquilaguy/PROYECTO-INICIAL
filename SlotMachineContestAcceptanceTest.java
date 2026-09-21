
import static org.junit.Assert.*;
import org.junit.Test;

public class SlotMachineContestAcceptanceTest {
    
    /**
     * Verifica como prueba de aceptacion que el solucionador
     * genere acciones validas para una maquina de tres ruedas.
     */
    @Test
    public void acceptanceTestThreeWheels() {
        SlotMachineContest concurso = new SlotMachineContest();

        int[][] acciones = concurso.solve(3);

        assertNotNull(acciones);
        assertTrue(acciones.length > 0);
        assertTrue(acciones.length <= 10000);
    }

    /**
     * Verifica como prueba de aceptacion que el solucionador
     * genere acciones validas para una maquina de cinco ruedas.
     */
    @Test
    public void acceptanceTestFiveWheels() {
        SlotMachineContest concurso = new SlotMachineContest();

        int[][] acciones = concurso.solve(5);

        assertNotNull(acciones);
        assertTrue(acciones.length > 0);
        assertTrue(acciones.length <= 10000);
    }
}