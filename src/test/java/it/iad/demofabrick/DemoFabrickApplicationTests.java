package it.iad.demofabrick;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.iad.demofabrick.model.BalanceAPI;
import it.iad.demofabrick.model.ListTransactionAPI;

@SpringBootTest()
@TestMethodOrder(OrderAnnotation.class)
class DemoFabrickApplicationTests {
	
	@Autowired
    private Environment env;
	
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
		String apiUrlBalance = env.getProperty("demofabrick.api.url").concat("14537780/balance");
        HttpEntity<BalanceAPI> httpEntity = new HttpEntity<BalanceAPI>(headers);
        
        ResponseEntity<BalanceAPI> response = restTemplate.exchange(apiUrlBalance, HttpMethod.GET, httpEntity, BalanceAPI.class);
        System.out.println("testReadBalance:"+String.valueOf(response.getBody().getPayload().getAvailableBalance()));
	}
	
	@Test
	@Order(2)
	public void testListTransacion() {
		String apiUrlTransactionList = env.getProperty("demofabrick.api.url").concat("14537780/transactions?fromAccountingDate=2022-01-01&toAccountingDate=2022-04-01");
		HttpEntity<ListTransactionAPI> httpEntity = new HttpEntity<ListTransactionAPI>(headers);
		
        ResponseEntity<ListTransactionAPI> listTransactionEntity = restTemplate.exchange(apiUrlTransactionList, HttpMethod.GET, httpEntity, ListTransactionAPI.class);
        System.out.println("transaction list: "+listTransactionEntity.getBody().getPayload().getList().size());
	}

}
