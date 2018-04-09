package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Company;

@Service
public class SimulationService {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private InvestmentRuleService investmentRuleService;

	public void executeSimulation() throws InterruptedException, DuplicateConstraintException, StringLengthException, BigDecimalLengthException {

		transactionService.setAllRecentToFalse();
		Iterable<Company> companies = companyService.findAll();

		for (int i = 0; i < 100; i++) {
			for (Company company : companies) {
				company.setValue(BigDecimal.valueOf(10 + (Math.random())));
				investmentRuleService.monitor(companyService.save(company));
			}
			Thread.sleep(5000);
		}
		
	}

}
