/**
 * 
 */
package com.database;

/**
 * @author Niranjan
 *
 */
public class TransactionLedgerDO {

	private String tranID;
	private String senderName;
	private String senderCountryName;
	private String receiverName;
	private String receiverCountryName;
	private String amount;
	private String status;
	private String dateTime;
	private String depositAccountNumber;
	
	/**
	 * @param senderName
	 * @param senderCountryName
	 * @param receiverName
	 * @param receiverCountryName
	 * @param amount
	 * @param dateTime
	 * @param depositAccountNumber
	 */
	public TransactionLedgerDO(String senderName, String senderCountryName, String receiverName,
			String receiverCountryName, String amount, String dateTime, String depositAccountNumber, String status) {
		super();
		this.senderName = senderName;
		this.senderCountryName = senderCountryName;
		this.receiverName = receiverName;
		this.receiverCountryName = receiverCountryName;
		this.amount = amount;
		this.dateTime = dateTime;
		this.depositAccountNumber = depositAccountNumber;
		this.status = status;
	}

	/**
	 * @return the tranID
	 */
	public String getTranID() {
		return tranID;
	}

	/**
	 * @param tranID the tranID to set
	 */
	public void setTranID(String tranID) {
		this.tranID = tranID;
	}

	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * @return the senderCountryName
	 */
	public String getSenderCountryName() {
		return senderCountryName;
	}

	/**
	 * @param senderCountryName the senderCountryName to set
	 */
	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}

	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * @return the receiverCountryName
	 */
	public String getReceiverCountryName() {
		return receiverCountryName;
	}

	/**
	 * @param receiverCountryName the receiverCountryName to set
	 */
	public void setReceiverCountryName(String receiverCountryName) {
		this.receiverCountryName = receiverCountryName;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the depositAccountNumber
	 */
	public String getDepositAccountNumber() {
		return depositAccountNumber;
	}

	/**
	 * @param depositAccountNumber the depositAccountNumber to set
	 */
	public void setDepositAccountNumber(String depositAccountNumber) {
		this.depositAccountNumber = depositAccountNumber;
	}
	
	
	
}
