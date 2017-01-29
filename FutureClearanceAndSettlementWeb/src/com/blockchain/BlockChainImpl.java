/**
 * 
 */

package com.blockchain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.database.TransactionLedgerDO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Niranjan
 *
 */
public class BlockChainImpl implements BlockChainService {
	
	private static String chaincodeID = "27d9aa4007c5dfeb41accc50f9061472f97a799f65e08121bb238ea5a0d53b37ab141c63c4d8734be959befb4fd21bbcd5e8ffb14a72a8998150cd18cb58ccd2";	
	private static String chaincodeURL = "https://936ed8a02d6f445b9527989414656782-vp0.us.blockchain.ibm.com:5003/chaincode";
	
	public boolean insertLedger(TransactionLedgerDO dataDO){
		
		String status = "";
		String amount = dataDO.getSettlementAmount().replace("$", "");
		if(Integer.parseInt(amount) > 10000){
			status = "Pending";
		}else{
			status = "Settled";
		}
		
		String data = "\""+ dataDO.getSettlementID() +"\","+
				      "\""+ dataDO.getOriginatingBankName() +"\","+
				      "\""+ dataDO.getOriginatingAccountName() +"\","+
				      "\""+ dataDO.getOriginatingAccountNumber() +"\","+
				      "\""+ dataDO.getReceiverBankName() +"\","+
				      "\""+ dataDO.getReceiverAccountName() +"\","+
					  "\""+ dataDO.getReceiverAccountNumber() +"\","+
				      "\""+ dataDO.getSettlementAmount() +"\","+
				      "\""+ dataDO.getSettlementDateTime() +"\","+
				      "\""+ status +"\"";
		
		String req = buildJsonRequest("WebAppAdmin", "invoke", "create_ledger", data);
		String jsonInString = callBlockChainAPI("POST", req);
		if(jsonInString.contains("\"status\":\"OK\""))
			return true;
		else
			return false;
	}
	
