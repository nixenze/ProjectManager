import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
/**
 * CalendarFormatManger class for define the standard of datetime and parsing back and forth
 *
 * create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */
public class CalendarFormatManager 
{
    private static String datePattern1 = "yyyy MM dd";
    private static String datePattern2 = "yyyy MMM dd";

    /**
     * get date pattern
     * @param choose
     */
    public static String getDatePattern(int choose) 
    {
        if (choose == 1)
            return datePattern1;
        else
            return datePattern2;
    }

    /**
     * parsing string to calendar class
     * @param stringDate string to parse
     * @return Calendar
     */
    public static Calendar stringToCalendar(String stringDate) 
    {
        if (stringDate == null) 
        {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern1);
        Calendar calendar = new GregorianCalendar();
        try 
        {
            calendar.setTime(sdf.parse(stringDate));
        } catch (ParseException pe) 
        {
            IOmanager.print("Wrong format date!!");
            calendar = null;
        }
        return calendar;
    }

    /**
     * parsing string to calendar class by specific format 
     * @param stringDate string to parse
     * @return Calendar
     */
    public static Calendar stringToCalendar(String stringDate,String datePattern) 
    {
        if (stringDate == null) 
        {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        Calendar calendar = new GregorianCalendar();
        try 
        {
            calendar.setTime(sdf.parse(stringDate));
        } catch (ParseException pe) 
        {
            IOmanager.print("Wrong format date!!");
            calendar = null;
        }
        return calendar;
    }
    /**
     * parsing calendar to string class
     * @param calendar string to parse
     * @return String
     */
    public static String calendarToString(Calendar calendar) 
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern2);
        String calendarString;
        try 
        {
            calendarString = simpleDateFormat.format(calendar.getTime());
        } catch (Exception e) 
        {
            //TODO: handle exception
            //IOmanager.print("Parse Error!");
            calendarString = "Invalid date format";
        }

        return calendarString;
    }
}