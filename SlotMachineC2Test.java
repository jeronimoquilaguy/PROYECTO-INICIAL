import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas del Ciclo 2 de SlotMachine.
 *
 * Todas las pruebas se realizan con la maquina invisible.
 */
public class SlotMachineC2Test {

    /**
     * Prueba que swap intercambia correctamente dos ruedas.
     */
    @Test
    public void accordingJqShouldSwapWheels() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.addWheel(2);

        maquina.placeSymbol(1, "red");
        maquina.placeSymbol(2, "blue");

        maquina.swap(1, 2);

        assertEquals("blue", maquina.configuration()[0]);
        assertEquals("red", maquina.configuration()[1]);
        assertTrue(maquina.ok());
    }

    /**
     * Prueba que una rueda fijada no puede girar.
     */
    @Test
    public void accordingJqShouldNotSpinLockedWheel() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.placeSymbol(1, "red");

        maquina.lock(1);
        maquina.spin(1);

        assertEquals("red", maquina.configuration()[0]);
        assertFalse(maquina.ok());
    }

    /**
     * Prueba que una rueda desbloqueada vuelve a poder modificarse.
     */
    @Test
    public void accordingJqShouldUnlockWheel() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.placeSymbol(1, "red");

        maquina.lock(1);
        maquina.unlock(1);
        maquina.placeSymbol(1, "blue");

        assertEquals("blue", maquina.configuration()[0]);
        assertTrue(maquina.ok());
    }

    /**
     * Prueba el giro por una cantidad determinada de pasos.
     */
    @Test
    public void accordingJqShouldSpinBySteps() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.placeSymbol(1, "red");

        maquina.spin(1, 1);

        assertEquals("blue", maquina.configuration()[0]);
        assertTrue(maquina.ok());
    }

    /**
     * Prueba que spin por pasos no acepta pasos negativos.
     */
    @Test
    public void accordingJqShouldNotSpinNegativeSteps() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.placeSymbol(1, "red");

        maquina.spin(1, -1);

        assertEquals("red", maquina.configuration()[0]);
        assertFalse(maquina.ok());
    }

    /**
     * Prueba la configuracion forzada.
     */
    @Test
    public void accordingJqShouldSetConfiguration() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.addWheel(2);
        maquina.addWheel(3);

        String[] configuracion = {"red", "blue", "green"};

        maquina.spin(configuracion);

        assertArrayEquals(
            configuracion,
            maquina.configuration()
        );

        assertTrue(maquina.ok());
    }

    /**
     * Prueba que una posicion fuera de rango produzca error.
     */
    @Test
    public void accordingJqShouldRejectInvalidWheelPosition() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);

        maquina.spin(2);

        assertFalse(maquina.ok());
    }

    /**
     * Prueba que una configuracion con un simbolo inexistente sea rechazada.
     */
    @Test
    public void accordingJqShouldRejectInvalidSymbolConfiguration() {
        SlotMachine maquina = new SlotMachine();

        maquina.addWheel(1);
        maquina.addWheel(2);

        String[] configuracion = {"red", "purple"};

        maquina.spin(configuracion);

        assertEquals("red", maquina.configuration()[0]);
        assertEquals("red", maquina.configuration()[1]);
        assertFalse(maquina.ok());
    }
}


