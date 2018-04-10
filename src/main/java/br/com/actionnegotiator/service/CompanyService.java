package br.com.actionnegotiator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.repository.CompanyRepository;
import br.com.actionnegotiator.util.SystemUtil;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public CompanyService() {
		super();
	}
	
	public CompanyService(CompanyRepository companyRepository) {
		super();
		this.companyRepository = companyRepository;
	}

	public Iterable<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company save(Company company) throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		validateSave(company);
		return companyRepository.save(company);		
	}
	
	private void validateSave(Company company) throws StringLengthException, BigDecimalLengthException, DuplicateConstraintException {
		if (SystemUtil.invalidStringLength(company.getName())) {
			throw new StringLengthException("Nome maior do que o permitido, favor preencher o mesmo com menos de 255 caracteres.");
		} else if (SystemUtil.invalidBigDecimalLength(company.getValue())) {
			throw new BigDecimalLengthException("Valor maior do que o permitido, favor preencher o mesmo com menos de 19 dígitos antes da vírgula.");
		} else if (company.getId() == null && nameAlreadyExists(company.getName())) {
			throw new DuplicateConstraintException("Já existe uma empresa com o nome " + company.getName() + " cadastrada.");
		}
	}
	
	private boolean nameAlreadyExists(String name) {
		return companyRepository.findByName(name) != null;
	}
	
}
