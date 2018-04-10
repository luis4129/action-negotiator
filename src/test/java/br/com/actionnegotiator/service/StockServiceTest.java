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
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.repository.StockRepository;

@RunWith(SpringRunner.class)
public class StockServiceTest {
	
	@MockBean
	private StockRepository stockRepository;
	
	@MockBean
	private AccountService accountService;
	
	@MockBean
	private EmailService emailService;
	
	@MockBean
	private TransactionService transactionService;
			
	private StockService stockService;
	
	private Stock stock;
	
	private Account account;
	
	private Company company;
	
	private static final BigDecimal quantity = BigDecimal.TEN;
	private static final Long accountId = 1L;
	private static final String accountEmail = "teste@teste.com.br";
	private static final BigDecimal accountFund = BigDecimal.valueOf(1000);
	private static final Long companyId = 1L;
	private static final String companyName = "Google";
	private static final BigDecimal companyValue = BigDecimal.valueOf(10.80);
	
	@Before
	public void setUp() {
		stockService = new StockService(stockRepository, accountService, emailService, transactionService);
		account = new Account(accountId, accountEmail, accountFund);
		company = new Company(companyId, companyName, companyValue);
		stock = new Stock(account, company, quantity);
	}
	
	@Test
	public void mustExecuteRepositoryDelete() {
		stockService.delete(stock);
		Mockito.verify(stockRepository).delete(stock);
	}
	
	@Test
	public void mustExecuteRepositoryFindByAccountAndCompany() {
		stockService.findByAccountAndCompany(account, company);
		Mockito.verify(stockRepository).findByAccountAndCompany(account, company);
	}
	
	@Test
	public void mustExecuteRepositorySave() throws DuplicateConstraintException, BigDecimalLengthException {
		stockService.save(stock);
		Mockito.verify(stockRepository).save(stock);
	}
	
	@Test(expected = DuplicateConstraintException.class)
	public void mustRespectUniqueKey() throws DuplicateConstraintException, BigDecimalLengthException {
		Mockito.when(stockRepository.findByAccountAndCompany(account, company)).thenReturn(stock);
		stockService.save(stock);
	}
	
	@Test(expected = BigDecimalLengthException.class)
	public void mustRespectQuantityLength() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		stock.setQuantity(BigDecimal.valueOf(1111111111111111111L).multiply(BigDecimal.valueOf(1000)));
		stockService.save(stock);		
	}
	
	/*@Test
	public void purchaseMustCreateStock() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		stockService.purchaseStock(account, company);
		Mockito.verify(stockRepository).save(stock);
	}*/
	
	@Test
	public void purchaseMustUpdateAccountFund() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		stockService.purchaseStock(account, company);
		account.setFund(BigDecimal.ZERO);
		Mockito.verify(accountService).save(account);
	}
	
	@Test
	public void saleMustDeleteStock() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		stockService.sellStock(stock);
		Mockito.verify(stockRepository).delete(stock);
	}
	
	@Test
	public void saleMustUpdateAccountFund() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		stockService.sellStock(stock);
		account.setFund(quantity.multiply(companyValue));
		Mockito.verify(accountService).save(account);
	}			
	
}
