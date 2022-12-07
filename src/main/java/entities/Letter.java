package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Letter {
    public static String addHeader(String text) {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\nFrom Raoul, Mario and Alan: \n" + text;
    }

    public static String addFooter(String text) {
        return text + "\nKind regards";
    }

    public static String checkSpelling(String text) {
        return text.replaceAll("labda", "lambda");
    }
}
