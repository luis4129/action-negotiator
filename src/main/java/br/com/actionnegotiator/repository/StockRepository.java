package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Stock;

public interface StockRepository extends CrudRepository<Stock, Long>  {
	
	public Iterable<Stock> findAllByAccount(Account account);
	
	/*@Transactional
	void delete(Stock stock);*/

}
