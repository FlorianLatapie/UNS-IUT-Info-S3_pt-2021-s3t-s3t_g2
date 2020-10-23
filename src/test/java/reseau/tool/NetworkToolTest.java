package reseau.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkToolTest {

    @Test
    void isBindSocket() {
        assertThrows(IllegalArgumentException.class, () -> NetworkTool.isBindSocket(0));
        assertThrows(IllegalArgumentException.class, () -> NetworkTool.isBindSocket(-1));
        assertThrows(IllegalArgumentException.class, () -> NetworkTool.isBindSocket(65536));
        assertThrows(IllegalArgumentException.class, () -> NetworkTool.isBindSocket(100000));
    }

    @Test
    void getPortSocket() {
        assertThrows(IllegalArgumentException.class, () -> NetworkTool.getPortSocket(0, -1));
        assertThrows(IllegalArgumentException.class, () -> NetworkTool.getPortSocket(-10, 10000));
        assertThrows(IllegalArgumentException.class, () -> NetworkTool.getPortSocket(65536, 10));
        assertDoesNotThrow(() -> {
            NetworkTool.getPortSocket(65534, 1);
        });
    }
}