package reseau.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadToolTest {
    boolean asyncTask;
    boolean asyncTask1 = true;

    @Test
    void asyncTask() {
        Assertions.assertDoesNotThrow(() -> {
            Runnable t1 = () -> {
                this.asyncTask = !this.asyncTask;
                Assertions.assertTrue(this.asyncTask);
            };
            Runnable t2 = () -> {
                this.asyncTask1 = !this.asyncTask1;
                Assertions.assertFalse(this.asyncTask1);
            };
            ThreadTool.asyncTask(t1, t2);
        });
    }
}