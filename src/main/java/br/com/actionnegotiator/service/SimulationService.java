package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@Service
public class SimulationService {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private TransactionService transactionService;

	public void executeSimulation() throws InterruptedException, DuplicateConstraintException {

		transactionService.setAllRecentToFalse();
		Iterable<Company> companys = companyService.findAll();

		for (int i = 0; i < 100; i++) {
			for (Company company : companys) {
				company = companyService.updateValue(company, BigDecimal.valueOf(10 + (Math.random())));
			}
			//Thread.sleep(5000);
		}
		
	}

}
