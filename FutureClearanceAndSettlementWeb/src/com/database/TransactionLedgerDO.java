/**
 * 
 */
package com.database;

/**
 * @author Niranjan
 *
 */
public class TransactionLedgerDO {

	private String senderName;
	private String senderCountryName;
	private String receiverName;
	private String receiverCountryName;
	private String amount;
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
			String receiverCountryName, String amount, String dateTime, String depositAccountNumber) {
		super();
		this.senderName = senderName;
		this.senderCountryName = senderCountryName;
		this.receiverName = receiverName;
		this.receiverCountryName = receiverCountryName;
		this.amount = amount;
		this.dateTime = dateTime;
		this.depositAccountNumber = depositAccountNumber;
	}
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderCountryName() {
		return senderCountryName;
	}
	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverCountryName() {
		return receiverCountryName;
	}
	public void setReceiverCountryName(String receiverCountryName) {
		this.receiverCountryName = receiverCountryName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDepositAccountNumber() {
		return depositAccountNumber;
	}
	public void setDepositAccountNumber(String depositAccountNumber) {
		this.depositAccountNumber = depositAccountNumber;
	}
	
	
}
