package main 

import (
	"fmt"
	"errors"
	"encoding/json"
	"github.com/hyperledger/fabric/core/chaincode/shim"
)

var logger = shim.NewLogger("CLDChaincode")

//==============================================================================================================================
//	 Structure Definitions
//==============================================================================================================================
//	Chaincode - A blank struct for use with Shim (A HyperLedger included go file used for get/put state
//				and other HyperLedger functions)
//==============================================================================================================================
type  SimpleChaincode struct {
}

func main() {
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}

//==============================================================================================================================
//	SettlementLedger - Defines the structure for a ledger object. JSON on right tells it what JSON fields to map to
//			  		   that element when reading a JSON object into the struct e.g. JSON datetime -> Struct datetime.
//==============================================================================================================================
type SettlementLedger struct {
	SettlementID           	  string `json:"settlementID"`
	OriginatingBankName       string `json:"originatingBankName"`
	OriginatingAccountName    string `json:"originatingAccountName"`
	OriginatingAccountNumber  string `json:"originatingAccountNumber"`
	ReceiverBankName   		  string `json:"receiverBankName"`
	ReceiverAccountName		  string `json:"receiverAccountName"`
	ReceiverAccountNumber     string `json:"receiverAccountNumber"`
	SettlementAmount	      string `json:"settlementAmount"`
	SettlementDateTime  	  string `json:"settlementDateTime"`
	SettlementStatus  	  	  string `json:"settlementStatus"`
}

//==============================================================================================================================
//	Ledger Holder - Defines the structure that holds all the settlementIDs for SettlementLedger that have been created.
//				    Used as an index when querying all records.
//==============================================================================================================================
type Ledger_Holder struct {
	SettlementIDs []string `json:"settlementIDs"`
}


//==============================================================================================================================
//	Init Function - Called when the user deploys the chaincode
//==============================================================================================================================
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	fmt.Println("invoke Init Method")
	
	var ledgerHld Ledger_Holder
	  
    if len(args) != 1 {
        return nil, errors.New("Incorrect number of arguments. Expecting 1")
    }

    bytes, err := json.Marshal(ledgerHld)

    if err != nil { 
    	return nil, errors.New("Error creating TRAN_Holder record") 
    }

	err = stub.PutState("tranIDs", bytes)
	
    return nil, nil
}

//==============================================================================================================================
//	Router Functions
//==============================================================================================================================
//	Invoke - Called on chaincode invoke. Takes a function name passed and calls that function.
//==============================================================================================================================
func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	fmt.Println("invoke is running " + function)

	// Handle different functions
	if function == "init" {													//initialize the chaincode state, used as reset
		return t.Init(stub, "init", args)
	}else if function == "create_ledger" {
        return t.create_ledger(stub, args)
	}else if function == "update_ledger_status" {
        return t.update_ledger_status(stub, args)
	}else if function == "ping" {
        return t.ping(stub)
    }
	
	return nil, errors.New("Received unknown function invocation: " + function)
}

//=================================================================================================================================
//	Query - Called on chaincode query. Takes a function name passed and calls that function. Passes the
//  		initial arguments passed are passed on to the called function.
//=================================================================================================================================
func (t *SimpleChaincode) Query(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	fmt.Println("Query is running " + function)
	
	if function == "get_settlementIDs"{
		bytes, err := stub.GetState("settlementIDs")
		if err != nil { 
			return nil, errors.New("Unable to get tranIDs") 
		}
		
		return bytes, nil
	}else if function == "get_ledgers" {	
		bytes, err := stub.GetState("settlementIDs")
		if err != nil { 
			return nil, errors.New("Unable to get settlementIDs") 
		}
	
		var ledgerHld Ledger_Holder
		err = json.Unmarshal(bytes, &ledgerHld)
		if err != nil {	
			return nil, errors.New("Corrupt Ledger_Holder record") 
		}
		
		var temp []byte
		result := "["
		for _, settlementID := range ledgerHld.SettlementIDs {		
			ledger, err := t.retrieve_ledger(stub, settlementID)
			if err != nil { 
				fmt.Printf("QUERY: Error retrieving settlementID: %s", err); 
				return nil, errors.New("QUERY: Error retrieving settlementID "+err.Error()) 
			}
			
			temp, err = json.Marshal(ledger)
			if err == nil {
				result += string(temp) + ","
			}
		}
		
		if len(result) == 1 {
			result = "[]"
		} else {
			result = result[:len(result)-1] + "]"
		}
		
		return []byte(result), nil
	}

	return nil, errors.New("Received unknown function invocation " + function)
}

