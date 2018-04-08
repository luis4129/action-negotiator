package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Long>  {
	
	public Company findByName(String name);
	
}
