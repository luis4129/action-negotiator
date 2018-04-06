package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repository;

	@Autowired
	private MonitorService monitorService;

	public Iterable<Company> findAll() {
		return repository.findAll();
	}

	public void save(Company company) {
		repository.save(company);
		monitorService.monitor(company);
	}

	public void save(String name, BigDecimal value) {
		Company company = new Company(name, value);
		this.save(company);
	}

}
