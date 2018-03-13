import java.io.*;

/**
 * IOmanager class for defining printing getting parsing standard and prevent typing errors
 *
 *  create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */
public class IOmanager 
{

    /**
     * print string
     * @param temp String to print
     */
    public static void print(String temp) 
    {
        System.out.println(temp);
    }
    /**
     * overload method for print integer
     * @param temp int to print
     */
    public static void print(int temp) 
    {
        String buffer = Integer.toString(temp);
        System.out.println(buffer);
    }

    /**
     * overload project for print without newline
     * @param temp String to print
     * @param boolean false to print without newline
     */
    public static void print(String temp, boolean newLine) 
    {
        if (newLine) 
        {

        } 
        else 
        {
            System.out.print(temp);
        }

    }

    /**
     * Original by Aj. Sally
     * modified for this project
     * Asks for long string, and returns it
     * as the function value.
     * @param   prompt    String to print, telling which coordinate
     * @return  the string value entered. without terminator
     */
    protected static String getLongString() 
    {
        String longString = new String();
        while (true) 
        {
            String inputString;
            int readBytes = 0;
            byte buffer[] = new byte[200];
            try 
            {
                readBytes = System.in.read(buffer, 0, 200);
            } catch (IOException ioe) 
            {
                System.out.println("Input/output exception - Exiting");
            }
            inputString = new String(buffer);
            /* modify to work for both Windows and Linux */
            int pos = inputString.indexOf("\r");
            if (pos < 0)
                pos = inputString.indexOf("\n");
            inputString = inputString.substring(0, pos);

            if (inputString.compareTo("end") == 0) 
            {
                return longString;
            } 
            else 
            {
                longString += inputString + "\n";
            }

        }

    }

    /**
     * Original by Aj. Sally
     * modified for this project
     * Asks for a string, and returns it
     * as the function value.
     * @param   prompt    String to print, telling which coordinate
     * @return  the string value entered, without a newline 
     */
    protected static String getOneString() 
    {
        String inputString;
        int readBytes = 0;
        byte buffer[] = new byte[200];
        try 
        {
            readBytes = System.in.read(buffer, 0, 200);
        } catch (IOException ioe) 
        {
            System.out.println("Input/output exception - Exiting");
        }
        inputString = new String(buffer);
        /* modify to work for both Windows and Linux */

        int pos = inputString.indexOf("\r");
        if (pos < 0)
            pos = inputString.indexOf("\n");
        inputString = inputString.substring(0, pos);
        return inputString;
    }

    /**
     * Original by Aj. Sally
     * modified for this project
     * Asks for one integer value, and returns it
     * as the function value.
     * @param   prompt    String to print, telling which coordinate
     * @return  the value. Exits with error if user types in
     *          something that can't be read as an integer 
     */
    protected static int getOneInteger() 
    {
        int value = 0;
        String inputString;
        int readBytes = 0;
        byte buffer[] = new byte[200];
        try 
        {
            readBytes = System.in.read(buffer, 0, 200);
        } catch (IOException ioe) 
        {
            //System.out.println("Input/output exception - Exiting");
        }
        inputString = new String(buffer);
        try 
        {
            /* modify to work for both Windows and Linux */
            int pos = inputString.indexOf("\r");
            if (pos <= 0)
                pos = inputString.indexOf("\n");
            if (pos > 0)
                inputString = inputString.substring(0, pos);
            value = Integer.parseInt(inputString);
        } catch (NumberFormatException nfe) 
        {
            //System.out.println("Bad number entered - Exiting");
            value = 99;
        }
        return value;
    }

}