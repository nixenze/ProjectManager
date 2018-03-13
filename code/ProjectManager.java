import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


import java.util.*;

/**
 * ProjectManager
 * a singleton class that keeps and manage pool of project and pass the message to other relate classes
 *  
 *  create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */
public class ProjectManager 
{
    /**to keep instance of this singleton class */
    private static ProjectManager manager = null;

    /**keep all the projects */
    private static ArrayList<Project> projectCollection = null;

    /**keep only today task for managing (markAsFinish,etc) */
    private static ArrayList<Task> todoTasks = null;

    /**constructor of this class */
    private ProjectManager() 
    {
        projectCollection = new ArrayList<Project>();
    }

    /**
     * method for get instance of this class
     * @return ProjectManager 
     */
    public static ProjectManager getInstance() 
    {
        if (manager != null)
            return manager;
        else 
        {
            manager = new ProjectManager();
            return manager;
        }

    }
    /**
     * method for show all prject and its tasks
     */
    public void showAllProject() 
    {
        Project temp;
        int i=0;
        for (i = 0; i < projectCollection.size(); i++) 
        {
            temp = projectCollection.get(i);
            IOmanager.print("_______________________________");
            IOmanager.print((i + 1) + ". " + temp.getProjectName());
            IOmanager.print("Project start : " + CalendarFormatManager.calendarToString(temp.getStartDate()));
            IOmanager.print("Project end : " + CalendarFormatManager.calendarToString(temp.getEndDate()));
            IOmanager.print("------------------------------");
            IOmanager.print("Tasks in this project : ");
            temp.showAllTask();
            IOmanager.print("------------------------------");
        }
        if (i==0) 
        {
            IOmanager.print("No Project Here");    
        }

    }


    /**
     * method for show all task in a project
     * @param projNo indicate which project to view 
     */
    public void showAllTask(int projNo) 
    {
        Project temp = projectCollection.get(projNo);
        temp.showAllTask();

    }
    /**
     * mthid to show task that match with date from param
     * and keep them in todoList
     * @param today date which indicate today
     */
    public void showTodayTask(Calendar today) 
    {
        Iterator<Project> it = projectCollection.iterator();
        todoTasks = new ArrayList<Task>();
        todoTasks.clear();
        Project temp = null;
        Task tempTask = null;
        int count = 0;
        String startDateString;
        String endDateString;
        String todayString;
        String taskStartDateString;
        String taskEndDateString;
        while (it.hasNext()) 
        {
            temp = it.next();
            /** parse calendar class to sting for being able to check if they are the same day 
             * (calendar also keeps time which make it impossible to compare equal date) */
            startDateString = CalendarFormatManager.calendarToString(temp.getStartDate());
            endDateString = CalendarFormatManager.calendarToString(temp.getEndDate());
            todayString = CalendarFormatManager.calendarToString(today);

            if ((temp.getStartDate().before(today) && temp.getEndDate().after(today))
                    || startDateString.compareTo(todayString) == 0 || endDateString.compareTo(todayString) == 0) 
                    {
                while (true) 
                {   /**check each task */
                    tempTask = temp.getTask(count);
                    if (tempTask == null)
                        break;
                    taskStartDateString = CalendarFormatManager.calendarToString(tempTask.getStartDate());
                    taskEndDateString = CalendarFormatManager.calendarToString(tempTask.getEndDate());

                    if (tempTask.getEndDate().before(today) && !tempTask.isFinish()) 
                    {
                        todoTasks.add(tempTask);
                        IOmanager.print(todoTasks.size() + ". LATE TASk : " + tempTask.getName());
                    } 
                    else if ((tempTask.getStartDate().before(today) && tempTask.getEndDate().after(today))
                            || taskStartDateString.compareTo(todayString) == 0
                            || taskEndDateString.compareTo(todayString) == 0) 
                            {
                        if(!tempTask.isFinish())
                        {
                            todoTasks.add(tempTask);
                            IOmanager.print(todoTasks.size() + ". today task : " + tempTask.getName());
                        }
                        
                    }
                    count++;
                }

            }
        }
        if (todoTasks.isEmpty()) 
        {
            IOmanager.print("NO TASK TODAY!");   
        }
    }

