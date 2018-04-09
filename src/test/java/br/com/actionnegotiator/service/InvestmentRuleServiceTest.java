package br.com.actionnegotiator.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

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
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.model.Transaction;
import br.com.actionnegotiator.repository.InvestmentRuleRepository;

@RunWith(SpringRunner.class)
public class InvestmentRuleServiceTest {
	
	@MockBean
	private InvestmentRuleRepository investmentRuleRepository;
	
	private InvestmentRuleService investmentRuleService;
	
	private InvestmentRule investmentRule;
	
	private Account account;
	
	private Company company;
	
	private Transaction transaction;
	
	private static final BigDecimal purchasePrice = BigDecimal.valueOf(10.40);
	private static final BigDecimal salePrice = BigDecimal.valueOf(10.60);
	
	@Before
	public void setUp() {
		investmentRuleService = new InvestmentRuleService(investmentRuleRepository);
		account = new Account(1L);
		company = new Company(1L);
		investmentRule = new InvestmentRule(null, account, company, purchasePrice, salePrice);
	}
	
	@Test
	public void mustExecuteRepositoryFindAll() {
		investmentRuleService.findAll();
		Mockito.verify(investmentRuleRepository).findAll();
	}
	
	@Test
	public void mustGetCorrectValue() {
		transaction = new Transaction(account, company, TransactionType.PURCHASE);
		Collection<InvestmentRule> investmentRules = new ArrayList<InvestmentRule>();
		investmentRules.add(investmentRule);
		account.setInvestmentRules(investmentRules);		
		assertEquals(purchasePrice.doubleValue(), investmentRuleService.getRequestedValue(transaction).doubleValue(), 1e-15);
	}
	
	@Test
	public void mustExecuteRepositoryFindByCompany() {
		investmentRuleService.findByCompany(company);
		Mockito.verify(investmentRuleRepository).findByCompany(company);
	}
	
	@Test
	public void mustExecuteRepositorySave() throws DuplicateConstraintException, BigDecimalLengthException {
		investmentRuleService.save(investmentRule);
		Mockito.verify(investmentRuleRepository).save(investmentRule);
	}
	
	@Test
	public void mustExecuteRepositoryDelete() {
		investmentRuleService.delete(investmentRule);
		Mockito.verify(investmentRuleRepository).delete(investmentRule);
	}
	
	@Test(expected = DuplicateConstraintException.class)
	public void mustRespectUniqueKey() throws DuplicateConstraintException, BigDecimalLengthException {
		Mockito.when(investmentRuleRepository.findByAccountAndCompany(account, company)).thenReturn(investmentRule);
		investmentRuleService.save(investmentRule);		
	}
	
	@Test(expected = BigDecimalLengthException.class)
	public void mustRespectPurchasePriceLength() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		investmentRule.setPurchasePrice(BigDecimal.valueOf(1111111111111111111L).multiply(BigDecimal.valueOf(1000)));
		investmentRuleService.save(investmentRule);		
	}
	
	@Test(expected = BigDecimalLengthException.class)
	public void mustRespectSalePriceLength() throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		investmentRule.setSalePrice(BigDecimal.valueOf(1111111111111111111L).multiply(BigDecimal.valueOf(1000)));
		investmentRuleService.save(investmentRule);		
	}
	
}
