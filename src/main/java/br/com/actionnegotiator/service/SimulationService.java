package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Company;

@Service
public class SimulationService {
	
	@Autowired
	private CompanyService companyService;
	
	public void simulate() throws InterruptedException{
		
		Iterable<Company> companys = companyService.findAll();
		
		for (int i = 0; i < 100; i++) {
			for (Company company : companys) {
				company.setValue(BigDecimal.valueOf(10 + (Math.random())));
				companyService.save(company);
			}			
			//Thread.sleep(5000);
		}
	}
	
	
}
