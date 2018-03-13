import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.print.DocFlavor.STRING;

import java.util.Calendar;
/**
 * Task class, act as task which has its data, own by Project calss
 * 
 *  create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */

public class Task 
{
    private static int counter = 0;
    private int sequence;
    private String taskName;
    private String description;
    private Calendar startDate;
    private Calendar endDate;
    private boolean isFinish = false;

    /** treeset of dependent task */
    protected TreeSet<Task> dependentTask = new TreeSet<Task>(new TaskComparator());

    /** constructor */
    public Task(String name, String description, Calendar startDate, Calendar endDate) 
    {
        this.taskName = name;
        this.description = description;
        counter++;
        sequence = counter;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /** constructor */
    public Task(String name, String description, Calendar startDate, Calendar endDate, boolean status) 
    {
        this.taskName = name;
        this.description = description;
        counter++;
        sequence = counter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinish = status;
    }

    /**
     * method for set the task as finished
     * @return boolean whether if setting was success
     */
    public boolean setFinish() 
    {
        Iterator<Task> it = null;
        Task temp = null;
        if (this.dependentTask.isEmpty()) 
        {
            isFinish = true;
            return true;
        } 
        else 
        {
            it = this.dependentTask.iterator();
            while (it.hasNext()) 
            {
                if (!it.next().isFinish())
                {
                    isFinish=false;
                    return false;
                }

            }
            isFinish = true;
            return true;
        }
    }

    /**
     * method to check if it's finished
     * @return boolean if finished or not
     */
    public boolean isFinish() 
    {
        return this.isFinish;
    }

    /**
     * setter for task name
     * @param newName new name
     */
    public void setName(String newName) 
    {
        this.taskName = newName;
    }

    /**
     * getter for the name
     * @return String name of task
     */
    public String getName() 
    {
        return taskName;
    }
    /**
     * set description
     * @param description 
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }

    /**
     * get description
     */
    public String getDescription() 
    {
        return description;
    }
    /**
     * set start date
     * @param startDate 
     */
    public boolean setStartDate(String startDate) 
    {
        Calendar temp;
        temp = CalendarFormatManager.stringToCalendar(startDate);
        if (temp != null) 
        {
            this.startDate = temp;
            return true;
        } 
        else 
        {
            return false;
        }
    }
    /**
     * set end date
     * @param endDate 
     */
    public boolean setEndDate(String endDate) 
    {
        Calendar temp;
        temp = CalendarFormatManager.stringToCalendar(endDate);
        if (temp != null) 
        {
            this.endDate = temp;
            return true;
        } 
        else 
        {
            return false;
        }
    }
    /**
     * get start date ass Calendar
     * @return Calendar
     */
    public Calendar getStartDate() 
    {
        return startDate;
    }
    /**
     * get end date
     * @return Calendar
     */
    public Calendar getEndDate() 
    {
        return endDate;
    }
    /**
     * set sequence
     * @param int 
     */
    public int getSequence() 
    {
        return sequence;
    }

    /**
     * method for adding dependent task to this task
     * @param depTask task to be add
     * @return boolean
     */
    public boolean addDependentTask(Task depTask) 
    {
        if (depTask == null) 
        {
            System.out.println("Task null");
            return false;
        } 
        else if (this.getStartDate().before(depTask.getStartDate())) 
        {
            System.out.println("invalid task!! dependent task must before date in this task");
            return false;
        } 
        else 
        {
            if (dependentTask.contains(depTask)) 
            {
                dependentTask.remove(depTask);
            } 
            else if (this.equals(depTask)) 
            {
                IOmanager.print("task cannot add itself");
                return false;
            } 
            else
                dependentTask.add(depTask);
            return true;
        }
    }

    /**
     * method for write task dependent to DB
     * @param depTask for specific task for add
     */
    public void recordTaskDependent(Task depTask)
    {
        SQLiteJDBC mySQLiteJDBC = new SQLiteJDBC();
        String dependentTasks = depTask.getName();
        int taskID = mySQLiteJDBC.getTaskID(depTask.getName());
        int taskOwnID = mySQLiteJDBC.getTaskID(this.taskName);
        mySQLiteJDBC.recordTaskDependent(taskID,taskOwnID);
    }

    /**
     * show information of this task and dependent task
     */
    public void printTask() 
    {
        int count = 0;
        IOmanager.print(
                this.taskName + "\n" + this.description + " : " + CalendarFormatManager.calendarToString(this.startDate)
                        + " - " + CalendarFormatManager.calendarToString(this.endDate));
        if (!dependentTask.isEmpty()) 
        {
            IOmanager.print("\tDependent Tasks : ");
            Iterator<Task> it = dependentTask.iterator();
            while (it.hasNext()) 
            {
                count++;
                it.next().printAsDepTask(count);
            }
        }
    }

    /**
     * method for print as dependent task format
     * @param count indicate the order to print
     */
    public void printAsDepTask(int count) 
    {
        IOmanager.print("\t" + count + ". " + this.taskName + "\n\t" + this.description + " : "
                + CalendarFormatManager.calendarToString(this.startDate) + " - "
                + CalendarFormatManager.calendarToString(this.endDate));

    }
    /**
     * show all dependent task
     */
    public void showDependentTask() 
    {
        Iterator<Task> iterator = dependentTask.iterator();
        while (iterator.hasNext()) 
        {
            iterator.next().printTask();
        }
    }

}