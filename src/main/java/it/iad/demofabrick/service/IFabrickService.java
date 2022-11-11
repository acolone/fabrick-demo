package it.iad.demofabrick.service;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.model.Balance;
import it.iad.demofabrick.model.ListTransaction;
import it.iad.demofabrick.model.Transfer;
import it.iad.demofabrick.model.TransferResult;

public interface IFabrickService {
	public Balance readBalance(Long accountId) throws BalanceException;
	public TransferResult saveTransfer(Long accountId,Transfer transfer) throws BalanceException;
	public ListTransaction listTransaction(Long accountId, String fromAccountingDate, String toAccountingDate);
}
