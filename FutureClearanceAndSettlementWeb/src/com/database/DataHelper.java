/**
 * 
 */
package com.database;

import java.util.ArrayList;

import com.blockchain.BlockChainImpl;
import com.blockchain.BlockChainService;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.util.AppConstants;

/**
 * @author Niranjan
 *
 */
public class DataHelper {
	
	public static String mockLogin(String username, String password){
		
		if(username.equalsIgnoreCase("caadmin") && password.equalsIgnoreCase("caadmin")){
			return "caadmin";
		}else if(username.equalsIgnoreCase("mgiauditor") && password.equalsIgnoreCase("mgiauditor")){
			return "mgiauditor";
		}else if(username.equalsIgnoreCase("wmauditor") && password.equalsIgnoreCase("wmauditor")){
			return "wmauditor";
		}else if(username.equalsIgnoreCase("bmauditor") && password.equalsIgnoreCase("bmauditor")){
			return "bmauditor";
		}
		
		return null;
	}
	
	public static ArrayList<TransactionLedgerDO> getTranData(String role){
		if(!AppConstants.useMockData){			
			BlockChainService chainService = new BlockChainImpl();
			return chainService.queryTranData();
		}else{
			//TODO: Remove below mock data call once, Above blockchain service code is working.
			return mockTranData(role);
		}		
	}
	
	public static ArrayList<TransactionLedgerDO> mockTranData(String role){
		ArrayList<TransactionLedgerDO> dataList = new ArrayList<TransactionLedgerDO>();
		
		TransactionLedgerDO data1 = new TransactionLedgerDO("Niranjan","USA","Pradeep","Mexico","100","11-26-2016 5:00PM","******2354","Sucess");
		TransactionLedgerDO data2 = new TransactionLedgerDO("Sriram","USA","Pradeep","Mexico","50","11-27-2016 7:00PM","******2354","Sucess");
		TransactionLedgerDO data3 = new TransactionLedgerDO("Niranjan","USA","Sankar","USA","100","11-25-2016 3:00PM","","Sucess");
		TransactionLedgerDO data4 = new TransactionLedgerDO("Sankar","USA","Praveen","India","200","11-23-2016 1:00PM","******2354","Sucess");
		TransactionLedgerDO data5 = new TransactionLedgerDO("Niranjan","USA","Pradeep","Mexico","100","11-23-2016 2:00PM","","Sucess");
		
		if(role.equalsIgnoreCase("mgiauditor")){
			dataList.add(data1);dataList.add(data2);dataList.add(data3);dataList.add(data4);dataList.add(data5);
		}else if(role.equalsIgnoreCase("wmauditor")){
			dataList.add(data3);
		}else if(role.equalsIgnoreCase("bmauditor")){
			dataList.add(data1);dataList.add(data2);
		}
		
		return dataList;
	}
	
	public static ArrayList<MembersDO> mockMembersDO(){
		ArrayList<MembersDO> dataList = new ArrayList<MembersDO>();
		
		MembersDO data1 = new MembersDO("Moneygram", "mgi.jpg", "Details about MGI....");
		MembersDO data2 = new MembersDO("Walmart", "wm.jpg", "Details about Walmart....");
		MembersDO data3 = new MembersDO("Bancomer", "bm.jpg", "Details about Bancomer....");
		
		dataList.add(data1);
		dataList.add(data2);
		dataList.add(data3);
		
		return dataList;
	}
	
	public static void applyChainID(String chainId) {
		System.out.println("Submitted Chain ID = "+chainId);
	}
	
	public static void applyChainURL(String chainURL) {
		System.out.println("Submitted Chain URL = "+chainURL);
	}
	
	public static void publishTransaction(String transactionXML) {
		System.out.println("Submitted Transaction XML = "+transactionXML);
	}

}
