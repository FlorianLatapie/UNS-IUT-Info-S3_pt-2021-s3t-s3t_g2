package reseau.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadToolTest {
    boolean asyncTask;
    boolean asyncTask1 = true;

    @Test
    void asyncTask() {
        assertDoesNotThrow(() -> {
            Runnable t1 = () -> {
                this.asyncTask = !this.asyncTask;
                assertTrue(this.asyncTask);
            };
            Runnable t2 = () -> {
                this.asyncTask1 = !this.asyncTask1;
                assertFalse(this.asyncTask1);
            };
            ThreadOutils.asyncTask(t1, t2);
        });
    }
}