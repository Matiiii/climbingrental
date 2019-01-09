package pl.sda.javapoz;

public class DateFilter {

    public static String[] filterData(String data) {
        String dates[] = data.split("-");
        return dates;
    }
}
