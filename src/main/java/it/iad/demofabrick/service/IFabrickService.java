package it.iad.demofabrick.service;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.model.Balance;
import it.iad.demofabrick.model.ListTransaction;
import it.iad.demofabrick.model.Transfer;
import it.iad.demofabrick.model.TransferResult;

/**
 * @author acolone
 * 
 */

public interface IFabrickService {
	/**
	 * @param accountId <i>id del conto</i>
	 * @return Balance <i>il saldo del conto</i>
	 * @throws BalanceException
	 */
	public Balance readBalance(Long accountId) throws BalanceException;
	
	
	/**
	 * @param accountId <i>id del conto</i>
	 * @param transfer <i>l'oggetto contenente le informazioni da salvare</i> 
	 * @return TransferResult
	 * @throws BalanceException
	 */
	public TransferResult saveTransfer(Long accountId,Transfer transfer) throws BalanceException;
	
	/**
	 * @param accountId <i>id del conto</i>
	 * @param fromAccountingDate <i>data di inizio della condizione della query</i>
	 * @param toAccountingDate <i>data di fine della condizione della query</i>
	 * @return ListTransaction <i>la lista delle transazioni recuperate</i>
	 */
	public ListTransaction listTransaction(Long accountId, String fromAccountingDate, String toAccountingDate);
}
