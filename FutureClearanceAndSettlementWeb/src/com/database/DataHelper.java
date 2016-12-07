/**
 * 
 */
package com.database;

import java.util.ArrayList;
import java.util.Iterator;

import com.blockchain.BlockChainImpl;
import com.blockchain.BlockChainService;

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
		}else if(username.equalsIgnoreCase("cubauditor") && password.equalsIgnoreCase("cubauditor")){
			return "cubauditor";
		}else if(username.equalsIgnoreCase("achauditor") && password.equalsIgnoreCase("achauditor")){
			return "achauditor";
		}
		
		return null;
	}
	
	public static ArrayList<TransactionLedgerDO> getTranData(String role){
		ArrayList<TransactionLedgerDO> dataList = new ArrayList<TransactionLedgerDO>();
		if(!AppConstants.useMockData){			
			BlockChainService chainService = new BlockChainImpl();
			ArrayList<TransactionLedgerDO> tempList = chainService.queryLedgers();
			// Filter data based on role.
			for (Iterator iterator = tempList.iterator(); iterator.hasNext();) {
				TransactionLedgerDO transactionLedgerDO = (TransactionLedgerDO) iterator.next();
				if(role.equalsIgnoreCase("wmauditor")){
					if(transactionLedgerDO.getOriginatingAccountName().equalsIgnoreCase("Walmart Business Account")
							|| transactionLedgerDO.getReceiverAccountName().equalsIgnoreCase("Walmart Business Account")){
						dataList.add(transactionLedgerDO);
					}
				}else if(role.equalsIgnoreCase("cubauditor")){
					if(transactionLedgerDO.getOriginatingAccountName().equalsIgnoreCase("Cub Foods Business Account")
							|| transactionLedgerDO.getReceiverAccountName().equalsIgnoreCase("Cub Foods Business Account")){
						dataList.add(transactionLedgerDO);
					}
				}else{
					dataList.add(transactionLedgerDO);
				}
			}
			return dataList;
		}else{
			return mockTranData(role);
		}		
	}
	
	public static ArrayList<TransactionLedgerDO> mockTranData(String role){
		ArrayList<TransactionLedgerDO> dataList = new ArrayList<TransactionLedgerDO>();
		
		TransactionLedgerDO data1 = new TransactionLedgerDO("100$","Settled","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");
		TransactionLedgerDO data2 = new TransactionLedgerDO("100$","Settled","MGI Business Account","US Bank","7664568765","Cub Foods Business Account","Citi","6654534234");
		TransactionLedgerDO data3 = new TransactionLedgerDO("250$","Settled","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");
		TransactionLedgerDO data4 = new TransactionLedgerDO("15000$","Pending","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");

		
		if(role.equalsIgnoreCase("mgiauditor")){
			dataList.add(data1);dataList.add(data2);dataList.add(data3);dataList.add(data4);
		}else if(role.equalsIgnoreCase("cubauditor")){
			dataList.add(data2);
		}else if(role.equalsIgnoreCase("wmauditor")){
			dataList.add(data1);dataList.add(data3);dataList.add(data4);
		}else if(role.equalsIgnoreCase("achauditor")){
			dataList.add(data1);dataList.add(data2);dataList.add(data3);dataList.add(data4);
		}
		
		return dataList;
	}
	
	public static ArrayList<MembersDO> mockMembersDO(){
		ArrayList<MembersDO> dataList = new ArrayList<MembersDO>();
		
		MembersDO data1 = new MembersDO("Moneygram", "mgi.jpg", AppConstants.aboutMoneygram,"US Bank","7664568765");
		MembersDO data2 = new MembersDO("Walmart", "wm.jpg", AppConstants.aboutWallmart,"BOA","5463736254");
		MembersDO data3 = new MembersDO("Cub Foods", "cub.jpg", AppConstants.aboutCubFoods,"Citi","6654534234");
		MembersDO data4 = new MembersDO("ACH", "ach.jpg", AppConstants.aboutACH);
		
		dataList.add(data1);
		dataList.add(data2);
		dataList.add(data3);
		dataList.add(data4);
		
		return dataList;
	}	
	
	public static void publishTransaction(TransactionLedgerDO transaction) {
		System.out.println("Submitted Transaction Simulator XML = "+transaction.toString());
		if(!AppConstants.useMockData){			
			BlockChainService chainService = new BlockChainImpl();
			chainService.insertLedger(transaction);
		}
	}
	
	public static void publishACHTransaction(TransactionLedgerDO achSettlementLedger) {
		System.out.println("Submitted ACH Simulator XML = "+achSettlementLedger.toString());
		if(!AppConstants.useMockData){			
			BlockChainService chainService = new BlockChainImpl();
			chainService.updateStatus(achSettlementLedger.getSettlementID(),achSettlementLedger.getSettlementStatus());
		}
	}

}
