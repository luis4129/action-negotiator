package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;

public interface InvestmentRuleRepository extends CrudRepository<InvestmentRule, Long>  {
	
	Iterable<InvestmentRule> findAllByCompany(Company company);	

}
