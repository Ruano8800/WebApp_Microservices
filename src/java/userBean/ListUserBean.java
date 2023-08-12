package userBean;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import UserEJB.ListUser;
import UserEJB.ListUserControllerLocal;
import java.util.ArrayList;
import uk.ac.susx.inf.ianw.webApps.taskBroker.ejb.TaskBrokerBeanRemote;
import uk.ac.susx.inf.ianw.webApps.taskBroker.ejb.TaskBrokerException;
import uk.ac.susx.inf.ianw.webApps.taskBroker.entity.Username;

@Named(value="listUser")
@SessionScoped
public class ListUserBean implements Serializable {
    private String username;
    private String password;

    @EJB ListUserControllerLocal users;
    @EJB TaskBrokerBeanRemote users2;
   
    public ListUserBean(){
        
    }

    public ListUserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Password is securely stored in the Database 
     * @param password
     */
    public void setPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        
        //Converting byte to hex
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<byteData.length; i++){
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,16).substring(1));
        }
        this.password = sb.toString();
    }
    
    public List<ListUser> getUsers() {
        return users.list();
    }
    
    public String[] getUsernames() {
        List<String> getUsers = users.usernamesList(); 
        String[] userNames = getUsers.toArray(new String[getUsers.size()]); 
        return userNames;
    }
    
    public ArrayList<String> getUsernames2() {
        ArrayList<String> newA = new ArrayList<String>();
        for (String user:getUsernames()){
            newA.add(users2.getUsername(user).getName());
        }
        return newA;
        
        
    }
    
    public String submit() throws TaskBrokerException {
        ListUser user = new ListUser();
        user.setUsername(getUsername());
        user.setPassword(getPassword());
        if(users.checkName(getUsername())){
            users.add(user);
            System.out.println("Burdayiiiiiim");
            users2.registerUsers(getUsernames());
            System.out.println("Burdayiiiiiim2");
            return "userList.xhtml";  
        }
        else {
            return "registration.xhtml";
        }
    }

    public String delete(ListUser user) throws TaskBrokerException {
        users.delete(user);
        users2.registerUsers(getUsernames());
        return "userList.xhtml";
    }
    
    public String login() {
        if(users.checkLogin(getUsername(), getPassword())){
            return "registration.xhtml";
        }
        else {    
            return "task.xhtml";
        }       
        
    }
}
@sessionEnd
