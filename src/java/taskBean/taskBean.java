/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import taskEJB.TaskEntity;
import taskEJB.TaskEntityControllerLocal;

/**
 *
 * @author muratmenevse
 */

@Named(value="taskBean")
@SessionScoped
public class taskBean implements Serializable{
    
    private int priority;
    private String title;
    private String optionalNotes;
    private Date dueDate;
    private boolean completionFlag;
    private String requestingUser;
    private String assignedUser;
    
    @EJB TaskEntityControllerLocal tasks;

    public taskBean(){
        
    }
    
    public taskBean(int priority, String title, String optionalNotes, 
            Date dueDate, boolean completionFlag, String requestingUser, 
            String assignedUser) {
        this.priority = priority;
        this.title = title;
        this.optionalNotes = optionalNotes;
        this.dueDate = dueDate;
        this.completionFlag = completionFlag;
        this.requestingUser = requestingUser;
        this.assignedUser = assignedUser;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionalNotes() {
        return optionalNotes;
    }

    public void setOptionalNotes(String optionalNotes) {
        this.optionalNotes = optionalNotes;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompletionFlag() {
        return completionFlag;
    }

    public void setCompletionFlag(boolean completionFlag) {
        this.completionFlag = completionFlag;
    }

    public String getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(String requestingUser) {
        this.requestingUser = requestingUser;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }
    
    public List<TaskEntity> getTasks() {
        return tasks.list();
    
    }
    
    public String addTask(){
        System.out.println("YEEES");
        TaskEntity task = new TaskEntity();
        task.setCompletionFlag(isCompletionFlag());
        task.setDueDate(getDueDate());
        task.setOptionalNotes(getOptionalNotes());
        task.setPriority(getPriority());
        task.setTitle(getTitle());
        task.setAssignedUser(getAssignedUser());
        task.setRequestingUser(getRequestingUser());
        
        tasks.add(task);
        
        return "task.xhtml";
    }
    
    public String deleteTask(TaskEntity task){
        tasks.delete(task);
        
        return "task.xhtml";
        
    }
    
    public String editTask (TaskEntity task){
        tasks.update(task);
        
        return "task.xhtml";
    }
    
    
    
    
    
}


