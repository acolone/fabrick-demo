package it.iad.demofabrick.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.model.Transaction;
import it.iad.demofabrick.model.Transfer;
import it.iad.demofabrick.service.IFabrickService;

/**
 * @author acolone
 * 
 */

@RestController
@RequestMapping("/fabrick")
public class DemoFabrickController {
	private static final Logger logger = LoggerFactory.getLogger(DemoFabrickController.class);
	
	@Autowired
	private IFabrickService fabrickService;
	
	
	/**
	 * @param accountId <i>id del conto</i>
	 * @return ResponseEntity<String> <i>il saldo</i>
	 * metodo richiamato per recuperare il saldo in formato Json del conto passato in input <b>accountId</b>
	 * @throws BalanceException
	 */
	@GetMapping("/balance/{accountId}")
	public ResponseEntity<String> getBalance(@PathVariable("accountId") Long accountId) throws BalanceException{
		logger.info("call getBalance with parameter: " + accountId);
		return new ResponseEntity<String>(String.valueOf(fabrickService.readBalance(accountId).getAvailableBalance()), HttpStatus.OK);
	}
	
	/**
	 * @param accountId <i>id del conto</i>
	 * @param transfer <i>oggetto contenente i dati da salvare</i>
	 * @return ResponseEntity<String> <i>messaggio di avvenuto inserimento o di errore</i>
	 * metodo richiamato per salvare il bonifico passato in input al servizio.
	 * @throws BalanceException
	 */
	@PostMapping("/transfer/{accountId}/")
	public ResponseEntity<String> saveTransfer(@PathVariable("accountId") Long accountId, @Valid @RequestBody Transfer transfer) throws BalanceException{
		logger.debug("call saveTransfer with parameter: " + transfer);
		return new ResponseEntity<String>(fabrickService.saveTransfer(accountId, transfer).getStatus(), HttpStatus.OK);
	}
	
	/**
	 * @param accountId <i>id del conto</i>
	 * @param fromAccountingDate <i>data di inizio della condizione della query</i>
	 * @param toAccountingDate <i>data di fine della condizione della query</i>
	 * @return ResponseEntity<List<Transaction>> <i>la lista delle transazioni recuperate</i>
	 * metodo richiamato per visualizzare la lista delle transazioni in base ai parametri passati in input
	 */
	@GetMapping("/list-transaction")
	public ResponseEntity<List<Transaction>> getTransactionList(@RequestParam(required = true) Long accountId, 
												      @RequestParam(required = true) String fromAccountingDate,
												      @RequestParam(required = true) String toAccountingDate) {
		logger.debug("call getTransactionList with parameter accountId: " + accountId);
		return new ResponseEntity<List<Transaction>>(fabrickService.listTransaction(accountId, fromAccountingDate, toAccountingDate).getList(), HttpStatus.OK);
	}
	
}
