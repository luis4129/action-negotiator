package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.repository.StockRepository;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

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
	public void mustExecuteRepositorySave() throws DuplicateConstraintException {
		stockService.save(stock);
		Mockito.verify(stockRepository).save(stock);
	}
	
	@Test(expected = DuplicateConstraintException.class)
	public void mustRespectUniqueKey() throws DuplicateConstraintException {
		Mockito.when(stockRepository.findByAccountAndCompany(account, company)).thenReturn(stock);
		stockService.save(stock);	
		//assertEquals()
	}
	
}
