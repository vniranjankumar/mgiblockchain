/**
 * 
 */
package com.blockchain;

import java.util.ArrayList;

import com.database.TransactionLedgerDO;

/**
 * @author xh09
 *
 */
public interface BlockChainService {
	
	public boolean insertLedger(TransactionLedgerDO dataDO);
	public ArrayList<TransactionLedgerDO> queryLedgers();
	public boolean updateStatus(String settlementID, String status);

}
