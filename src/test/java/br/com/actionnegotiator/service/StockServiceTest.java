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
	
	private StockService stockService;
	
	private Stock stock;
	
	private Account account;
	
	private Company company;
	
	private static final BigDecimal quantity = BigDecimal.TEN;
	
	@Before
	public void setUp() {
		stockService = new StockService(stockRepository);
		account = new Account(1L);
		company = new Company(1L);
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
	
}
