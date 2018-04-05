package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Action;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.TransactionType;
import br.com.actionnegotiator.repository.ActionRepository;

@Service
public class ActionService {
	
	@Autowired
	private ActionRepository repository;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	public void save(Action action){
		repository.save(action);
	}
	
	public void delete(Action action){
		repository.delete(action);
	}
	
	public void purchaseAction(Account account, Company company) {
		BigDecimal quantity = account.getFund().divide(company.getValue(), 2, RoundingMode.HALF_EVEN);
		account.setFund(BigDecimal.ZERO);		
		Action action = new Action(account, company, quantity);
		transactionService.registerTransaction(TransactionType.PURCHASE, account, company, company.getValue(), quantity);
		this.save(action);
	}
	
	public void sellAction(Action action) {
		BigDecimal value = action.getQuantity().multiply(action.getCompany().getValue());
		Account account = action.getAccount();
		account.setFund(value);
		accountService.save(account);
		transactionService.registerTransaction(TransactionType.SALE, account, action.getCompany(), action.getCompany().getValue(), action.getQuantity());
		this.delete(action);
	}
	
}
