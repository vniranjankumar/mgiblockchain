/**
 * 
 */
package com.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Niranjan
 *
 */
public class TransactionLedgerDO {

	private String settlementID;
	private String settlementAmount;
	private String settlementDateTime;
	private String settlementStatus;

	private String originatingAccountName;
	private String originatingBankName;
	private String originatingAccountNumber;
	
	private String receiverAccountName;
	private String receiverBankName;
	private String receiverAccountNumber;
	
	public TransactionLedgerDO(){
		
	}

	
	/**
	 * @param settlementID
	 * @param settlementAmount
	 * @param settlementDateTime
	 * @param settlementStatus
	 * @param originatingAccountName
	 * @param originatingBankName
	 * @param originatingAccountNumber
	 * @param receiverAccountName
	 * @param receiverBankName
	 * @param receiverAccountNumber
	 */
	public TransactionLedgerDO(String settlementAmount,
			String settlementStatus, String originatingAccountName, String originatingBankName,
			String originatingAccountNumber, String receiverAccountName, String receiverBankName,
			String receiverAccountNumber) {
		super();
		int random = (int )(Math.random() * 99999 + 1);
		this.settlementID = random+"";
		this.settlementAmount = settlementAmount;
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = new Date();
		this.settlementDateTime = dateFormat.format(date);

		this.settlementStatus = settlementStatus;
		this.originatingAccountName = originatingAccountName;
		this.originatingBankName = originatingBankName;
		this.originatingAccountNumber = originatingAccountNumber;
		this.receiverAccountName = receiverAccountName;
		this.receiverBankName = receiverBankName;
		this.receiverAccountNumber = receiverAccountNumber;
	}


	/**
	 * @return the settlementID
	 */
	public String getSettlementID() {
		return settlementID;
	}


	/**
	 * @param settlementID the settlementID to set
	 */
	public void setSettlementID(String settlementID) {
		this.settlementID = settlementID;
	}


	/**
	 * @return the settlementAmount
	 */
	public String getSettlementAmount() {
		return settlementAmount;
	}


	/**
	 * @param settlementAmount the settlementAmount to set
	 */
	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}


	/**
	 * @return the settlementDateTime
	 */
	public String getSettlementDateTime() {
		return settlementDateTime;
	}


	/**
	 * @param settlementDateTime the settlementDateTime to set
	 */
	public void setSettlementDateTime(String settlementDateTime) {
		this.settlementDateTime = settlementDateTime;
	}


	/**
	 * @return the settlementStatus
	 */
	public String getSettlementStatus() {
		return settlementStatus;
	}


	/**
	 * @param settlementStatus the settlementStatus to set
	 */
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}


	/**
	 * @return the originatingAccountName
	 */
	public String getOriginatingAccountName() {
		return originatingAccountName;
	}


	/**
	 * @param originatingAccountName the originatingAccountName to set
	 */
	public void setOriginatingAccountName(String originatingAccountName) {
		this.originatingAccountName = originatingAccountName;
	}


	/**
	 * @return the originatingBankName
	 */
	public String getOriginatingBankName() {
		return originatingBankName;
	}


	/**
	 * @param originatingBankName the originatingBankName to set
	 */
	public void setOriginatingBankName(String originatingBankName) {
		this.originatingBankName = originatingBankName;
	}


	/**
	 * @return the originatingAccountNumber
	 */
	public String getOriginatingAccountNumber() {
		return originatingAccountNumber;
	}


	/**
	 * @param originatingAccountNumber the originatingAccountNumber to set
	 */
	public void setOriginatingAccountNumber(String originatingAccountNumber) {
		this.originatingAccountNumber = originatingAccountNumber;
	}


	/**
	 * @return the receiverAccountName
	 */
	public String getReceiverAccountName() {
		return receiverAccountName;
	}


	/**
	 * @param receiverAccountName the receiverAccountName to set
	 */
	public void setReceiverAccountName(String receiverAccountName) {
		this.receiverAccountName = receiverAccountName;
	}


	/**
	 * @return the receiverBankName
	 */
	public String getReceiverBankName() {
		return receiverBankName;
	}


	/**
	 * @param receiverBankName the receiverBankName to set
	 */
	public void setReceiverBankName(String receiverBankName) {
		this.receiverBankName = receiverBankName;
	}


	/**
	 * @return the receiverAccountNumber
	 */
	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}


	/**
	 * @param receiverAccountNumber the receiverAccountNumber to set
	 */
	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}


	
}
