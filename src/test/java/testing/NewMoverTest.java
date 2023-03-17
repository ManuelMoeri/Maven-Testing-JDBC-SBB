package testing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewMoverTest {
    private Mover uut = new Mover();
    @Test
    public void oppositeDirection() {
        Mover.Direction directionNorth = Mover.Direction.NORTH;
        Mover.Direction directionEast = Mover.Direction.EAST;
        Mover.Direction directionSouth = Mover.Direction.SOUTH;
        Mover.Direction directionWest = Mover.Direction.WEST;

        assertEquals(Mover.Direction.SOUTH, Mover.Direction.getOpposite(directionNorth));
        assertEquals(Mover.Direction.WEST, Mover.Direction.getOpposite(directionEast));
        assertEquals(Mover.Direction.NORTH, Mover.Direction.getOpposite(directionSouth));
        assertEquals(Mover.Direction.EAST, Mover.Direction.getOpposite(directionWest));
    }
}
