import java.text.SimpleDateFormat;
import java.util.*;
/**
 *  TaskComparator.java
 *
 *   Class to compare Project objects.
 *
 *   create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */
public class TaskComparator implements Comparator<Task>
{
    /**
     * Fundamental method compares two task
     * @param  task1    First task
     * @param  task2    Second task
     * @return -1 if task1 date before task2, 1 if task1 date after task2, 0 if date equal
     */
    public int compare(Task task1, Task task2)
    {
        Task t1 = (Task) task1;
        Task t2 = (Task) task2;
        Calendar c1 = t1.getStartDate();
        Calendar c2 = t2.getStartDate();
        int seq1 = t1.getSequence();
        int seq2 = t2.getSequence();
        if(c1 == null || c2 == null)
        {
            if(c2 == null)
                System.out.print("c2 is null");
            else
                System.out.print("c1 is null");
            return 1;
        }
            
        else
        {
            if (c1.compareTo(c2) > 0)
                return 1;
            else if (c1.compareTo(c2) < 0)
                return -1;
            else
                return (int) Math.signum(seq1 - seq2);
        }
    }

}