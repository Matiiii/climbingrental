package pl.onsight.wypozyczalnia.validator.impl;

import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.DateFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateValidatorImpl implements pl.onsight.wypozyczalnia.validator.DateValidator {

    @Override
    public boolean isDateValid(String dates) throws ParseException {
        return isDatesCorrectToParse(dates) && areDatesCorrect(dates) ;
    }
    private boolean areDatesCorrect(String dates) throws ParseException {
        Date[] datesArray = DateFilter.changeStringToDate(dates);
        return areDatesInFuture(datesArray[0], datesArray[1]) && isDateStartBeforeDateEnd(datesArray[0], datesArray[1]);
    }

    private boolean areDatesInFuture(Date start, Date end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date today = sdf.parse(sdf.format(new Date()).split(" ")[0]+ " 00:00");
        return !start.before(today) && !end.before(today);
    }

    private boolean isDateStartBeforeDateEnd(Date start, Date end){
        return start.before(end);
    }

    private boolean isDateNotEmpty(String dates){
        return dates != null && !dates.isEmpty();
    }

    private boolean isStringContainRegexToSplit(String dates){
        return dates.contains(" - ");
    }

    private boolean isDatesCorrectToParse(String dates){
        if(isDateNotEmpty(dates) && isStringContainRegexToSplit(dates)){
            return DateFilter.changeStringToDate(dates).length > 0;
        }
        return false;
    }
}
