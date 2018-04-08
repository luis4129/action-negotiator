package br.com.actionnegotiator.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.actionnegotiator.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>  {
	
	public Account findByEmail(String email);

}
