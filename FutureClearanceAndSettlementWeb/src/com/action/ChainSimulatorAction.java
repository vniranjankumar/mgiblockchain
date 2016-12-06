/**
 * 
 */
package com.action;

import java.io.StringReader;

import com.database.DataHelper;
import com.database.TransactionLedgerDO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;



/**
 * @author Niranjan
 *
 */
import com.opensymphony.xwork2.Action;

public class ChainSimulatorAction implements Action {
	
	//Java Bean to hold the form parameters
    private String chainId;
    private String chainUrl;
    private String transactionXML;
    private String achXML;

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
	
    /**
	 * @return the achXML
	 */
	public String getAchXML() {
		return achXML;
	}

	/**
	 * @param achXML the achXML to set
	 */
	public void setAchXML(String achXML) {
		this.achXML = achXML;
	}
	
	@Override
    public String execute() throws Exception {
        return "SUCCESS";
    }	


	public String publishTransaction() throws Exception {
		TransactionLedgerDO transactionLedger = unmarshallTransactionXML (getTransactionXML()) ;
		DataHelper.publishTransaction(transactionLedger);
        return "SUCCESS";
    }
    
    public String publishACHTransaction() throws Exception {
    	TransactionLedgerDO achSettlementLedger = unmarshallTransactionXML (getAchXML()) ;
    	DataHelper.publishACHTransaction(achSettlementLedger);
        return "SUCCESS";
    }
    
    public TransactionLedgerDO unmarshallTransactionXML(String transactionXML) throws Exception {
    	TransactionLedgerDO transactionLedger = null ;
    	try {
    		
    		JAXBContext jaxbContext = JAXBContext.newInstance(TransactionLedgerDO.class);
    		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    		StringReader reader = new StringReader(transactionXML);
    		transactionLedger = (TransactionLedgerDO) unmarshaller.unmarshal(reader);
    	}catch (JAXBException e) {
            e.printStackTrace();
        }
    	return transactionLedger;
    }

}
