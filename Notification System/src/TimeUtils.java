import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String getCurrentTime(){
        LocalDateTime currTimeAndDate = LocalDateTime.now();
        return  currTimeAndDate.format(DateTimeFormatter.ofPattern("\nEEE dd MMM yyyy\n HH:mm:ss \n"));
    }
}
