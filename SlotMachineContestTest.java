
import static org.junit.Assert.*;
import org.junit.Test;

public class SlotMachineContestTest {

    /**
     * Verifica que la solucion para una maquina de tres ruedas
     * genere una secuencia valida de acciones.
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
     * Verifica que la simulacion de una maquina de tres ruedas
     * pueda ejecutarse correctamente.
     */
    @Test
    public void shouldSimulateThreeWheels() {
        SlotMachineContest concurso = new SlotMachineContest();

        concurso.simulate(3);
    }
    
    /**
     * Verifica que la solucion para una maquina de cuatro ruedas
     * genere una secuencia valida de acciones.
     */
    @Test
    public void shouldSolveFourWheels() {
        SlotMachineContest concurso = new SlotMachineContest();
    
        int[][] acciones = concurso.solve(4);
    
        assertNotNull(acciones);
        assertTrue(acciones.length > 0);
        assertTrue(acciones.length <= 10000);
    }
    
    /**
     * Verifica que la simulacion de una maquina de cuatro ruedas
     * pueda ejecutarse correctamente.
     */
    @Test
    public void shouldSimulateFourWheels() {
        SlotMachineContest concurso = new SlotMachineContest();
    
        concurso.simulate(4);
    }
}