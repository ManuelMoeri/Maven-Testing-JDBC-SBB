package testing;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeOfDayTest {
    private TimeOfDay uut = new TimeOfDay();

    @Test
    public void timeOfDayTest() {
        LocalDateTime localDateTimeNight = LocalDateTime.of(2023, 1, 19, 4, 0);
        LocalDateTime localDateTimeMorning = LocalDateTime.of(2023, 1, 19, 8, 0);
        LocalDateTime localDateTimeAfternoon = LocalDateTime.of(2023, 1, 19, 14, 0);
        LocalDateTime localDateTimeEvening = LocalDateTime.of(2023, 1, 19, 19, 0);

        String returnedTimeNight = uut.getTimeOfDay(localDateTimeNight);
        String returnedTimeMorning = uut.getTimeOfDay(localDateTimeMorning);
        String returnedTimeAfternoon = uut.getTimeOfDay(localDateTimeAfternoon);
        String returnedTimeEvening = uut.getTimeOfDay(localDateTimeEvening);

        assertEquals("Night", returnedTimeNight);
        assertEquals("Morning", returnedTimeMorning);
        assertEquals("Afternoon", returnedTimeAfternoon);
        assertEquals("Evening", returnedTimeEvening);
    }

}
