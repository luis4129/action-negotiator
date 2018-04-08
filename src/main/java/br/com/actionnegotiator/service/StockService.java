package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.model.TransactionType;
import br.com.actionnegotiator.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;

	public Stock save(Stock stock) throws DataIntegrityViolationException {
		return stockRepository.save(stock);
	}

	public void delete(Stock stock) {
		stockRepository.delete(stock);
	}
	
	public Iterable<Stock> findAllByAccount(Account account) {
		return stockRepository.findAllByAccount(account);
	}
	
	@Transactional
	public void purchaseStock(Account account, Company company) {
		BigDecimal quantity = account.getFund().divide(company.getValue(), 2, RoundingMode.HALF_EVEN);
		account.setFund(BigDecimal.ZERO);
		account = accountService.save(account);
		transactionService.save(TransactionType.PURCHASE, account, company, company.getValue(), quantity);
		Stock stock = new Stock(account, company, quantity);
		this.save(stock);
	}

	@Transactional
	public void sellStock(Stock stock) {
		BigDecimal value = stock.getQuantity().multiply(stock.getCompany().getValue());
		Account account = stock.getAccount();
		account.setFund(value);
		account = accountService.save(account);
		transactionService.save(TransactionType.SALE, account, stock.getCompany(), stock.getCompany().getValue(), stock.getQuantity());
		this.delete(stock);
	}

}
