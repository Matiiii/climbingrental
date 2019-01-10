package pl.onsight.wypozyczalnia;

public class DateFilter {

    public static String[] filterData(String data) {
        String dates[] = data.split("-");
        return dates;
    }
}
