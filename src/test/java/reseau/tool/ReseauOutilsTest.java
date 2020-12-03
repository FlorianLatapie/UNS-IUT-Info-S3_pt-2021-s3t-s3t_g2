package reseau.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReseauOutilsTest {

    @Test
    void isBindSocket() {
        assertThrows(IllegalArgumentException.class, () -> ReseauOutils.isBindSocket(0));
        assertThrows(IllegalArgumentException.class, () -> ReseauOutils.isBindSocket(-1));
        assertThrows(IllegalArgumentException.class, () -> ReseauOutils.isBindSocket(65536));
        assertThrows(IllegalArgumentException.class, () -> ReseauOutils.isBindSocket(100000));
    }

    @Test
    void getPortSocket() {
        assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(0, -1));
        assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(-10, 10000));
        assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(65536, 10));
        assertDoesNotThrow(() -> {
            ReseauOutils.getPortSocket(65534, 1);
        });
    }
}