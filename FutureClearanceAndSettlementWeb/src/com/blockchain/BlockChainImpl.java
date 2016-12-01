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
import com.database.TransactionLedgerDO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Niranjan
 *
 */
public class BlockChainImpl implements BlockChainService {
	
	private static String chaincodeID = "ec7bb54de1772fb7478ee6782bdbcb693283ec80bc0a4aed927eff72bbaa7612dd54f43b26ad8f39c32be44497ee65bec008e3751b099cbaecd48e494c3e0989";	
	private static String chaincodeURL = "https://e89e437ae32349eebfc1a0af95fb5b78-vp0.us.blockchain.ibm.com:5002/chaincode";
	
	public ArrayList<TransactionLedgerDO> queryTranData(){
		ArrayList<TransactionLedgerDO> dataList = new ArrayList<TransactionLedgerDO>();
		
		String req = buildJsonRequest("user_type1_0", "query", "get_event_details", "\"123\"");
		String jsonInString = callBlockChainAPI("POST", req);
		jsonInString.substring(jsonInString.indexOf("message")+10,jsonInString.indexOf("\"},"));
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// Convert JSON string to Object
			TransactionLedgerDO obj = mapper.readValue(jsonInString, TransactionLedgerDO.class);
			dataList.add(obj);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataList;
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
		
		String req = buildJsonRequest("user_type1_0", "query", "get_event_details", "\"123\"");
		System.out.println(req);
		String temp = callBlockChainAPI("POST", req);
		temp = temp.substring(temp.indexOf("message")+10,temp.indexOf("\"},"));
		System.out.println(temp);
	}
}
