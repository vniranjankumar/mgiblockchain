/**
 * 
 */
package com.action;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.database.DataHelper;
import com.database.TransactionLedgerDO;
/**
 * @author Niranjan
 *
 */
import com.opensymphony.xwork2.Action;

public class AuditAction implements Action, SessionAware{   
	
	private Map<String, Object> sessionMap;
	ArrayList<TransactionLedgerDO> transactionList = new ArrayList<TransactionLedgerDO>();
	
    /**
	 * @return the transactionList
	 */
	public ArrayList<TransactionLedgerDO> getTransactionList() {
		return transactionList;
	}

	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(ArrayList<TransactionLedgerDO> transactionList) {
		this.transactionList = transactionList;
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
    	if(role != null && !role.isEmpty()) {
    		transactionList = DataHelper.mockTranData(role);
    		//DataHelper.getTranData(role);
    	}
        return "SUCCESS";
    }
    
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

  
}
