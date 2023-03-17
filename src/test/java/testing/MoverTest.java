package testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class MoverTest {
    private Mover uut = new Mover();

    @Test
    public void moverTest() {

        for (Mover.Direction direction1 : Mover.Direction.values()) {
            for (Mover.Direction direction2 : Mover.Direction.values()) {
                LocalDateTime timeBeforeChange = uut.getLastMovingTime();
                Mover.Direction lastDirBeforeChange = uut.getLastDirection();
                uut.setLastDirection(direction1);
                uut.move(direction2);
                if (uut.getLastDirection().equals(Mover.Direction.getOpposite(direction1))) {
                    Assertions.assertNotEquals(uut.getLastDirection(), Mover.Direction.getOpposite(direction2));
                    Assertions.assertNotEquals(uut.getLastMovingTime(), timeBeforeChange);
                } else {
                    uut.setLastMovingTime(timeBeforeChange);
                    uut.setLastDirection(lastDirBeforeChange);
                }
            }
        }
    }
}
