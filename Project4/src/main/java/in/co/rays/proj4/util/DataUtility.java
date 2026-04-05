import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtility {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(DATE_FORMAT);

    private static final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static String getString(String val) {
        return (val != null) ? val.trim() : null;
    }

    public static int getInt(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            return 0;
        }
    }

    public static LocalDate getDate(String val) {
        try {
            return LocalDate.parse(val, dateFormatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDateString(LocalDate date) {
        return (date != null) ? date.format(dateFormatter) : "";
    }

    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}