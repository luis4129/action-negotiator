package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.repository.AccountRepository;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Iterable<Account> findAll() {
		return accountRepository.findAll();
	}
	
	public Account save(String email, BigDecimal fund) throws DuplicateConstraintException {
		return this.save(new Account(email, fund));
	}
	
	public Account save(Account account) throws DuplicateConstraintException {
		if (account.getId() == null && emailAlreadyExists(account.getEmail())) {
			throw new DuplicateConstraintException("Já existe uma conta com o email " + account.getEmail() + " cadastrada.");
		}
		
		return accountRepository.save(account);
	}

	private boolean emailAlreadyExists(String email) {
		return accountRepository.findByEmail(email) != null;
	}

}
