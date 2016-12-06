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
	
	private static String chaincodeID = "55ca480f56bd2ef525cbb87e16599b63bb30a534d877b42c9ff3e0d2690695e24b89d0ebda8a391fc42b434bf3bcca13d12e9ac7cc01f7173e340568dda96f02";	
	private static String chaincodeURL = "https://fb64dd9844a846289a7c426c55348f9b-vp0.us.blockchain.ibm.com:5002/chaincode";
	
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
		
		TransactionLedgerDO data1 = new TransactionLedgerDO("100$","Settled","MGI Business Account","US Bank","7664568765","Cub Foods Business Account","Citi","6654534234");

		BlockChainImpl obj = new BlockChainImpl();	
		//obj.insertLedger(data1);
		ArrayList<TransactionLedgerDO> temp = obj.queryLedgers();
		System.out.println(temp.size());
		
	}
}
