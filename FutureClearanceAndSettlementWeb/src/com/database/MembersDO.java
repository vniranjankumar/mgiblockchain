/**
 * 
 */
package com.database;

/**
 * @author Niranjan
 *
 */
public class MembersDO {
	
	private String memberName;
	private String memberLogoFileName;
	private String memberDetails;
	private String bankName;
	private String bankAccountNumber;

	
	/**
	 * @param memberName
	 * @param memberLogoFileName
	 * @param memberDetails
	 */
	public MembersDO(String memberName, String memberLogoFileName, String memberDetails) {
		super();
		this.memberName = memberName;
		this.memberLogoFileName = memberLogoFileName;
		this.memberDetails = memberDetails;
	}


	/**
	 * @param memberName
	 * @param memberLogoFileName
	 * @param memberDetails
	 * @param bankName
	 * @param bankAccountNumber
	 */
	public MembersDO(String memberName, String memberLogoFileName, String memberDetails, String bankName,
			String bankAccountNumber) {
		super();
		this.memberName = memberName;
		this.memberLogoFileName = memberLogoFileName;
		this.memberDetails = memberDetails;
		this.bankName = bankName;
		this.bankAccountNumber = bankAccountNumber;
	}

	
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the memberLogoFileName
	 */
	public String getMemberLogoFileName() {
		return memberLogoFileName;
	}

	/**
	 * @param memberLogoFileName the memberLogoFileName to set
	 */
	public void setMemberLogoFileName(String memberLogoFileName) {
		this.memberLogoFileName = memberLogoFileName;
	}

	/**
	 * @return the memberDetails
	 */
	public String getMemberDetails() {
		return memberDetails;
	}

	/**
	 * @param memberDetails the memberDetails to set
	 */
	public void setMemberDetails(String memberDetails) {
		this.memberDetails = memberDetails;
	}


	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}


	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	/**
	 * @return the bankAccountNumber
	 */
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}


	/**
	 * @param bankAccountNumber the bankAccountNumber to set
	 */
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	
	
}
