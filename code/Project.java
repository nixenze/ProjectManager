import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.*;
import java.util.TreeSet;
import java.util.Calendar;

/**
 * Project class, act as a project which has neccessary information and task
 * 
 *  create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */
public class Project 
{
    /**keep all task as treeset */
    protected TreeSet<Task> taskList = new TreeSet<Task>(new TaskComparator());
    private String projectName;
    private String projectDetails;
    private Calendar startDate;
    private Calendar endDate;

    /**contructor of Project class */
    public Project(String pName, String pDetails) 
    {
        projectDetails = pDetails;
        projectName = pName;
    }

    /**contructor of Project class */
    public Project(String pName, String pDetails,String pStartDate,String pEndDate) 
    {
        projectDetails = pDetails;
        projectName = pName;
        this.startDate = CalendarFormatManager.stringToCalendar(pStartDate,"yyyy MMM dd");
        this.endDate = CalendarFormatManager.stringToCalendar(pEndDate,"yyyy MMM dd");  
    }
    /**
     * setProjectname
     * @param name String to set
     */
    public void setProjectName(String name) 
    {
        projectName = name;
    }
    /**
     * setProjectDetails
     * @param details String to set
     */
    public void setProjectDetails(String details) 
    {
        projectDetails = details;

    }

    /**
     * method for calculate for startdate based from its tasks
     */
    public void setStartDate() 
    {
        try 
        {
            this.startDate = taskList.first().getStartDate();
        } catch (Exception e) 
        {
            this.startDate = null;
        }
    }
    /**
     * method for calculate for enddate based from its tasks
     */
    public void setEndDate() 
    {
        try 
        {
            this.endDate = taskList.last().getEndDate();
        } catch (Exception e) 
        {
            this.endDate = null;
        }
    }

    /**
     * getter for project name
     */
    public String getProjectName() 
    {
        return projectName;
    }

    /**
     * getter for project details
     */
    public String getProjectDetails() 
    {
        return projectDetails;
    }
    /**
     * getter for project start date
     */
    public Calendar getStartDate() 
    {
        return startDate;
    }
    /**
     * getter for project end date
     */
    public Calendar getEndDate() 
    {
        return endDate;
    }
    /**
     * create task method
     * @param newTask task to be add
     * @return boolean
     */
    public boolean createTask(Task newTask) 
    {
        if (newTask == null) 
        {
            System.out.println("Task null");
            return false;
        }
        else
            return taskList.add(newTask);
    }
    /**
     * method for write task to the database
     */
    public void recordTask()
    {
        Iterator<Task> iterator = taskList.iterator();
        SQLiteJDBC mySQLiteJDBC = new SQLiteJDBC();
        Task taskNext = null;
        while(iterator.hasNext())
            {
                taskNext = iterator.next();
                String taskName = taskNext.getName();
                String taskDescription = taskNext.getDescription();
                String startDate = CalendarFormatManager.calendarToString(taskNext.getStartDate());
                String endDate = CalendarFormatManager.calendarToString(taskNext.getEndDate());
                int projectID = mySQLiteJDBC.getProjectID(this.projectName);
                mySQLiteJDBC.recordTask(taskName,taskDescription,startDate,endDate,taskNext.isFinish(),projectID);
            }
    }

    /**
     * method for write task dependent to DB
     * @param depTask for specific task for add
     */
    public void recordTaskDependent(Task depTask)
    {
        Iterator<Task> iterator = taskList.iterator();
        SQLiteJDBC mySQLiteJDBC = new SQLiteJDBC();
        Task taskNext = null;
        while(iterator.hasNext())
        {
            if(taskNext == depTask)
            taskNext.recordTaskDependent(taskNext);
        }
    }
    
    /**
     * get task method
     * @param taskNo indicate the task
     * @return Task
     */
    public Task getTask(int taskNo) 
    {
        Task temp;
        int count = 0;
        Iterator<Task> it = taskList.iterator();
        while (it.hasNext()) 
        {
            //IOmanager.print("task no "+count);
            temp = it.next();
            if (count == taskNo) 
            {
                return temp;
            }
            count++;
        }
        return null;
    }

    /**
     * method to delete a task
     * @param chooseTask indicate task to be delete
     */
    public void deleteTask(int chooseTask) 
    {
        Task temp = getTask(chooseTask);
        taskList.remove(temp);
        resortTask();

    }
    /**
     * method for set dependent task
     * @param taskNo task to work with
     * @param target task to be add as dependent task
     */
    public void setDependentTask(int taskNo, int target) 
    {
        Task temp = getTask(target);
        getTask(taskNo).addDependentTask(temp);
    }
    /**
     * method to show all task
     */
    public void showAllTask() 
    {
        int count = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(CalendarFormatManager.getDatePattern(2));
        Iterator<Task> iterator = taskList.iterator();
        Task taskNext = null;
        while (iterator.hasNext()) 
        {
            taskNext = iterator.next();
            count++;
            IOmanager.print(count + ". ", false);
            taskNext.printTask();
        }
    }

    /**
     * method to show all earlier task before taskNo
     * @param taskNo
     */
    public Task showPreviousTask(int taskNo) 
    {
        int count = 0;
        Task thisTask = getTask(taskNo);
        SimpleDateFormat sdf = new SimpleDateFormat(CalendarFormatManager.getDatePattern(2));
        Iterator<Task> iterator = taskList.iterator();
        Task taskNext = null;
        while (iterator.hasNext() && (taskNext = iterator.next()) != thisTask) 
        {
            count++;
            IOmanager.print(count + ". ", false);
            taskNext.printTask();
        }

        return taskNext;
    }
    /**
     * method to sort task if anything has change and might has effect with the order of task
     * and treeset has no method to explicitly sort the set
     */
    public void resortTask() 
    {
        TreeSet<Task> tempSet = new TreeSet<Task>(new TaskComparator());
        tempSet.addAll(taskList);
        taskList.clear();
        taskList.addAll(tempSet);
    }

}