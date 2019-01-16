package pl.onsight.wypozyczalnia.validator;

import java.text.ParseException;

public interface DateValidator {
    boolean isDateValid(String date) throws ParseException;
}
