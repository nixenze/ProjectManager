import java.text.SimpleDateFormat;
import java.util.*;
/**
 *  ProjectComparator.java
 *
 *   Class to compare Project objects.
 *
 *   create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */
public class ProjectComparator implements Comparator<Project>
{
    /**
     * Fundamental method compares two Project
     * @param  Project1    First Project
     * @param  Project2    Second Project
     * @return -1 if Project1 date before Project2, 1 if Project1 date after Project2, 0 if date equal
     */
    public int compare(Project project1, Project project2)
    {
        Project t1 = project1;
        Project t2 = project2;
        Calendar c1 = t1.getStartDate();
        Calendar c2 = t2.getStartDate();
        
        try 
        {
            if (c1.compareTo(c2) > 0)
            return 1;
        else if (c1.compareTo(c2) < 0)
            return -1;
        else
            return (int) Math.signum(0);
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
            return 1;
        }

                    
    }

}