    /**
     * method for set finish task
     * @param taskNo to indicate the task
     * @return boolean if marking was success
     */
    public boolean markAsFinish(int taskNo) 
    {
        taskNo -= 1;
        try {
            Task temp = todoTasks.get(taskNo);
            if (temp.setFinish()) 
            {
                IOmanager.print("finished!");
                return true;
            } 
            else 
            {
                IOmanager.print("Task cannot set as finish, Some task dependent has to finished fisrt!");
                temp.printTask();
                return false;
    
            }
            
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
            IOmanager.print("input error!");
            return false;
        }


    }

    /**
     * method for sort project based on its duration
     */
    public void sortProject() 
    {
        projectCollection.sort(new ProjectComparator());

    }

    /**
     * method for create new project from given arguments
     * @param pName name of project
     * @param pDetails project details
     * @return int number of the project position in list
     */
    public int newProject(String pName, String pDetails) 
    {
        Project temp = new Project(pName, pDetails);
        projectCollection.add(temp);
        sortProject();
        return projectCollection.indexOf(temp);
    }

       /**
     * method for create new project from given arguments
     * @param pName name of project
     * @param pDetails project details
     * @param startDate project start date
     * @param endDate project end date
     * @return int number of the project position in list
     */
    public int newProject(String pName,String pDetails, String startDate, String endDate)
    {
        Project temp = new Project (pName,pDetails,startDate,endDate);
        projectCollection.add(temp);
        projectCollection.sort(new ProjectComparator());

        return projectCollection.indexOf(temp);
    }

    /**
     * method for add task to the project
     * @param position  indicate which project to add 
     * @param tName     name of task
     * @param description   description of task
     * @param tStartDate    task start date
     * @param tEndDate      task end date
     * @return boolean 
     */
    public boolean addNewTask(int position, String tName, String description, String tStartDate, String tEndDate) 
    {
        Project newProject = projectCollection.get(position);
        Calendar startDate = CalendarFormatManager.stringToCalendar(tStartDate);
        Calendar endDate = CalendarFormatManager.stringToCalendar(tEndDate);
        Task newTask = new Task(tName, description, startDate, endDate);
        if (newProject.createTask(newTask)) 
        {
            newProject.setStartDate();
            newProject.setEndDate();
            sortProject();
            return true;
        } 
        else
            return false;
    }

    /**
     * method for add task to the project
     * @param position  indicate which project to add 
     * @param tName     name of task
     * @param description   description of task
     * @param tStartDate    task start date
     * @param tEndDate      task end date
     * @param boolean       task status
     * @return boolean 
     */
    public boolean addNewTask(int position,String tName,String description,String tStartDate,String tEndDate,boolean status) 
    {
        Project newProject = projectCollection.get(position);
        Calendar startDate = CalendarFormatManager.stringToCalendar(tStartDate,"yyyy MMM dd");
        Calendar endDate =  CalendarFormatManager.stringToCalendar(tEndDate,"yyyy MMM dd");
        Task newTask = new Task(tName,description,startDate,endDate,status);
        if(newProject.createTask(newTask))
        {
            newProject.setStartDate();
            newProject.setEndDate();
            sortProject();
            return true;
        }
        else
            return false;
    }

    /**
     * an overload method for add task to the project and set status of task
     * (used by sql query from datatbase)
     * @param position  indicate which project to add 
     * @param tName     name of task
     * @param description   description of task
     * @param tStartDate    task start date
     * @param tEndDate      task end date
     * @param isFinish      tell if the task is finish
     * @return boolean 
     */
    public boolean addNewTask(int position, String tName, String description, String tStartDate, String tEndDate, Boolean isFinish) 
    {
        Project newProject = projectCollection.get(position);
        Calendar startDate = CalendarFormatManager.stringToCalendar(tStartDate);
        Calendar endDate = CalendarFormatManager.stringToCalendar(tEndDate);
        Task newTask = new Task(tName, description, startDate, endDate);
        if(isFinish)
        newTask.setFinish();
        if (newProject.createTask(newTask)) 
        {
            newProject.setStartDate();
            newProject.setEndDate();
            sortProject();
            return true;
        } 
        else
            return false;
    }

