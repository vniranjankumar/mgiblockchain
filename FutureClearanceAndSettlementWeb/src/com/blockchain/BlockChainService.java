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
	
	public ArrayList<TransactionLedgerDO> queryTranData();

}
