package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	public Iterable<Account> findAll() {
		return repository.findAll();
	}

	public void save(Account account) {
		repository.save(account);
	}

	public void save(String email, BigDecimal fund) {
		Account account = new Account(email, fund);
		this.save(account);
	}

}
