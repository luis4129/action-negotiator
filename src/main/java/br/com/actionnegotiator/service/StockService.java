package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.model.TransactionType;
import br.com.actionnegotiator.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private StockRepository repository;

	@Autowired
	private TransactionService transstockService;

	@Autowired
	private AccountService accountService;

	public void save(Stock stock) {
		repository.save(stock);
	}

	public void delete(Stock stock) {
		repository.delete(stock);
	}

	public void purchaseStock(Account account, Company company) {
		BigDecimal quantity = account.getFund().divide(company.getValue(), 2, RoundingMode.HALF_EVEN);
		account.setFund(BigDecimal.ZERO);
		Stock stock = new Stock(account, company, quantity);
		transstockService.registerTransaction(TransactionType.PURCHASE, account, company, company.getValue(),
				quantity);
		this.save(stock);
	}

	public void sellStock(Stock stock) {
		BigDecimal value = stock.getQuantity().multiply(stock.getCompany().getValue());
		Account account = stock.getAccount();
		account.setFund(value);
		accountService.save(account);
		transstockService.registerTransaction(TransactionType.SALE, account, stock.getCompany(),
				stock.getCompany().getValue(), stock.getQuantity());
		this.delete(stock);
	}
	
	public Iterable<Stock> findAllByAccount(Account account) {
		return repository.findAllByAccount(account);
	}

}
