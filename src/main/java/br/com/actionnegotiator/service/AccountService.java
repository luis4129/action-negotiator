package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Iterable<Account> findAll() {
		return accountRepository.findAll();
	}

	public void save(Account account) throws DataIntegrityViolationException {
		accountRepository.save(account);
	}

	public void save(String email, BigDecimal fund) throws DataIntegrityViolationException {
		Account account = new Account(email, fund);
		this.save(account);
	}

}
