import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Court {

    private final List<Booking> bookings = new LinkedList<>();

    private final Map<LocalDate, boolean[]> bookedHours = new

    void book(Booking booking) throws BookingException {
        bookings.add(booking);
    }

    /**
     * @return 按订单中的时间排序，不论是否被取消
     * 先按起始时刻排序；起始时间相同则按结束时刻排序
     */
    List<Booking> bookings() {
        return new LinkedList<>(bookings);
    }
}
