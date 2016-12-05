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
	
	private static String chaincodeID = "86a12ae10a5b75eb1818de5f876e567e917b7d7b21d44822e65c0a6642d2fc88f112fc141aee7a2532b75d94a9da8f85483d28014e0f6d1416f6bf733cb071b3";	
	private static String chaincodeURL = "https://e4229cbb71ae4d7a8730530346055d41-vp0.us.blockchain.ibm.com:5002/chaincode";
	
	public boolean insertLedger(TransactionLedgerDO dataDO){
		String data = "\""+ dataDO.getSettlementID() +"\","+
				      "\""+ dataDO.getOriginatingBankName() +"\","+
				      "\""+ dataDO.getOriginatingAccountName() +"\","+
				      "\""+ dataDO.getOriginatingAccountNumber() +"\","+
				      "\""+ dataDO.getReceiverBankName() +"\","+
				      "\""+ dataDO.getReceiverAccountName() +"\","+
					  "\""+ dataDO.getReceiverAccountNumber() +"\","+
				      "\""+ dataDO.getSettlementAmount() +"\","+
				      "\""+ dataDO.getSettlementDateTime() +"\","+
				      "\""+ dataDO.getSettlementStatus() +"\"";
		
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
		
		
		BlockChainImpl obj = new BlockChainImpl();		
	}
}
