
/**
* TaskDetails Class for receive task data in DB. 
* 
* create by Team LogCat
*      Thanin Srithai   58070501092
*      weerawat Onsamlee 58070501099
*/
public class TaskDetails
{

    /* declare variable for all data here and also getter setter method */
    private int taskID;
    private String taskName;
    private String taskDescription;
    private String startDate;
    private String endDate;
    private boolean status;
    private int projectID;

    /**
     * setter for task id
     */
    public void setTaskID(int taskID)
    {
        this.taskID = taskID;
    }

    /**
     * getter for task id
     */
    public int getTaskID()
    {
        return taskID;
    }

    /**
     * setter for task name id
     */
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    /**
     * getter for task name
     */
    public String getTaskName()
    {
        return taskName;
    }

    /**
     * setter for task detail
     */
    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    /**
     * getter for task detail
     */
    public String getTaskDescription()
    {
        return taskDescription;
    }

    /**
     * setter for start date of task
     */
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    /**
     * getter for start date of task
     */
    public String getStartDate()
    {
        return startDate;
    }

    /**
     * setter for end date of task
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    
    /**
     * getter for end date of task
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * setter for status of task
     */
    public void setStatus(int status)
    {
        if(status==1)
        this.status = true;
        else
        this.status = false;
    }

    /**
     * getter for status of task
     */
    public boolean getStatus() 
    {
        return status;    
    }

    /**
     * setter for project id
     */
    public void setProjectID(int projectID)
    {
        this.projectID = projectID;
    }

    /**
     * getter for project id
     */
    public int getProjectID()
    {
        return projectID;
    }
}