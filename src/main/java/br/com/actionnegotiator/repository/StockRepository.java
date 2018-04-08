package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.Stock;

public interface StockRepository extends CrudRepository<Stock, Long>  {
	
	public Iterable<Stock> findAllByAccount(Account account);
	
	public Stock findByAccountAndCompany(Account account, Company company);

}
