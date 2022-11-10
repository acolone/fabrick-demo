package it.iad.demofabrick.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.model.BalanceAPI;
import it.iad.demofabrick.model.ListTransactionAPI;
import it.iad.demofabrick.model.Transaction;
import it.iad.demofabrick.model.Transfer;
import it.iad.demofabrick.model.TransferAPI;
import it.iad.demofabrick.service.IFabrickService;

@Service
public class FabrickServiceImpl implements IFabrickService {
	private static final Logger logger = LoggerFactory.getLogger(FabrickServiceImpl.class);
	
	@Autowired
    private Environment env;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpHeaders headers;
	

	@Override
	public String readBalance(Long accountId) throws BalanceException{
		logger.info("readBalance with parameter: " + accountId);
//		if (accountId == null) {
//			throw new BalanceException("401","Parametro 'accountId' è obbligatorio nella chiamata al servizio");
//		}
		String apiUrlBalance = env.getProperty("demofabrick.api.url") + accountId + "/balance";
		String availableBalance = null;
        try {
			HttpEntity<BalanceAPI> httpEntity = new HttpEntity<BalanceAPI>(headers);
			
	        ResponseEntity<BalanceAPI> balanceEntity = restTemplate.exchange(apiUrlBalance, HttpMethod.GET, httpEntity, BalanceAPI.class);
	        int statusCode = balanceEntity.getStatusCodeValue();
	        if(statusCode == 200) {
	        	availableBalance = String.valueOf(balanceEntity.getBody().getPayload().getAvailableBalance());
	        }else {
	        	throw new BalanceException("204",HttpStatus.NO_CONTENT,"Nessun dato trovato per accountId: " + accountId); 
	        }
        }catch(HttpClientErrorException ex) {
        	throw new BalanceException("403",HttpStatus.FORBIDDEN,"Nessun dato trovato per accountId: " + accountId);
        }catch (Exception e) {
        	throw new BalanceException("500",HttpStatus.INTERNAL_SERVER_ERROR,"Errore generico nella chiamata al servizio - " + e.getMessage()); 
		}
		return availableBalance;
	}

	@Override
	public String saveTransfer(Long accountId, Transfer transfer) throws BalanceException{
		logger.info("saveTransfer for account code: " + transfer.getCreditor().getAccount().getAccountCode());
		String apiUrlTransfer = env.getProperty("demofabrick.api.url") + accountId + "/payments/money-transfers";
		String status = null;
		try {
			HttpEntity<Transfer> httpEntity = new HttpEntity<Transfer>(transfer, headers);
			
			ResponseEntity<TransferAPI> transferEntity = restTemplate.exchange(apiUrlTransfer, HttpMethod.POST, httpEntity, TransferAPI.class);
	        status = transferEntity.getBody().getStatus();
	        
		}catch(BadRequest br){ 
            throw new BalanceException("API000",HttpStatus.BAD_REQUEST, br.getMessage());
        }
		catch(InternalServerError is){
            throw new BalanceException("API000",HttpStatus.INTERNAL_SERVER_ERROR,"Errore durante l'operazione - " + is.getMessage());
        }
		return status;
	}

	@Override
	public List<Transaction> listTransaction(Long accountId, String fromAccountingDate, String toAccountingDate) {
		logger.info("listTransaction with parameter: " + accountId);
		String apiUrlTransactionList = env.getProperty("demofabrick.api.url") + accountId + "/transactions?fromAccountingDate="+fromAccountingDate+"&toAccountingDate="+toAccountingDate;
		try {
			HttpEntity<ListTransactionAPI> httpEntity = new HttpEntity<ListTransactionAPI>(headers);
			
	        ResponseEntity<ListTransactionAPI> listTransactionEntity = restTemplate.exchange(apiUrlTransactionList, HttpMethod.GET, httpEntity, ListTransactionAPI.class);
	        
	        return listTransactionEntity.getBody().getPayload().getList();
        }catch(HttpClientErrorException ex) {
        	throw new BalanceException("403",HttpStatus.FORBIDDEN,"Nessun dato trovato per accountId: " + accountId);
        }catch (Exception e) {
        	throw new BalanceException("500",HttpStatus.INTERNAL_SERVER_ERROR,"Errore generico nella chiamata al servizio - " + e.getMessage()); 
		}
	}

}
