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
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.repository.InvestmentRuleRepository;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@RunWith(SpringRunner.class)
public class InvestmentRuleServiceTest {
	
	@MockBean
	private InvestmentRuleRepository investmentRuleRepository;
	
	private InvestmentRuleService investmentRuleService;
	
	private InvestmentRule investmentRule;
	
	private Account account;
	
	private Company company;	
	
	private static final BigDecimal purchasePrice = BigDecimal.valueOf(10.40);
	private static final BigDecimal salePrice = BigDecimal.valueOf(10.60);
	
	@Before
	public void setUp() {
		investmentRuleService = new InvestmentRuleService(investmentRuleRepository);
		account = new Account(1L);
		company = new Company(1L);
		investmentRule = new InvestmentRule(account, company, purchasePrice, salePrice);
	}
	
	@Test
	public void mustExecuteRepositorySave() throws DuplicateConstraintException {
		investmentRuleService.save(investmentRule);
		Mockito.verify(investmentRuleRepository).save(investmentRule);
	}
	
	@Test(expected = DuplicateConstraintException.class)
	public void mustRespectUniqueKey() throws DuplicateConstraintException {
		Mockito.when(investmentRuleRepository.findByAccountAndCompany(account, company)).thenReturn(investmentRule);
		investmentRuleService.save(investmentRule);		
	}
	
}
