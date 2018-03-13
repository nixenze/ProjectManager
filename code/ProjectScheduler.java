import java.io.*;
import java.util.*;

/**
 * ProjectScheduler
 * contain main class of the program. include all features
 * 
 * create by Team LogCat
 *      Thanin Srithai   58070501092
 *      weerawat Onsamlee 58070501099
 */
public class ProjectScheduler 
{
    
    private static ProjectManager manager = ProjectManager.getInstance();
    private static Calendar today;
    private static SQLiteJDBC mySQLite = new SQLiteJDBC();

    /**
     * method for show all the project in the program
     * include all their task
     */
    private static void showAllProjects() 
    {
        manager.showAllProject();
    }
    /**
     * method for show tasks which its duration match with today
     * and show task that weren't completed in the past as LATE TASK
     * it can also mark if user want it to be finished
     */
    private static void showTodayTask() 
    {
        IOmanager.print("Today is : " + CalendarFormatManager.calendarToString(today));
        manager.showTodayTask(today);
        IOmanager.print("Mark finish task!(0 to cancel)");
        int choose = IOmanager.getOneInteger();
        if (choose != 0)
            manager.markAsFinish(choose);

    }

    /**
     * method for create project from DB
     * this method also sort the project collection in ascending order of Start date
     * @param projectList ArrayList of project data
     * @param taskList ArrayList of task data
     */
    private static void createProjectFromQuery(ArrayList<ProjectDetails> projectList,ArrayList<TaskDetails> taskList)
    {
        SQLiteJDBC mySQLite = new SQLiteJDBC();
        int projectID = -1;
        String pName = "";
        String pDetails = "";
        String pStartDate = "";
        String pEndDate = "";
        String tDesc = "";
        String tStartDate = "";
        String tEndDate = "";
        String tName = "";
        int positionProject = -1;
        for(int i = 0;i<projectList.size();i++)
        {
            projectID = projectList.get(i).getProjectId();
            pName = projectList.get(i).getProjectName();
            pDetails = projectList.get(i).getProjectDetail();
            pStartDate = projectList.get(i).getStartDate();
            pEndDate = projectList.get(i).getEndDate();
            positionProject = manager.newProject(pName, pDetails, pStartDate, pEndDate);
            createTaskFromQuery(positionProject,projectID, taskList);
        }
    }

    /**
     * method for create task
     * form DB
     * this method also sort the project collection in ascending order of Start date
     * @param position to specific project position
     * @param projectID to specific project id
     * @param taskList ArrayList to keep data of task
     */
    private static void createTaskFromQuery(int position,int projectID,ArrayList<TaskDetails> taskList)
    {
        SQLiteJDBC mySQLite = new SQLiteJDBC();
        int taskID = -1;
        String tName = "";
        String tDesc = "";
        String tStartDate = "";
        String tEndDate = "";
        boolean tStatus = false;
        int tProjectID = -1;
        for(int i = 0;i<taskList.size();i++)
        {
            taskID = taskList.get(i).getTaskID();
            tName = taskList.get(i).getTaskName();
            tDesc = taskList.get(i).getTaskDescription();
            tStartDate = taskList.get(i).getStartDate();
            tEndDate = taskList.get(i).getEndDate();
            tStatus = taskList.get(i).getStatus();
            tProjectID = taskList.get(i).getProjectID();
            if(tProjectID == projectID)
                manager.addNewTask(position, tName, tDesc, tStartDate, tEndDate, tStatus);
        }
    }

    /**
     * method for create project
     * ask user to create project and its details
     * this method also sort the project collection in ascending order of Start date
     */
    private static void createProject() 
    {
        String pName;
        String pDetails;
        String tDesc;
        String tStartDate;
        String tEndDate;
        String tName;
        int positionProject;
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");
        IOmanager.print("        Create new Project");
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");
        IOmanager.print("please input Project name : ", false);
        pName = IOmanager.getOneString();
        IOmanager.print("next, describe project's details here! (type 'end' to stop)");
        pDetails = IOmanager.getLongString();
        positionProject = manager.newProject(pName, pDetails);

        /** loop for add new task */
        while (true) 
        {
            IOmanager.print("Add new task? y/n");
            if (IOmanager.getOneString().compareTo("y") == 0) 
            {
                IOmanager.print("Task name here : ", false);
                tName = IOmanager.getOneString();
                IOmanager.print("Task Description : ", false);
                tDesc = IOmanager.getLongString();
                IOmanager.print("Start Date(ex:2017 9 25) :", false);
                tStartDate = IOmanager.getOneString();
                IOmanager.print("End Date(ex:2017 9 25) :", false);
                tEndDate = IOmanager.getOneString();
                manager.addNewTask(positionProject, tName, tDesc, tStartDate, tEndDate);
            }
            else 
            {
                break;
            }
        }

        IOmanager.print("***finished");
        manager.recordProject(positionProject);
        manager.showAllProject();

    }

