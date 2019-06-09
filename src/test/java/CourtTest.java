import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CourtTest {

    @Test
    public void booking_is_accepted_then_can_be_queried() throws BookingException {
        Court court = new Court();

        court.book(new Booking("U123", LocalDate.of(2016, 6, 2), 20, 22));

        assertEquals(Collections.singletonList(
                new Booking("U123", LocalDate.of(2016, 6, 2), 20, 22, true)
        ), court.bookings());
    }

    @Test
    public void new_booking_conflicts_with_existing_booking_then_reject() throws BookingException {
        Court court = new Court();
        court.book(new Booking("U123", LocalDate.of(2016, 6, 2), 20, 22));

        try {
            court.book(new Booking("U222", LocalDate.of(2016, 6, 2), 19, 21));
            fail();

        }catch (BookingException e){
            assertEquals("Error: the booking conflicts with existing bookings!", e.getMessage());
        }
    }
}
