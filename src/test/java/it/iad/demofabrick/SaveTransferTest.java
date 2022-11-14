package it.iad.demofabrick;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.iad.demofabrick.exception.BalanceException;
import it.iad.demofabrick.exception.ErrorMessage;
import it.iad.demofabrick.model.Result;
import it.iad.demofabrick.model.Transfer;
import it.iad.demofabrick.model.TransferResult;
import it.iad.demofabrick.service.impl.FabrickServiceImpl;

/**
 * @author acolone
 * 
 */

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SaveTransferTest {

	@Autowired
	private FabrickServiceImpl fabrickService;

	@MockBean
	private RestTemplate restTemplate;

	@BeforeEach
	public void setUpForAll() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Nested
	class TestCasesForSaveTransferWithResultOK {

		@SuppressWarnings("unchecked")
		@BeforeEach
		void setUp() {
			Result<TransferResult> responseBodySaveTransfer = new Result<>();
			responseBodySaveTransfer.setPayload(geTransferOutput());

			ResponseEntity<Result<TransferResult>> responseEntityGetSaveTransfer = new ResponseEntity<Result<TransferResult>>(
					responseBodySaveTransfer, HttpStatus.OK);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.endsWith("money-transfers"),
					ArgumentMatchers.same(HttpMethod.POST), ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(responseEntityGetSaveTransfer);
		}

		
		@Test
		public void saveTransfer_whenResultIsOk_andTransferIsNotNull() {
			Long accountId = 1L;
			Transfer transfer = geTransferInput();
			TransferResult result = fabrickService.saveTransfer(accountId, transfer);
			assertThat(result.getCro()).isEqualTo(geTransferOutput().getCro());
		}
	}
	
	@Test
	public void saveTransfer_whenTransferIsNull() {
		try {
			Long accountId = 1L;
			fabrickService.saveTransfer(accountId, null);
		} catch (BalanceException e) {
			assertThat(e.getErrorCode().equals("422"));
		}
	}
	
	@Nested
	class TestCasesForSaveTransferWithResultKO {
		
		@SuppressWarnings("unchecked")
		@BeforeEach
		void setUp() {
			ResponseEntity<ErrorMessage> responseBodySaveTransfer = new ResponseEntity<ErrorMessage>(
					geErrorResult(), HttpStatus.BAD_REQUEST);

			Mockito.when(restTemplate.exchange(ArgumentMatchers.endsWith("money-transfers"),
					ArgumentMatchers.same(HttpMethod.POST), ArgumentMatchers.any(HttpEntity.class),
					ArgumentMatchers.any(ParameterizedTypeReference.class))).thenReturn(responseBodySaveTransfer);
		}
		
		@Test
		public void readBalance_whenResultIsKO() {
			Long accountId = 1L;
			Transfer transfer = geTransferInput();
			try {
				fabrickService.saveTransfer(accountId, transfer);
			} catch (BalanceException e) {
				assertThat(e.getErrorCode()).isEqualTo(geErrorResult().getErrors().get(0).getCode());
			}
		}
	}
	
	private Transfer geTransferInput() {
		Transfer transfer = null;
		try {
			InputStream inJson = Transfer.class.getResourceAsStream("/TestSaveTransferInput.json");
			transfer = new ObjectMapper().readValue(inJson, Transfer.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transfer;
	}
	
	private TransferResult geTransferOutput() {
		TransferResult transfer = null;
		try {
			InputStream inJson = TransferResult.class.getResourceAsStream("/TestSaveTransferOutput.json");
			transfer = new ObjectMapper().readValue(inJson, TransferResult.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transfer;
	}
	
	private ErrorMessage geErrorResult() {
		ErrorMessage t = null;
		try {
			InputStream inJson = Result.class.getResourceAsStream("/ErrorResultSaveTransfer.json");
			t = new ObjectMapper().readValue(inJson, ErrorMessage.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
}
