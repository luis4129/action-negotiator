package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.model.Transaction;
import br.com.actionnegotiator.model.TransactionType;
import br.com.actionnegotiator.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private EmailService emailService;

	public void save(Transaction transaction) {
		repository.save(transaction);
		emailService.notify(transaction);
	}

	public void save(TransactionType transactionType, Account account, Company company, BigDecimal value,
			BigDecimal quantity) {
		Transaction transaction = new Transaction(transactionType, account, company, value, quantity);
		this.save(transaction);		
	}

	public Iterable<Transaction> findAll() {
		return repository.findByOrderByCreatedInDesc();
	}
	
	public Iterable<Transaction> findAllRecent() {
		return repository.findByRecentOrderByCreatedInDesc(true);
	}
	
	public void setAllRecentToFalse() {
		repository.setAllRecentToFalse();
	}
	
}
