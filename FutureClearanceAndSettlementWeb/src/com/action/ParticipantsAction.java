/**
 * 
 */
package com.action;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.database.DataHelper;
import com.database.MembersDO;
/**
 * @author Niranjan
 *
 */
import com.opensymphony.xwork2.Action;

public class ParticipantsAction implements Action , SessionAware{ 

	private Map<String, Object> sessionMap;
	ArrayList<MembersDO> membersList = new ArrayList<MembersDO>();
	
	/**
	 * @return the membersList
	 */
	public ArrayList<MembersDO> getMembersList() {
		return membersList;
	}

	/**
	 * @param membersList the membersList to set
	 */
	public void setMembersList(ArrayList<MembersDO> membersList) {
		this.membersList = membersList;
	}

	@Override
    public String execute() throws Exception {
		boolean isUserLoggedIn = false ;
    	String role = null;
    	if(sessionMap.containsKey("login")) {
    		isUserLoggedIn = true;
    	}
    	if(isUserLoggedIn){
    		if(sessionMap.containsKey("role")) {
    			role = (String)sessionMap.get("role");
    		}
    	}
    	// Assuming user need to have some role assigned to view this page
    	if(role != null && !role.isEmpty()) {
    		membersList = DataHelper.mockMembersDO();
    	}
        return "SUCCESS";
    }
	
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

  
}
