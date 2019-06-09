import java.time.LocalDate;

class Booking {

    private final String userId;
    private final LocalDate date;
    private final int startHourInclusive;
    private final int endHourExclusive;
    private boolean isValid;

    Booking(String userId, LocalDate date, int startHourInclusive, int endHourExclusive) {
        this(userId, date, startHourInclusive, endHourExclusive, true);
    }

    Booking(String userId, LocalDate date, int startHourInclusive, int endHourExclusive, boolean isValid) {
        this.userId = userId;
        this.date = date;
        this.startHourInclusive = startHourInclusive;
        this.endHourExclusive = endHourExclusive;
        this.isValid = isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (startHourInclusive != booking.startHourInclusive) return false;
        if (endHourExclusive != booking.endHourExclusive) return false;
        if (isValid != booking.isValid) return false;
        //noinspection SimplifiableIfStatement
        if (userId != null ? !userId.equals(booking.userId) : booking.userId != null) return false;
        return date != null ? date.equals(booking.date) : booking.date == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + startHourInclusive;
        result = 31 * result + endHourExclusive;
        result = 31 * result + (isValid ? 1 : 0);
        return result;
    }
}
