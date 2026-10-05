import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas unitarias del Ciclo 4 de SlotMachine.
 */
public class SlotMachineC4Test {

    /**
     * Comprueba que una rueda Rebel no puede eliminarse.
     */
    @Test
    public void testRebelNoSePuedeEliminar() {
        SlotMachine maquina = new SlotMachine();
        maquina.addWheel("rebel", 1);

        int cantidadAntes = maquina.configuration().length;
        maquina.delWheel(1);
        int cantidadDespues = maquina.configuration().length;

        assertEquals(cantidadAntes, cantidadDespues);
        assertFalse(maquina.ok());
    }

    /**
     * Comprueba que una rueda Rebel no puede bloquearse.
     */
    @Test
    public void testRebelNoSePuedeBloquear() {
        SlotMachine maquina = new SlotMachine();
        maquina.addWheel("rebel", 1);

        maquina.lock(1);

        assertFalse(maquina.ok());
    }

    /**
     * Comprueba que una rueda Lefty copia el color
     * de la rueda que esta a su izquierda.
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

    /**
     * Comprueba que un simbolo Ephemeral disminuye
     * su tamaño cuando es seleccionado.
     */
    @Test
    public void testEphemeralDisminuye() {
        SimboloEphemeral simbolo = new SimboloEphemeral("red");

        int tamañoInicial = simbolo.obtenerTamaño();
        simbolo.seleccionado();

        assertEquals(tamañoInicial - 1, simbolo.obtenerTamaño());
    }

    /**
     * Comprueba que un simbolo Shy cambia
     * su visibilidad cuando es seleccionado.
     */
    @Test
    public void testShyCambiaVisibilidad() {
        SimboloShy simbolo = new SimboloShy("blue");

        boolean visibilidadInicial = simbolo.estaVisible();
        simbolo.seleccionado();

        assertEquals(!visibilidadInicial, simbolo.estaVisible());
    }
}