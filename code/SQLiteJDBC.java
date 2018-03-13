import java.sql.*;
import java.util.ArrayList;

/**
 * SQLiteJDBC Class for communicate with sql database (sqlite)
 * 
 * create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */

public class SQLiteJDBC 
{
  /**
   * make new connection to database
   */
  public boolean connect()
  {
    Connection c = null;  
    try 
    {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:Schedule_database.db");
    } 
    catch ( Exception e ) 
    {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       System.exit(0);
    }
    //System.out.println("Opened database successfully");
    return true;
  } 

/**
   * method to query project into databse
   * @param sql for commamd SQL syntax
   */
  private void insert(String sql)
  {
    Connection c = null;
    Statement stmt = null;
    
    try 
    {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:Schedule_database.db");
       c.setAutoCommit(false);

       stmt = c.createStatement();
       stmt.executeUpdate(sql);

       stmt.close();
       c.commit();
       c.close();
       System.out.println("Records created successfully");
    } 
    catch ( Exception e ) 
    {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    }
  }

  /**
   * method to clear the data in database
   */
  public void delete() 
  {
    Connection c = null;
    Statement stmt = null;
    try 
    {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:Schedule_database.db");
      c.setAutoCommit(false);
      //System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql = "DELETE from Tasks;";
      stmt.executeUpdate(sql);
      c.commit();
      sql = "DELETE from Projects;";
      stmt.executeUpdate(sql);
      c.commit();
    } 
    catch (Exception e) 
    {
      IOmanager.print("error!");
      //TODO: handle exception
    }  
  }

    /**
   * medhod to get ID from sql command
   * @param sql for commamd SQL syntax
   * @param getId  send id to caller
   * @return id
   */
  private int selectID(String sql,String getId)
  {
    Connection c = null;
    Statement stmt = null;
    int id = -1;
    try 
    {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:Schedule_database.db");
       c.setAutoCommit(false);
       stmt = c.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       
       if ( rs.next() ) 
        {
          id = rs.getInt(getId);
       }
       rs.close();
       stmt.close();
       c.close();
    } 
    catch ( Exception e ) 
    {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       System.exit(0);
    }
    System.out.println("Operation done successfully");
    return id;
   }

  /**
   * method to contain data that receive in DB
   * @return ArrayList 
   */
  public ArrayList<ProjectDetails> selectProject()
  {
     ArrayList<ProjectDetails> listAll = new ArrayList<ProjectDetails>();
     Connection c = null;
     Statement stmt = null;
     try 
     {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:Schedule_database.db");
        c.setAutoCommit(false);
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Projects;");
        
        while ( rs.next() ) 
         {
           ProjectDetails e = new ProjectDetails();
           e.setProjectID(rs.getInt("ProjectID"));  
           e.setProjectName(rs.getString("ProjectName"));
           e.setProjectDetail(rs.getString("ProjectDetail"));
           e.setStartDate(rs.getString("StartDate"));
           e.setEndDate(rs.getString("EndDate"));

           listAll.add(e);
        }
        rs.close();
        stmt.close();
        c.close();
     } 
     catch ( Exception e ) 
     {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
     }
     System.out.println("Operation done successfully");

     return listAll;
  }

   /**
   * method to contain data that receive in DB
   * @return ArrayList 
   */
  public ArrayList<TaskDetails> selectTask()
  {
     ArrayList<TaskDetails> listAll = new ArrayList<TaskDetails>();
     Connection c = null;
     Statement stmt = null;
     try 
     {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:Schedule_database.db");
        c.setAutoCommit(false);
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks;");
        
        while ( rs.next() ) 
         {
           TaskDetails e = new TaskDetails();
           e.setTaskID(rs.getInt("TaskID"));
           e.setTaskName(rs.getString("TaskName"));
           e.setTaskDescription(rs.getString("TaskDescription"));
           e.setStartDate(rs.getString("StartDate"));
           e.setEndDate(rs.getString("EndDate"));
           e.setStatus(rs.getInt("Status"));
           e.setProjectID(rs.getInt("ProjectID"));
           listAll.add(e);
        }
        rs.close();
        stmt.close();
        c.close();
     } 
     catch ( Exception e ) 
     {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
     }
     System.out.println("Operation done successfully");

     return listAll;
  }

