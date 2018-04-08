package br.com.actionnegotiator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Transaction;
import br.com.actionnegotiator.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private EmailService emailService;

	public Transaction save(Transaction transaction) {
		transaction = transactionRepository.save(transaction);
		emailService.notify(transaction);
		return transaction;
	}

	public Iterable<Transaction> findAll() {
		return transactionRepository.findByOrderByCreatedInDesc();
	}
	
	public Iterable<Transaction> findAllRecent() {
		return transactionRepository.findByRecentOrderByCreatedInDesc(true);
	}
	
	public void setAllRecentToFalse() {
		transactionRepository.setAllRecentToFalse();
	}
	
}
