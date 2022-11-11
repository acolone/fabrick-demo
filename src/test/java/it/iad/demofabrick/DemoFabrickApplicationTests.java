package it.iad.demofabrick;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.iad.demofabrick.model.Balance;
import it.iad.demofabrick.model.ListTransaction;
import it.iad.demofabrick.model.Result;

@SpringBootTest()
@TestMethodOrder(OrderAnnotation.class)
class DemoFabrickApplicationTests extends ResponseEntityExceptionHandler{
	
	@Value("${demofabrick.api.url}")
    private String apiUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpHeaders headers;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	@Order(1)
	public void testReadBalance() {
		String apiUrlBalance = apiUrl.concat("14537780/balance");
        HttpEntity<Result<Balance>> httpEntity = new HttpEntity<Result<Balance>>(headers);
        
        ResponseEntity<Result<Balance>> response = restTemplate.exchange(apiUrlBalance, HttpMethod.GET, httpEntity, 
        		new ParameterizedTypeReference<Result<Balance>>() {});
        BigDecimal result = response.getBody().getPayload().getAvailableBalance();
        System.out.println("testReadBalance:"+String.valueOf(result));
		
	}
	
	@Test
	@Order(2)
	public void testListTransacion() {
		String apiUrlTransactionList = apiUrl.concat("14537780/transactions?fromAccountingDate=2022-01-01&toAccountingDate=2022-04-01");
		HttpEntity<Result<ListTransaction>> httpEntity = new HttpEntity<Result<ListTransaction>>(headers);
		
		ResponseEntity<Result<ListTransaction>> listTransactionEntity = restTemplate.exchange(apiUrlTransactionList, HttpMethod.GET, httpEntity, 
        		new ParameterizedTypeReference<Result<ListTransaction>>() {});
        System.out.println("transaction list: "+listTransactionEntity.getBody().getPayload().getList().size());
	}
	

}
