package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.repository.InvestmentRuleRepository;

@Service
public class InvestmentRuleService {

	@Autowired
	private InvestmentRuleRepository repository;

	public Iterable<InvestmentRule> findAll() {
		return repository.findAll();
	}

	public void save(InvestmentRule investmentRule) {
		repository.save(investmentRule);
	}

	public void save(Long accountId, Long companyId, BigDecimal purchaseValue, BigDecimal saleValue) {
		Account account = new Account();
		account.setId(accountId);

		Company company = new Company();
		company.setId(companyId);

		InvestmentRule investmentRule = new InvestmentRule(account, company, purchaseValue, saleValue);
		this.save(investmentRule);
	}

	public Iterable<InvestmentRule> findAllByCompany(Company company) {
		return repository.findAllByCompany(company);
	}

}
