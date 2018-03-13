/**
* ProjectDetails Class for receive project data in DB. 
* 
* create by Team LogCat
*      Thanin Srithai   58070501092
*      weerawat Onsamlee 58070501099
*/
public class ProjectDetails
{


    /* declare variable for all data here and also getter setter method */
    private int projectId;    
    private String projectName;
    private String projectDetail;
    private String startDate;
    private String endDate;

    /**
     * getter for project id
     */
    public int getProjectId()
    {
        return projectId;
    }

    /**
     * setter for project id
     */
    public void setProjectID(int projectId)
    {
        this.projectId = projectId;
    }

    /**
     * getter for project name
     */
    public String getProjectName()
    {
        return projectName;
    }

    /**
     * setter for project name
     */
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    /**
     * getter for project name
     */
    public String getProjectDetail()
    {
        return projectDetail;
    }

    /**
     * setter for project detail
     */
    public void setProjectDetail(String projectDetail)
    {
        this.projectDetail = projectDetail;
    }

    /**
     * getter for start date
     */
    public String getStartDate()
    {
        return startDate;
    }

    /**
     * setter for start date
     */
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    /**
     * getter for end date
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * setter for end date
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
}