/**
 * 
 */
package it.iad.demofabrick;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

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
import it.iad.demofabrick.model.Balance;
import it.iad.demofabrick.model.Result;
import it.iad.demofabrick.service.impl.FabrickServiceImpl;

/**
 * @author acolone
 * 
 */

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BalanceTest {

	@Autowired
	private FabrickServiceImpl fabrickService;

	@MockBean
	private RestTemplate restTemplate;

	@BeforeEach
	public void setUpForAll() {
		MockitoAnnotations.openMocks(this);
	}

	@Nested
	class TestCasesForReadBalanceWithResultOK {

		@SuppressWarnings("unchecked")
		@BeforeEach
		void setUp() {
			Result<Balance> responseBodyBalance = new Result<>();
			responseBodyBalance.setPayload(getMockBalance());

			ResponseEntity<Result<Balance>> responseEntityGetBalance = new ResponseEntity<Result<Balance>>(
					responseBodyBalance, HttpStatus.OK);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.endsWith("balance"),
					ArgumentMatchers.same(HttpMethod.GET), ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(responseEntityGetBalance);
		}

		@Test
		public void readBalance_whenResultIsOk_andBalancetIsNotNull() {
			Long accountId = 1L;
			Balance result = fabrickService.readBalance(accountId);
			assertThat(result.getBalance().doubleValue()).isEqualTo(getMockBalance().getBalance().doubleValue());
		}
	}

	@Test
	public void readBalance_whenAccountIdIsNull() {
		try {
			fabrickService.readBalance(null);
		} catch (BalanceException e) {
			assertThat(e.getErrorCode().equals("422"));
		}
	}
	
	@Nested
	class TestCasesForReadBalanceWithResultKO {
		
		@SuppressWarnings("unchecked")
		@BeforeEach
		void setUp() {
			ResponseEntity<Result<Balance>> responseEntityGetBalance = new ResponseEntity<Result<Balance>>(
					new Result<Balance>(), HttpStatus.BAD_REQUEST);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.endsWith("balance"),
					ArgumentMatchers.same(HttpMethod.GET), ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(responseEntityGetBalance);
		}
		
		@Test
		public void readBalance_whenResultIsNot200() {
			try {
				fabrickService.readBalance(2L);
			} catch (BalanceException e) {
				assertThat(e.getErrorCode().equals("204"));
			}
		}
	}

	private Balance getMockBalance() {
		Balance balance = new Balance();
		balance.setBalance(new BigDecimal("100.00"));
		return balance;
	}

}
