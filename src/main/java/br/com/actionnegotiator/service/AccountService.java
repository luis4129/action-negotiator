package br.com.actionnegotiator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.repository.AccountRepository;
import br.com.actionnegotiator.util.SystemUtil;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public AccountService() {
		super();
	}
	
	public AccountService(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	public Iterable<Account> findAll() {
		return accountRepository.findAll();
	}
	
	public Account save(Account account) throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		validateSave(account);
		return accountRepository.save(account);
	}
	
	private void validateSave(Account account) throws StringLengthException, BigDecimalLengthException, DuplicateConstraintException {
		if (SystemUtil.invalidStringLength(account.getEmail())) {
			throw new StringLengthException("E-mail maior do que o permitido, favor preencher o mesmo com menos de 255 caracteres.");
		} else if (SystemUtil.invalidBigDecimalLength(account.getFund())) {
			throw new BigDecimalLengthException("Fundo de investimento maior do que o permitido, favor preencher o mesmo com menos de 19 dígitos antes da vírgula.");
		} else if (account.getId() == null && emailAlreadyExists(account.getEmail())) {
			throw new DuplicateConstraintException("Já existe uma conta com o email " + account.getEmail() + " cadastrada.");
		}
	}

	private boolean emailAlreadyExists(String email) {
		return accountRepository.findByEmail(email) != null;
	}

}
