/**
 * 
 */
package com.action;

import com.database.DataHelper;
/**
 * @author Niranjan
 *
 */
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


import java.util.Map;
 
import org.apache.struts2.interceptor.SessionAware;

public class LoginAction extends ActionSupport implements Action, SessionAware{    

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Java Bean to hold the form parameters
    private String username;
    private String password;
    private Map<String, Object> sessionMap;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean validateString(String str) {
        if (str != null && !str.equals(""))
            return true;
        return false;
    }    
    
	private boolean validateLogin(String username, String password){
    	if (validateString(username) && validateString(password)){
    		String role = DataHelper.mockLogin(username, password);
    		if(role!=null){
    			sessionMap.put("role",role); 
    			return true;
    		}  			
    	}
    	return false;
    }
    
    @Override
    public String execute() throws Exception {
    	if (validateLogin(getUsername(),getPassword())){
           	//Commenting welcome message for now
    		//addActionMessage("Welcome user!");
           	sessionMap.put("login",true); 		
           	return "SUCCESS";
        }            
        else{
          	addActionError("Invalid Username or password");
           	return "ERROR";
        } 
    }
    
    
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	
	public String logout(){
		if(sessionMap.containsKey("login")) {
			sessionMap.remove("login");  
		}
		
	    return "SUCCESS";  
	}  
}
