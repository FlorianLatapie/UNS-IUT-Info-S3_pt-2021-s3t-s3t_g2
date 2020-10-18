package reseau.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NetworkToolTest {

    @Test
    void isBindSocket() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NetworkTool.isBindSocket(0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NetworkTool.isBindSocket(-1);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NetworkTool.isBindSocket(65536);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NetworkTool.isBindSocket(100000);
        });
    }

    @Test
    void getPortSocket() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NetworkTool.getPortSocket(0, -1);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NetworkTool.getPortSocket(-10, 10000);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NetworkTool.getPortSocket(65536, 10);
        });
        Assertions.assertDoesNotThrow(() -> {
            NetworkTool.getPortSocket(65534, 1);
        });
    }
}