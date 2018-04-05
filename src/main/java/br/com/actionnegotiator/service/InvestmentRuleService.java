package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.repository.InvestmentRuleRepository;

@Service
public class InvestmentRuleService {
	
	@Autowired
	private InvestmentRuleRepository repository;
	
	public Iterable<InvestmentRule> findAll(){		
		return repository.findAll();		
	}	
	
	public void save(InvestmentRule investmentRule){
		repository.save(investmentRule);
	}
	
	public void save(String name, BigDecimal value){
		InvestmentRule investmentRule = new InvestmentRule();		
		this.save(investmentRule);
	}
	
	public Iterable<InvestmentRule> findAllByCompany(Company company) {
		return repository.findAllByCompany(company);
	}
	
}
