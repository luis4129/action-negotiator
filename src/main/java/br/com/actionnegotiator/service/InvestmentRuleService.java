package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import javax.mail.Session;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.repository.InvestmentRuleRepository;

@Service
public class InvestmentRuleService {

	@Autowired
	private InvestmentRuleRepository investmentRuleRepository;
	
	@Autowired
	private StockService stockService;

	public Iterable<InvestmentRule> findAll() {
		return investmentRuleRepository.findAll();
	}

	public InvestmentRule save(InvestmentRule investmentRule) throws DataIntegrityViolationException {
		return investmentRuleRepository.save(investmentRule);
	}

	public InvestmentRule save(Long accountId, Long companyId, BigDecimal purchaseValue, BigDecimal saleValue) throws DataIntegrityViolationException {
		Account account = new Account();
		account.setId(accountId);

		Company company = new Company();
		company.setId(companyId);

		InvestmentRule investmentRule = new InvestmentRule(account, company, purchaseValue, saleValue);
		return this.save(investmentRule);
	}

	public Iterable<InvestmentRule> findAllByCompany(Company company) {
		return investmentRuleRepository.findAllByCompany(company);
	}

	@Transactional
	public void monitor(Company company) {
		
		for (InvestmentRule investmentRule : this.findAllByCompany(company)) {

			System.out.println(company.getName() + " - " + company.getValue());
			
			if (purchaseCheck(company.getValue(), investmentRule.getPurchasePrice())) {
				Account account = investmentRule.getAccount();
				if (!account.getFund().equals(BigDecimal.ZERO)) {
					System.out.println("BOUGHT");
					stockService.purchaseStock(account, company);
				}
			}

			if (saleCheck(company.getValue(), investmentRule.getSalePrice())) {
				for (Stock stock : stockService.findAllByAccount(investmentRule.getAccount())) {
					if (stock.getCompany().getId().equals(company.getId())) {
						System.out.println("SOLD");
						stockService.sellStock(stock);
					}
				}				
			}
		}
	}

	private Boolean purchaseCheck(BigDecimal companyPrice, BigDecimal purchasePrice) {
		return companyPrice.compareTo(purchasePrice) < 1;
	}

	private Boolean saleCheck(BigDecimal companyPrice, BigDecimal salePrice) {
		return companyPrice.compareTo(salePrice) > -1;
	}
	
}
