package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.repository.AccountRepository;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

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
	public void mustExecuteRepositorySave() throws DuplicateConstraintException {
		accountService.save(account);
		Mockito.verify(accountRepository).save(account);
	}
	
	@Test(expected = DuplicateConstraintException.class)
	public void mustRespectUniqueKey() throws DuplicateConstraintException {
		Mockito.when(accountRepository.findByEmail(email)).thenReturn(account);
		accountService.save(account);		
	}
	
}