//==============================================================================================================================
//	 retrieve_ledger - Gets the state of the data at settlementID in the ledger then converts it from the stored
//					JSON into the SettlementLedger struct for use in the contract. Returns the SettlementLedger struct.
//					Returns empty SettlementLedger if it errors.
//==============================================================================================================================
func (t *SimpleChaincode) retrieve_ledger(stub shim.ChaincodeStubInterface, settlementID string) (SettlementLedger, error) {

	var ledger SettlementLedger

	bytes, err := stub.GetState(settlementID);

	if err != nil {	
		fmt.Printf("retrieve_ledger: Failed to retrieving SettlementLedger: %s", err); 
		return ledger, errors.New("retrieve_ledger: Error retrieving SettlementLedger with settlementID = " + settlementID) 
	}

	err = json.Unmarshal(bytes, &ledger);

    if err != nil {	
    	fmt.Printf("retrieve_ledger: Corrupt Event record "+string(bytes)+": %s", err); 
    	return ledger, errors.New("retrieve_ledger: Corrupt Event record"+string(bytes))	
    }

	return ledger, nil
}

//==============================================================================================================================
//
//==============================================================================================================================
func (t *SimpleChaincode) update_ledger_status(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	ledger, err := t.retrieve_ledger(stub, args[0])
	if err != nil { 
		fmt.Printf("QUERY: Error retrieving settlementID: %s", err); 
		return nil, errors.New("QUERY: Error retrieving settlementID "+err.Error()) 
	}
	
	return nil, nil;
}

//=================================================================================================================================
//	 Create Function
//=================================================================================================================================
//	 Create Settlement Ledger - Creates the initial JSON for the Settlement Ledger and then saves it to the Shared Ledger.
//=================================================================================================================================
func (t *SimpleChaincode) create_ledger(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	var ledger SettlementLedger
	
	settlementID     			:= "\"SettlementID\":\""+args[0]+"\", "
	originatingBankName     	:= "\"OriginatingBankName\":\""+args[1]+"\", "
	originatingAccountName      := "\"OriginatingAccountName\":\""+args[2]+"\", "
	originatingAccountNumber    := "\"OriginatingAccountNumber\":\""+args[3]+"\", "
	receiverBankName 			:= "\"ReceiverBankName\":\""+args[4]+"\", "
	receiverAccountName     	:= "\"ReceiverAccountName\":\""+args[5]+"\","
	receiverAccountNumber     	:= "\"ReceiverAccountNumber\":\""+args[6]+"\","
	settlementAmount   			:= "\"SettlementAmount\":\""+args[7]+"\","
	settlementDateTime			:= "\"SettlementDateTime\":\""+args[8]+"\""
	settlementStatus			:= "\"SettlementStatus\":\""+args[9]+"\""

    // Concatenates the variables to create the total JSON object
	event_json := "{"+settlementID+originatingBankName+originatingAccountName+originatingAccountNumber+receiverBankName+receiverAccountName+receiverAccountNumber+
						settlementAmount+settlementDateTime+settlementStatus+"}" 	
							
	// Convert the JSON defined above into a SettlementLedger object for go
	err := json.Unmarshal([]byte(event_json), &ledger)										
	if err != nil { 
		return nil, errors.New("Invalid JSON object") 
	}

	bytes, err := json.Marshal(ledger)
	if err != nil { 
		return nil, errors.New("Error converting SettlementLedger") 
	}

	// Save new ledger record
	err = stub.PutState(ledger.SettlementID, bytes)
	if err != nil { 
		fmt.Printf("create_event: Error storing SettlementLedger: %s", err); 
		return nil, errors.New("Error storing SettlementLedger") 
	}

	// Update SettlementIDs with newly created ID and store it in chain.
	bytes, err = stub.GetState("settlementIDs")
	if err != nil { 
		return nil, errors.New("Unable to get SettlementID") 
	}

	var ledgerHld Ledger_Holder
	err = json.Unmarshal(bytes, &ledgerHld)
	if err != nil {	
		return nil, errors.New("Corrupt Ledger_Holder record") 
	}

	ledgerHld.SettlementIDs = append(ledgerHld.SettlementIDs, args[0])
	bytes, err = json.Marshal(ledgerHld)

	err = stub.PutState("settlementIDs", bytes)
	if err != nil { 
		fmt.Printf("create_event: Error storing SettlementIDs: %s", err); 
		return nil, errors.New("Error storing SettlementIDs") 
	}

	return nil, nil 
}


//==============================================================================================================================
//	 get_username - Retrieves the username of the user who invoked the chaincode.
//				  Returns the username as a string.
//==============================================================================================================================

func (t *SimpleChaincode) get_username(stub shim.ChaincodeStubInterface) (string, error) {

    username, err := stub.ReadCertAttribute("username");
	if err != nil { return "", errors.New("Couldn't get attribute 'username'. Error: " + err.Error()) }
	return string(username), nil
}

//=================================================================================================================================
//	 Ping Function
//=================================================================================================================================
//	 Pings the peer to keep the connection alive
//=================================================================================================================================
func (t *SimpleChaincode) ping(stub shim.ChaincodeStubInterface) ([]byte, error) {
	return []byte("Hello, world!"), nil
}


