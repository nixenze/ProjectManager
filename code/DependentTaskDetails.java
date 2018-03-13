/**
* DependentTaskDetails Class for receive dapendent task data in DB. 
* 
* create by Team LogCat
*      Thanin Srithai   58070501092
*      weerawat Onsamlee 58070501099
*/
public class DependentTaskDetails
{
    /* declare variable for all data here and also getter setter method */
    private int dependentTaskID;
    private int taskID;
    private int taskOwnID;

    /**
     * setter for dependent task id
     */
    public void setDependentTaskID(int dependentTaskID)
    {
        this.dependentTaskID = dependentTaskID;
    }

    /**
     * getter for dependent task id
     */
    public int getDependentTaskID()
    {
        return dependentTaskID;
    }

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
     * setter for taskOwn id
     */
    public void setTaskOwnID(int taskOwnID)
    {
        this.taskOwnID = taskOwnID;
    }

    /**
     * getter for taskOwn id
     */
    public int getTaskOwnID()
    {
        return taskOwnID;
    }
}