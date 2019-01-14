package pl.onsight.wypozyczalnia;

import java.util.Date;

public class DateFilter {
    public static Date[] changeStringToDate(String dateString) {
        String[] date = dateString.split("-");
        return new Date[]{new Date(date[0]), new Date(date[1])};
    }
}
