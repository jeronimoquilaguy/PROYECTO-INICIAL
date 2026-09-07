

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas colectivas del Ciclo 2.
 */
public class SlotMachineCC2Test {

    /**
     * Verifica que una rueda fijada conserve su simbolo
     * mientras otra rueda puede cambiar.
     */
    @Test
    public void accordingJqShouldKeepLockedWheel() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.addWheel(2);

        maquina.placeSymbol(1, "red");
        maquina.placeSymbol(2, "blue");

        maquina.lock(1);
        maquina.spin(2);

        assertEquals("red", maquina.configuration()[0]);
        assertTrue(maquina.ok());
    }

    /**
     * Verifica que swap tambien conserve el estado de fijacion.
     */
    @Test
    public void accordingJqShouldSwapWheelStates() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.addWheel(2);

        maquina.placeSymbol(1, "red");
        maquina.placeSymbol(2, "blue");

        maquina.lock(1);      
        maquina.swap(1, 2);   

        maquina.spin(2);       

        assertEquals("red", maquina.configuration()[1]); 
        assertFalse(maquina.ok());                       
    }
}