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

public class ViewChainAction implements Action {
	
	//Java Bean to hold the form parameters
    private String chainId;
    private String chainUrl;
    private String transactionXML;

    /**
	 * @return the chainId
	 */
	public String getChainId() {
		return chainId;
	}

	/**
	 * @param chainId the chainId to set
	 */
	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	/**
	 * @return the chainUrl
	 */
	public String getChainUrl() {
		return chainUrl;
	}

	/**
	 * @param chainUrl the chainUrl to set
	 */
	public void setChainUrl(String chainUrl) {
		this.chainUrl = chainUrl;
	}

	/**
	 * @return the transactionXML
	 */
	public String getTransactionXML() {
		return transactionXML;
	}

	/**
	 * @param transactionXML the transactionXML to set
	 */
	public void setTransactionXML(String transactionXML) {
		this.transactionXML = transactionXML;
	}

	@Override
    public String execute() throws Exception {
    	System.out.println("Entered execute method ====");
        return "SUCCESS";
    }
    
    public String applyChainID() throws Exception {
    	DataHelper.applyChainID(getChainId());
        return "SUCCESS";
    }
    
    public String applyChainURL() throws Exception {
    	DataHelper.applyChainURL(getChainUrl());
        return "SUCCESS";
    }
    
    public String publishTransaction() throws Exception {
    	DataHelper.publishTransaction(getTransactionXML());
        return "SUCCESS";
    }

}
