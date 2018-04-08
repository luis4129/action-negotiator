package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private InvestmentRuleService investmentRuleService;

	public Iterable<Company> findAll() {
		return companyRepository.findAll();
	}

	public Company save(Company company) throws DataIntegrityViolationException {
		company = companyRepository.save(company);
		investmentRuleService.monitor(company);
		return company;
	}
	public Company save(String name, BigDecimal value) throws DataIntegrityViolationException {
		Company company = new Company(name, value);
		return companyRepository.save(company);
	}

}
