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
	
	
	
}
