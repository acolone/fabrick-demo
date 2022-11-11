package it.iad.demofabrick;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ContextConfiguration(classes = DemoFabrickApplication.class)
@SpringBootTest()
@TestMethodOrder(OrderAnnotation.class)
class DemoFabrickApplicationTests extends ResponseEntityExceptionHandler{
	
	@Value("${demofabrick.api.url}")
    private String apiUrl;
	
	@Value("${demofabrick.test.accountId}")
    private String accountId;
	
	@Value("${demofabrick.test.fromaccountingdate}")
    private String fromAccountingDate;
	
	@Value("${demofabrick.test.toaccountingdate}")
    private String toAccountingDate;
	
	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@BeforeEach
	public void setUp() throws JSONException, IOException {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	@Order(1)
	public void saldoAccountIdNotFound() throws Exception
	{
		setUp();
		String apiUrlBalance = "/fabrick/balance/8008490000021";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(apiUrlBalance)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		
		assertTrue(status == 403);
	}
	
	@Test
	@Order(2)
	public void saldoAccountIdFound() throws Exception
	{
		String apiUrlBalance = "/fabrick/balance/"+accountId;
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(apiUrlBalance)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		
		assertTrue(status == 200);
	}
	
	@Test
	@Order(3)
	public void transactionsListOK() throws Exception {
		setUp();
		String fromAccountingDate = "2019-01-01";
		String toAccountingDate = "2019-12-01";
		String accountIdReqParam = "accountId="+accountId;
		String fromAccountingDateReqParam = "fromAccountingDate="+fromAccountingDate;
		String toAccountingDateReqParam = "toAccountingDate="+toAccountingDate;
		
		String apiUrlBalance = "/fabrick/list-transaction";
		String queryString = apiUrlBalance + "?" +accountIdReqParam + "&" +fromAccountingDateReqParam + "&" + toAccountingDateReqParam;
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(queryString)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		
		assertTrue(status == 200);
	}
	
	@Test
	@Order(4)
	public void transactionsListKO() throws Exception {
		setUp();
		Long accountId = 14537781L;
		String fromAccountingDate = "2019-01-01";
		String toAccountingDate = "2019-12-01";
		String accountIdReqParam = "accountId="+accountId;
		String fromAccountingDateReqParam = "fromAccountingDate="+fromAccountingDate;
		String toAccountingDateReqParam = "toAccountingDate="+toAccountingDate;
		
		String apiUrlBalance = "/fabrick/list-transaction";
		String queryString = apiUrlBalance + "?" +accountIdReqParam + "&" +fromAccountingDateReqParam + "&" + toAccountingDateReqParam;
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(queryString)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		
		assertTrue(status == 403 || status == 422 || status == 500);
	}
	

}