   /**
   * method to contain data that receive in DB
   * @return ArrayList 
   */
  public ArrayList<DependentTaskDetails> selectDependent()
  {
    ArrayList<DependentTaskDetails> listAll = new ArrayList<DependentTaskDetails>();
    Connection c = null;
    Statement stmt = null;
    try 
    {
       Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:Schedule_database.db");
       c.setAutoCommit(false);
       stmt = c.createStatement();
       ResultSet rs = stmt.executeQuery("SELECT * FROM DependentTasks;");
       
       while ( rs.next() ) 
        {
          DependentTaskDetails e = new DependentTaskDetails();
          e.setDependentTaskID(rs.getInt("DependentTaskID"));
          e.setTaskOwnID(rs.getInt("TaskOwnID"));
          e.setTaskID(rs.getInt("TaskID"));
          listAll.add(e);
       }
       rs.close();
       stmt.close();
       c.close();
    } 
    catch ( Exception e ) 
    {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       System.exit(0);
    }
    System.out.println("Operation done successfully");
    return listAll;
  }

  /**
   * medhod to insert data project. It work with insert method
   * @param projectName insert name to DB
   * @param projectDetail  insert detail to DB
   * @param startDate insert start date to DB
   * @param endDate insert end date to DB
   */
  public void recordProject(String projectName,String projectDetail,String startDate,String endDate)
  {
    String sql = "INSERT INTO Projects (ProjectName,ProjectDetail,StartDate,EndDate) " +
    "VALUES ('" + projectName + "','" + projectDetail + "','" + startDate + "','" + endDate + "');"; 
    insert(sql);
  }

  /**
   * medhod to insert data task. It work with insert method
   * @param taskName insert name to DB
   * @param taskDescription  insert detail to DB
   * @param startDate insert start date to DB
   * @param startDate insert end date to DB
   * @param projectID insert project ID (for reference task in project) to DB
   */
  public void recordTask(String taskName,String taskDescription,String startDate,String endDate,boolean isFinish,int projectID)
  {
    int status = (isFinish) ? 1 : 0;
    String sql = "INSERT INTO Tasks (TaskName,TaskDescription,StartDate,EndDate,Status,ProjectID) " +
    "VALUES ('" + taskName + "','" + taskDescription + "','" + startDate + "','" + endDate + "','" + status + "'," + projectID + ");"; 
    insert(sql);
  }

    /**
   * medhod to insert data task dapendent. It work with insert method
   * @param taskID insert task ID to DB
   * @param taskDescription  insert datail to DB
   */
  public void recordTaskDependent(int taskID,int taskOwnID)
  {
    String sql = "INSERT INTO DependentTasks (TaskID,TaskOwnID) " +
    "VALUES (" + taskID + "," + taskOwnID + ");"; 
    insert(sql);
  }

  /**
   * medhod to get project ID my project name. It work with selectID
   * @return projectID for send project id to caller
   */
  public int getProjectID(String projectName)
  {
    String sql = "SELECT ProjectID FROM Projects " +
    "WHERE ProjectName = '" + projectName + "';";
    String getId = "ProjectID";
    int projectID = selectID(sql,getId);
    return projectID;
  }
  /**
   * medhod to get task ID my taks name. It work with selectID
   * @return taskID for send task id to caller
   */
  public int getTaskID(String taskName)
  {
    String sql = "SELECT TaskID FROM Tasks " +
    "WHERE TaskName = '" + taskName + "';";
    String getId = "TaskID";
    int taskID = selectID(sql,getId);
    return taskID;
  }
      
}