package pl.onsight.wypozyczalnia;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFilter {
    public static Date[] changeStringToDate(String dateString) {
        String[] date = dateString.split(" - ");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            Date firstDate = sdf.parse(date[0] + " 1:00");
            Date secondDate = sdf.parse(date[1] + " 23:59");
            return new Date[]{firstDate, secondDate};
        } catch (Exception e) {
            System.out.println(e);
            return new Date[]{};
        }
    }
}
