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
	
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public Iterable<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company save(Company company) throws DuplicateConstraintException {
		if (company.getId() == null && nameAlreadyExists(company.getName())) {
			throw new DuplicateConstraintException("Já existe uma empresa com o nome " + company.getName() + " cadastrada.");
		}		
		return companyRepository.save(company);		
	}
	
	public Company save(String name, BigDecimal value) throws DuplicateConstraintException {		
		return this.save(new Company(name, value));
	}
	
	private boolean nameAlreadyExists(String name) {
		return companyRepository.findByName(name) != null;
	}
	
}
