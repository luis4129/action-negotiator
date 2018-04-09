package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;

public interface InvestmentRuleRepository extends CrudRepository<InvestmentRule, Long>  {
	
	public Iterable<InvestmentRule> findByCompany(Company company);	
	
	public InvestmentRule findByAccountAndCompany(Account account, Company company);

}
