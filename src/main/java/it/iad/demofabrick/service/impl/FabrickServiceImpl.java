package it.iad.demofabrick.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.model.Balance;
import it.iad.demofabrick.model.ListTransaction;
import it.iad.demofabrick.model.Result;
import it.iad.demofabrick.model.Transfer;
import it.iad.demofabrick.model.TransferResult;
import it.iad.demofabrick.service.IFabrickService;

/**
 * @author acolone
 * 
 */

@Service
public class FabrickServiceImpl extends ResponseEntityExceptionHandler implements IFabrickService {
	private static final Logger logger = LoggerFactory.getLogger(FabrickServiceImpl.class);
	
	@Value("${demofabrick.api.url}")
    private String apiUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpHeaders headers;
	

	@Override
	public Balance readBalance(Long accountId) throws BalanceException{
		if (accountId == null) {
			throw new BalanceException("422",HttpStatus.UNPROCESSABLE_ENTITY,"Parametro 'accountId' mancante.");
		}
		logger.info("readBalance with parameter: " + accountId);
		String apiUrlBalance = apiUrl.concat(String.valueOf(accountId)).concat("/balance");
		Balance result = null;
        try {
			HttpEntity<Result<Balance>> httpEntity = new HttpEntity<Result<Balance>>(headers);
			
			ResponseEntity<Result<Balance>> balanceEntity = restTemplate.exchange(apiUrlBalance, HttpMethod.GET, httpEntity, 
					new ParameterizedTypeReference<Result<Balance>>() {});
	        int statusCode = balanceEntity.getStatusCodeValue();
	        if(statusCode == 200) {
	        	result = balanceEntity.getBody().getPayload();
	        }else {
	        	throw new BalanceException("204",HttpStatus.NO_CONTENT,"Nessun dato trovato per accountId: " + accountId); 
	        }
        }catch(HttpClientErrorException ex) {
        	throw new BalanceException("403",HttpStatus.FORBIDDEN,"Nessun dato trovato per accountId: " + accountId);
        }catch (Exception e) {
        	throw new BalanceException("500",HttpStatus.INTERNAL_SERVER_ERROR,"Errore generico nella chiamata al servizio - " + e.getMessage()); 
		}
		return result;
	}
	
	@Override
	public ListTransaction listTransaction(Long accountId, String fromAccountingDate, String toAccountingDate) {
		logger.info("listTransaction with parameter: " + accountId);
		if (accountId == null) {
			throw new BalanceException("422",HttpStatus.UNPROCESSABLE_ENTITY,"Parametro 'accountId' mancante.");
		}
		if(!StringUtils.hasLength(fromAccountingDate)){
			throw new BalanceException("422",HttpStatus.UNPROCESSABLE_ENTITY,"Parametro 'fromAccountingDate' mancante.");
		}
		if(!StringUtils.hasLength(toAccountingDate)){
			throw new BalanceException("422",HttpStatus.UNPROCESSABLE_ENTITY,"Parametro 'toAccountingDate' mancante.");
		}
		
		String apiUrlTransactionList = apiUrl.concat(String.valueOf(accountId)) + "/transactions";
		apiUrlTransactionList = UriComponentsBuilder.fromHttpUrl(apiUrlTransactionList)
									                .queryParam("fromAccountingDate", "{fromAccountingDate}")
									                .queryParam("toAccountingDate", "{toAccountingDate}")
									                .encode().toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("fromAccountingDate", fromAccountingDate);
        params.put("toAccountingDate", toAccountingDate);
		
		try {
			HttpEntity<Result<ListTransaction>> httpEntity = new HttpEntity<Result<ListTransaction>>(headers);
			
	        ResponseEntity<Result<ListTransaction>> listTransactionEntity = restTemplate.exchange(apiUrlTransactionList, HttpMethod.GET, httpEntity, 
	        		new ParameterizedTypeReference<Result<ListTransaction>>() {}, params);
	        return listTransactionEntity.getBody().getPayload();
        }catch(HttpClientErrorException ex) {
        	throw new BalanceException("403",HttpStatus.FORBIDDEN,"Nessun dato trovato per accountId: " + accountId);
        }catch (Exception e) {
        	throw new BalanceException("500",HttpStatus.INTERNAL_SERVER_ERROR,"Errore generico nella chiamata al servizio - " + e.getMessage()); 
		}
	}
	
	@Override
	public TransferResult saveTransfer(Long accountId, Transfer transfer) throws BalanceException{
		if (accountId == null) {
			throw new BalanceException("422",HttpStatus.UNPROCESSABLE_ENTITY,"Parametro 'accountId' mancante.");
		}
		if (transfer == null) {
			throw new BalanceException("422",HttpStatus.UNPROCESSABLE_ENTITY,"Parametro 'transfer' mancante.");
		}
		logger.info("saveTransfer for account code: " + transfer.getCreditor().getAccount().getAccountCode());
		String apiUrlTransfer = apiUrl.concat(String.valueOf(accountId)).concat("/payments/money-transfers");
		TransferResult result = null;
		try {
			HttpEntity<Transfer> httpEntity = new HttpEntity<Transfer>(transfer, headers);
			
	        ResponseEntity<Result<TransferResult>> transferEntity = restTemplate.exchange(apiUrlTransfer, HttpMethod.POST, httpEntity, 
	        		new ParameterizedTypeReference<Result<TransferResult>>() {});
	        result = transferEntity.getBody().getPayload();
		}catch(BadRequest br){ 
            throw new BalanceException("API000",HttpStatus.BAD_REQUEST, "Errore tecnico  La condizione BP049 non e' prevista per il conto id: " +accountId);
        }
		catch(InternalServerError is){
            throw new BalanceException("API000",HttpStatus.INTERNAL_SERVER_ERROR,"Errore durante l'operazione - " + is.getMessage());
        }catch(Exception ex) {
        	throw new BalanceException("API000",HttpStatus.BAD_REQUEST, ex.getMessage());
        }
		return result;
	}

}