    /**
     * edit  project method
     * recieve new input and set as their new information
     * if their are empty string it will leave that field untouched
     * @return boolean show if wditing was success
     */
    private static boolean editProject() 
    {
        int chooseProj;
        int chooseTask;
        String newName;
        String newDetails;
        String tName;
        String tDesc;
        String startDate;
        String endDate;
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");
        IOmanager.print("        Edit existing Project");
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");

        manager.showAllProject();
        IOmanager.print("***Select Project to edit, 0 to exit");
        chooseProj = IOmanager.getOneInteger();
        if (chooseProj == 0)
            return false;
        chooseProj -= 1;
        manager.showOneProject(chooseProj);
        IOmanager.print("edit name (enter blank for no change) : ", false);
        newName = IOmanager.getOneString();
        IOmanager.print("new describtion here! (enter end for no change)");
        newDetails = IOmanager.getLongString();
        if(manager.editProject(chooseProj, newName, newDetails))
        while (true) 
        {
            IOmanager.print("***Select task to edit, 0 to exit");
            IOmanager.print("***leave the field blank to not change it");
            manager.showAllTask(chooseProj);
            chooseTask = IOmanager.getOneInteger();
            chooseTask -= 1;
            IOmanager.print("edit task name : ", false);
            tName = IOmanager.getOneString();
            IOmanager.print("edit task description : ");
            tDesc = IOmanager.getLongString();
            IOmanager.print("edit task startdate(ex:2017 9 25) : ", false);
            startDate = IOmanager.getOneString();
            IOmanager.print("edit task enddate(ex:2017 9 25) : ", false);
            endDate = IOmanager.getOneString();
            manager.editTask(chooseProj, chooseTask, tName, tDesc, startDate, endDate);

            IOmanager.print("edit more?");
            if (IOmanager.getOneString().compareTo("y") != 0) 
            {
                break;
            }

        }

        return true;
    }
    /**
     * method for delete wether the project or just a task
     * @return  boolean if delete was success
     */
    private static boolean deleteProject() 
    {
        int chooseProj;
        int chooseTask;
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");
        IOmanager.print("    Delete existing Project/tasks");
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");
        manager.showAllProject();
        IOmanager.print("***Select Project to delete, 0 to exit");
        chooseProj = IOmanager.getOneInteger();
        if (chooseProj == 0)
            return false;
        chooseProj -= 1;
        manager.showOneProject(chooseProj);
        IOmanager.print("Delete the whole project? y/n");
        if (IOmanager.getOneString().compareTo("y") == 0) 
        {
            manager.deleteProject(chooseProj);
            return true;
        }
        manager.showAllTask(chooseProj);
        IOmanager.print("  Select task to edit, 0 to exit");
        chooseTask = IOmanager.getOneInteger();
        if (chooseTask == 0) 
        {
            return false;
        }
        chooseTask -= 1;
        manager.deleteTask(chooseProj, chooseTask);
        return true;

    }

    /**
     * method for manage the dependent task (add/delete)
     * if the task to be add are already in the list, it will remove from the list
     * @return boolean if add/delete was success
     */
    private static boolean addDeleteDepTask() 
    {
        int chooseProj;
        int chooseTask;
        int chooseDepTask;
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");
        IOmanager.print("     Add/Delete dependent tasks");
        IOmanager.print("+++++++++++++++++++++++++++++++++++++");
        manager.showAllProject();
        IOmanager.print("***Select Project, 0 to exit");
        chooseProj = IOmanager.getOneInteger();
        if (chooseProj == 0)
            return false;
        chooseProj -= 1;
        IOmanager.print("Editing : ", false);
        manager.showOneProject(chooseProj);
        IOmanager.print("***Select task to edit, 0 to exit");
        chooseTask = IOmanager.getOneInteger();
        if (chooseTask == 0) 
        {
            return false;
        }
        chooseTask -= 1;
        IOmanager.print("Task available for add :");
        manager.showPreviousTask(chooseProj, chooseTask);
        IOmanager.print("***Select task to add/delete, 0 to exit");
        chooseDepTask = IOmanager.getOneInteger();
        chooseDepTask -= 1;
        manager.addDepTask(chooseProj, chooseTask, chooseDepTask);

        return true;
    }

    private static void saveToDatabase() 
    {
        IOmanager.print("Saving projects and tasks . . .");
        mySQLite.delete();
        manager.saveToDatabase();
        IOmanager.print("Save Completed!");
    }

    /**
     * main class
     */
    public static void main(String[] args) 
    {
        int buffer = 99;
        //Scanner scan = new Scanner(System.in);
        today = new GregorianCalendar();
        today.setTime(Calendar.getInstance().getTime());
        if (mySQLite.connect()) 
        {
            ArrayList<ProjectDetails> projectList = mySQLite.selectProject();
            ArrayList<TaskDetails> taskList = mySQLite.selectTask();
            
            if(projectList.size() != -1)
                createProjectFromQuery(projectList,taskList);

            //IOmanager.print(manager.toString());
            IOmanager.print("=================================================");
            IOmanager.print("Welcome to you Project Scheduler!");
            while (true) 
            {
                IOmanager.print("1. Show today's task(s)");
                IOmanager.print("2. Show all Project schedule");
                IOmanager.print("3. Create new Project");
                IOmanager.print("4. Edit any existing Project");
                IOmanager.print("5. Delete Project/tasks");
                IOmanager.print("6. Add/delete dependent tasks");
                IOmanager.print("0. Exit and Save data");
                IOmanager.print("");
                IOmanager.print("please choose your action");
                buffer = IOmanager.getOneInteger();
                switch (buffer) 
                {
                case 1:
                    showTodayTask();
                    break;
                case 2:
                    showAllProjects();
                    break;
                case 3:
                    createProject();
                    break;
                case 4:
                    editProject();
                    break;
                case 5:
                    deleteProject();
                    break;
                case 6:
                    addDeleteDepTask();
                    break;
                case 0:
                    saveToDatabase();
                    IOmanager.print("Goodbye");
                    System.exit(1);
                default:
                    IOmanager.print("invalid input! please enter again!");
                    break;
                }
                IOmanager.print("press enter to continue");
                IOmanager.getOneString();
                IOmanager.print("=================================================");
            }
        }
    }
}