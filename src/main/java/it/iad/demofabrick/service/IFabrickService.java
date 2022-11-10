package it.iad.demofabrick.service;

import java.util.List;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.model.Transaction;
import it.iad.demofabrick.model.Transfer;

public interface IFabrickService {
	public String readBalance(Long accountId) throws BalanceException;
	public String saveTransfer(Long accountId,Transfer transfer) throws BalanceException;
	public List<Transaction> listTransaction(Long accountId, String fromAccountingDate, String toAccountingDate);
}