	public ArrayList<TransactionLedgerDO> queryLedgers(){
		ArrayList<TransactionLedgerDO> dataList = new ArrayList<TransactionLedgerDO>();
		
		String req = buildJsonRequest("WebAppAdmin", "query", "get_ledgers", "");
		String jsonInString = callBlockChainAPI("POST", req);
		jsonInString = jsonInString.substring(jsonInString.indexOf("message")+10,jsonInString.indexOf("\"},\"id"));
		jsonInString = jsonInString.replaceAll("\\\\","");
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// Convert JSON string to Object
			dataList = mapper.readValue(jsonInString, new TypeReference<List<TransactionLedgerDO>>(){});

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	public boolean updateStatus(String settlementID, String status){
		String data = "\""+ settlementID +"\","+
			          "\""+ status +"\"";
		
		String req = buildJsonRequest("WebAppAdmin", "invoke", "update_ledger_status", data);
		String jsonInString = callBlockChainAPI("POST", req);
		
		return true;
	}
	
	private static String callBlockChainAPI(String httpMethod, String jsonRequest){
		String output = "";
		
		try {

			System.out.println("Before calling chain code URL");
			URL url = new URL(chaincodeURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if(httpMethod.equalsIgnoreCase("GET")){
				conn.setRequestMethod(httpMethod);
				conn.setRequestProperty("Accept", "application/json");
			}else{
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json charset=UTF-8");
				
				OutputStream os = conn.getOutputStream();
				os.write(jsonRequest.getBytes("UTF-8"));
				os.flush();
			}

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String line;
			System.out.println("Output from Server .... \n");
			while ((line = br.readLine()) != null) {
				output = line;
				System.out.println(output);
			}

			conn.disconnect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	private static String buildJsonRequest(String userId, String method, String ctorMsg, String parameters){
	
		String jsonReq = "{" +
							"\"jsonrpc\": \"2.0\","+
							"\"method\": \""+method+"\","+
							"\"params\": {"+
								"\"type\": 1,"+
								"\"chaincodeID\": {"+
								"\"name\": \""+chaincodeID+"\""+
								"},"+
								"\"ctorMsg\": {"+
									"\"function\": \""+ctorMsg+"\","+
									"\"args\":["+ parameters +"]"+
								"},"+
								"\"secureContext\": \""+userId+"\""+
							"},"+
							"\"id\": 0"+
						  "}";
	
		return jsonReq;
	}
	
	public static void main(String[] args){
		
		// Demo Purpose...
//		TransactionLedgerDO data1 = new TransactionLedgerDO("100$","Settled","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");
//		TransactionLedgerDO data2 = new TransactionLedgerDO("100$","Settled","MGI Business Account","US Bank","7664568765","Cub Foods Business Account","Citi","6654534234");
//		TransactionLedgerDO data3 = new TransactionLedgerDO("250$","Settled","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");
//		TransactionLedgerDO data4 = new TransactionLedgerDO("15000$","Pending","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");
//
		BlockChainImpl obj = new BlockChainImpl();	
//		obj.insertLedger(data1);
//		obj.insertLedger(data2);
//		obj.insertLedger(data3);
//		obj.insertLedger(data4);
//		
		
		obj.updateStatus("66730", "Settled");	
	}
}
=======
/**
 * 
 */

package com.blockchain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.database.TransactionLedgerDO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Niranjan
 *
 */
public class BlockChainImpl implements BlockChainService {
	
	private static String chaincodeID = "27d9aa4007c5dfeb41accc50f9061472f97a799f65e08121bb238ea5a0d53b37ab141c63c4d8734be959befb4fd21bbcd5e8ffb14a72a8998150cd18cb58ccd2";	
	private static String chaincodeURL = "https://a7d850c513304ad682e2d453f0ed1538-vp0.us.blockchain.ibm.com:5003/chaincode";
	
	public boolean insertLedger(TransactionLedgerDO dataDO){
		
		String status = "";
		String amount = dataDO.getSettlementAmount().replace("$", "");
		if(Integer.parseInt(amount) > 10000){
			status = "Pending";
		}else{
			status = "Settled";
		}
		
		String data = "\""+ dataDO.getSettlementID() +"\","+
				      "\""+ dataDO.getOriginatingBankName() +"\","+
				      "\""+ dataDO.getOriginatingAccountName() +"\","+
				      "\""+ dataDO.getOriginatingAccountNumber() +"\","+
				      "\""+ dataDO.getReceiverBankName() +"\","+
				      "\""+ dataDO.getReceiverAccountName() +"\","+
					  "\""+ dataDO.getReceiverAccountNumber() +"\","+
				      "\""+ dataDO.getSettlementAmount() +"\","+
				      "\""+ dataDO.getSettlementDateTime() +"\","+
				      "\""+ status +"\"";
		
		String req = buildJsonRequest("WebAppAdmin", "invoke", "create_ledger", data);
		String jsonInString = callBlockChainAPI("POST", req);
		if(jsonInString.contains("\"status\":\"OK\""))
			return true;
		else
			return false;
	}
	
	public ArrayList<TransactionLedgerDO> queryLedgers(){
		ArrayList<TransactionLedgerDO> dataList = new ArrayList<TransactionLedgerDO>();
		
		String req = buildJsonRequest("WebAppAdmin", "query", "get_ledgers", "");
		String jsonInString = callBlockChainAPI("POST", req);
		jsonInString = jsonInString.substring(jsonInString.indexOf("message")+10,jsonInString.indexOf("\"},\"id"));
		jsonInString = jsonInString.replaceAll("\\\\","");
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// Convert JSON string to Object
			dataList = mapper.readValue(jsonInString, new TypeReference<List<TransactionLedgerDO>>(){});

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	public boolean updateStatus(String settlementID, String status){
		String data = "\""+ settlementID +"\","+
			          "\""+ status +"\"";
		
		String req = buildJsonRequest("WebAppAdmin", "invoke", "update_ledger_status", data);
		String jsonInString = callBlockChainAPI("POST", req);
		
		return true;
	}
	
	private static String callBlockChainAPI(String httpMethod, String jsonRequest){
		String output = "";
		
		try {

			System.out.println("Before calling chain code URL");
			URL url = new URL(chaincodeURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if(httpMethod.equalsIgnoreCase("GET")){
				conn.setRequestMethod(httpMethod);
				conn.setRequestProperty("Accept", "application/json");
			}else{
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json charset=UTF-8");
				
				OutputStream os = conn.getOutputStream();
				os.write(jsonRequest.getBytes("UTF-8"));
				os.flush();
			}

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String line;
			System.out.println("Output from Server .... \n");
			while ((line = br.readLine()) != null) {
				output = line;
				System.out.println(output);
			}

			conn.disconnect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	private static String buildJsonRequest(String userId, String method, String ctorMsg, String parameters){
	
		String jsonReq = "{" +
							"\"jsonrpc\": \"2.0\","+
							"\"method\": \""+method+"\","+
							"\"params\": {"+
								"\"type\": 1,"+
								"\"chaincodeID\": {"+
								"\"name\": \""+chaincodeID+"\""+
								"},"+
								"\"ctorMsg\": {"+
									"\"function\": \""+ctorMsg+"\","+
									"\"args\":["+ parameters +"]"+
								"},"+
								"\"secureContext\": \""+userId+"\""+
							"},"+
							"\"id\": 0"+
						  "}";
	
		return jsonReq;
	}
	
	public static void main(String[] args){
		
		// Demo Purpose...
		TransactionLedgerDO data1 = new TransactionLedgerDO("100$","Settled","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");
		TransactionLedgerDO data2 = new TransactionLedgerDO("100$","Settled","MGI Business Account","US Bank","7664568765","Cub Foods Business Account","Citi","6654534234");
		TransactionLedgerDO data3 = new TransactionLedgerDO("250$","Settled","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");
		TransactionLedgerDO data4 = new TransactionLedgerDO("15000$","Pending","Walmart Business Account","BOA","5463736254","MGI Business Account","US Bank","7664568765");

		BlockChainImpl obj = new BlockChainImpl();	
		obj.insertLedger(data1);
		obj.insertLedger(data2);
		obj.insertLedger(data3);
		obj.insertLedger(data4);
		
		
		ArrayList<TransactionLedgerDO> temp = obj.queryLedgers();
		System.out.println(temp.size());
		
	}
}

