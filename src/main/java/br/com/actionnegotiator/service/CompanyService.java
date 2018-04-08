package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.repository.CompanyRepository;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private InvestmentRuleService investmentRuleService;
	
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public Iterable<Company> findAll() {
		return companyRepository.findAll();
	}

	public Company updateValue(Company company, BigDecimal value) throws DuplicateConstraintException {
		company.setValue(value);
		company = companyRepository.save(company);
		investmentRuleService.monitor(company);
		return company;
	}
	
	public Company save(Company company) throws DuplicateConstraintException {
		if (nameAlreadyExists(company.getName())) {
			throw new DuplicateConstraintException("Já existe uma empresa com o nome " + company.getName() + " cadastrada.");
		}		
		return companyRepository.save(company);		
	}
	
	public Company save(String name, BigDecimal value) throws DuplicateConstraintException {		
		Company company = new Company(name, value);
		return this.save(company);
	}
	
	private boolean nameAlreadyExists(String name) {
		return companyRepository.findByName(name) != null;
	}
	
}
