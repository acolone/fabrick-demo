package it.iad.demofabrick;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.model.ListTransaction;
import it.iad.demofabrick.model.Result;
import it.iad.demofabrick.model.Transaction;
import it.iad.demofabrick.service.impl.FabrickServiceImpl;

/**
 * @author acolone
 * 
 */

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransferTest {
	
	@Autowired
	private FabrickServiceImpl fabrickService;

	@MockBean
	private RestTemplate restTemplate;

	@BeforeEach
	public void setUpForAll() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Nested
	class TestCasesForTransactionListWithResultOK {

		@SuppressWarnings("unchecked")
		@BeforeEach
		void setUp() {
			Result<ListTransaction> responseBodyListTransaction = new Result<>();
			responseBodyListTransaction.setPayload(getMockTransactionList());

			ResponseEntity<Result<ListTransaction>> responseEntityGetListTransaction = new ResponseEntity<Result<ListTransaction>>(
					responseBodyListTransaction, HttpStatus.OK);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("transactions"),
					ArgumentMatchers.same(HttpMethod.GET), ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(ParameterizedTypeReference.class), ArgumentMatchers.any(Map.class))).thenReturn(responseEntityGetListTransaction);
		}

		
		@Test
		public void listTransaction_whenResultIsOk_andListIsNotNull() {
			Long accountId = 1L;
			String fromAccountingDateReqParam = "2022-01-01";
			String toAccountingDateReqParam = "2022-04-01";
			ListTransaction result = fabrickService.listTransaction(accountId, fromAccountingDateReqParam, toAccountingDateReqParam);
			assertThat(result.getList().size()).isEqualTo(getMockTransactionList().getList().size());
		}
	}
	
	@Test
	public void listTransaction_whenParametersIsNull() {
		try {
			fabrickService.listTransaction(null,"2022-01-01","2022-04-01");
		} catch (BalanceException e) {
			assertThat(e.getErrorCode().equals("422"));
		}
	}
	
	@Nested
	class TestCasesForTransactionListWithResultKO {
		
		@SuppressWarnings("unchecked")
		@BeforeEach
		void setUp() {
			ResponseEntity<Result<ListTransaction>> responseBodyListTransaction = new ResponseEntity<Result<ListTransaction>>(
					new Result<ListTransaction>(), HttpStatus.BAD_REQUEST);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("transactions"),
					ArgumentMatchers.same(HttpMethod.GET), ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(responseBodyListTransaction);
		}
		
		@Test
		public void readBalance_whenResultIsNot200() {
			try {
				fabrickService.listTransaction(1L,"2022-01-01","2022-04-01");
			} catch (BalanceException e) {
				assertThat(e.getErrorCode().equals("500"));
			}
		}
	}
	
	private ListTransaction getMockTransactionList() {
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		Transaction transaction = new Transaction();
		transaction.setTransactionId("1");
		transaction.setAmount(new BigDecimal("100.00"));
		list.add(transaction);
		
		ListTransaction trans = new ListTransaction();
		trans.setList(list);
		return trans;
	}

}
