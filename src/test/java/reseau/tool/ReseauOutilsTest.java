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
		assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(3000, 2999));
		assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(3000, 3000));
		assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(3000, 65536));
		assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(0, 5));
		assertThrows(IllegalArgumentException.class, () -> ReseauOutils.getPortSocket(-50, 5000000));
		assertDoesNotThrow(() -> {
			ReseauOutils.getPortSocket(65534, 65535);
		});
		assertDoesNotThrow(() -> {
			ReseauOutils.getPortSocket(1024, 65535);
		});
	}
}