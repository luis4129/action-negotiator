package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.actionnegotiator.enums.TransactionType;
import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.Transaction;
import br.com.actionnegotiator.repository.TransactionRepository;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	private TransactionService transactionService;
	
	private Transaction transaction;
	
	private Account account;
	
	private Company company;
	
	private static final Calendar createdIn = Calendar.getInstance();
	private static final BigDecimal value = BigDecimal.valueOf(10.27);
	private static final BigDecimal quantity = BigDecimal.valueOf(100);
	private static final TransactionType transactionType = TransactionType.PURCHASE;
	private static final boolean recent = true;
	
	@Before
	public void setUp() {
		transactionService = new TransactionService(transactionRepository);
		account = new Account(1L);
		company = new Company(1L);
		transaction =  new Transaction(account, company, createdIn, value, quantity, transactionType, recent);
	}
	
	@Test
	public void mustExecuteRepositoryFindAll() {
		transactionService.findAll();
		Mockito.verify(transactionRepository).findByOrderByCreatedInDesc();
	}
	
	@Test
	public void mustExecuteRepositoryFindAllRecent() {
		transactionService.findAllRecent();
		Mockito.verify(transactionRepository).findByRecentOrderByCreatedInDesc(true);
	}
	
	@Test
	public void mustExecuteRepositorySave() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		transactionService.save(transaction);
		Mockito.verify(transactionRepository).save(transaction);
	}
	
	@Test
	public void mustExecuteRepositorySetAllRecentToFalse() {
		transactionService.setAllRecentToFalse();
		Mockito.verify(transactionRepository).setAllRecentToFalse();
	}
	
}
