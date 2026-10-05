import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas compartidas del Ciclo 4 de SlotMachine.
 */
public class SlotMachineCC4Test {

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
    }

    /**
     * Comprueba que una rueda Lefty copia
     * el color de la rueda que esta a su izquierda.
     */
    @Test
    public void testLeftyCopiaEstado() {
        SlotMachine maquina = new SlotMachine();
        maquina.addWheel("normal", 1);
        maquina.addWheel("lefty", 2);

        maquina.placeSymbol(1, "green");
        maquina.placeSymbol(2, "red");
        maquina.spin(2, 1);

        String[] configuracion = maquina.configuration();

        assertEquals(configuracion[0], configuracion[1]);
    }
}