    /**
     * methos to edit the choosen project
     * @param projNo indicate which prohect to edit
     * @param newName new name
     * @param newDetails new details
     * @return boolean result of edititng
     */
    public boolean editProject(int projNo, String newName, String newDetails) 
    {
        Project temp;
        try 
        {
            temp = projectCollection.get(projNo);
            if (newName.compareTo("") != 0) 
            {
                temp.setProjectName(newName);
            }
            if (newDetails.compareTo("") != 0) 
            {
                temp.setProjectDetails(newDetails);
            }
            return true;
        } 
        catch (Exception e) {
            //TODO: handle exception
            IOmanager.print("No such project");
            return false;
        }

    }
    /**
     * method for delete the project
     * @param projNo indicate the project to delete
     * @return boolean if it was success
     */
    public boolean deleteProject(int projNo) 
    {
        if (projNo <= projectCollection.size() - 1) 
        {
            projectCollection.remove(projNo);
            return true;
        } 
        else
            return false;

    }
    /**
     * method for delete the project
     * @param projNo indicate the project to delete
     * @param taskN0 indicate task to edit
     * @param tName new task name
     * @param tDesc new task description
     * @param startDate new startdate
     * @param endDate   new enddate
     * @return boolean if it was success
     */
    public boolean editTask(int projNo, int taskNo, String tName, String tDesc, String startDate, String endDate) 
    {
        Project tempProj;
        tempProj = projectCollection.get(projNo);
        Task tempTask = tempProj.getTask(taskNo);
        if (tempTask == null) 
        {
            IOmanager.print("no task");
            return false;

        }

        if (tName.compareTo("") != 0) 
        {
            tempTask.setName(tName);
        }
        if (tDesc.compareTo("") != 0) 
        {
            tempTask.setDescription(tDesc);
        }
        if (startDate.compareTo("") != 0) 
        {
            tempTask.setStartDate(startDate);
        }
        if (endDate.compareTo("") != 0) 
        {
            tempTask.setEndDate(endDate);
        }
        tempProj.resortTask();
        tempProj.setStartDate();
        tempProj.setEndDate();
        sortProject();
        return true;
    }

    /**
     * method for delete task
     * @param chooseProj project to work with
     * @param chooseTask task to delete
     */
    public void deleteTask(int chooseProj, int chooseTask) 
    {
        projectCollection.get(chooseProj).deleteTask(chooseTask);
    }

    /**
     * method for show earlier task
     * @param projNo project to work with
     * @param taskNo task to delete
     */
    public void showPreviousTask(int projNo, int taskNo) 
    {
        projectCollection.get(projNo).showPreviousTask(taskNo);
    }

    /**
     * method to add dependent task
     * @param ProjNo project that has the task
     * @param taskNo task to work with
     * @param target task to be add as dependent task of taskNo
     */
    public void addDepTask(int projNo, int taskNo, int target) 
    {
        projectCollection.get(projNo).setDependentTask(taskNo, target);

    }
    /**
     * method for print one project
     * @param projNo indicate project to print 
     * @return boolean if found
     */
    public boolean showOneProject(int projNo) 
    {
        Project temp;
        try 
        {
            temp = projectCollection.get(projNo);
        } catch (Exception e) 
        {
            //TODO: handle exception
            temp = null;
            return false;
        }

        IOmanager.print(temp.getProjectName());
        IOmanager.print(temp.getProjectDetails());
        temp.showAllTask();
        return true;
        //IOmanager.print(temp.);

    }

    /**
     * method for write project to the database
     * @param projNo indicate whic project to be write
     */
    public void recordProject(int projNo) 
    {
        SQLiteJDBC mySQLiteJDBC = new SQLiteJDBC();
        Project temp = projectCollection.get(projNo);
        String projName = temp.getProjectName();
        String projDatail = temp.getProjectDetails();
        String projStartDate = CalendarFormatManager.calendarToString(temp.getStartDate());
        String projEndDate = CalendarFormatManager.calendarToString(temp.getEndDate());
        mySQLiteJDBC.recordProject(projName, projDatail, projStartDate, projEndDate);
        temp.recordTask();
    }

    /**
     * method to save all projects and its tasks to database
     */
    public void saveToDatabase()
    {
        Iterator<Project> it = projectCollection.iterator();
        int count=0;
        while(it.hasNext())
        {
            it.next();
            recordProject(count);
            count++;
        }
    }



}