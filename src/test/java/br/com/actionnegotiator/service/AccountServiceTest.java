package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.repository.AccountRepository;

@RunWith(SpringRunner.class)
public class AccountServiceTest {
	
	@MockBean
	private AccountRepository accountRepository;
	
	private AccountService accountService;
	
	private Account account;
	
	private static final String email = "luis4129@hotmail.com";
	private static final BigDecimal fund = BigDecimal.valueOf(1000);
	
	@Before
	public void setUp() {
		accountService = new AccountService(accountRepository);
		account = new Account(email, fund);
	}
	
	@Test
	public void mustExecuteRepositoryFindAll() {
		accountService.findAll();
		Mockito.verify(accountRepository).findAll();
	}
	
	@Test
	public void mustExecuteRepositorySave() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		accountService.save(account);
		Mockito.verify(accountRepository).save(account);
	}
	
	@Test(expected = DuplicateConstraintException.class)
	public void mustRespectUniqueKey() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		Mockito.when(accountRepository.findByEmail(email)).thenReturn(account);
		accountService.save(account);		
	}
	
	@Test(expected = StringLengthException.class)
	public void mustRespectEmailLength() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		account.setEmail("ABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZ"
				+ "ABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZ"
				+ "ABCDEFGHIJKLMNOPQRSTUVXYWZABCDEFGHIJKLMNOPQRSTUVXYWZ");
		accountService.save(account);		
	}
	
	@Test(expected = BigDecimalLengthException.class)
	public void mustRespectFundLength() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		account.setFund(BigDecimal.valueOf(1111111111111111111L).multiply(BigDecimal.valueOf(1000)));
		accountService.save(account);		
	}
	
}
