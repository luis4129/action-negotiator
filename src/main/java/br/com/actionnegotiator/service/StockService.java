package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

	public void save(Stock stock) throws DataIntegrityViolationException {
		stockRepository.save(stock);
	}

	public void delete(Stock stock) {
		stockRepository.delete(stock);
	}

	public void purchaseStock(Account account, Company company) {
		BigDecimal quantity = account.getFund().divide(company.getValue(), 2, RoundingMode.HALF_EVEN);
		account.setFund(BigDecimal.ZERO);
		Stock stock = new Stock(account, company, quantity);
		transactionService.save(TransactionType.PURCHASE, account, company, company.getValue(), quantity);
		this.save(stock);
	}

	public void sellStock(Stock stock) {
		BigDecimal value = stock.getQuantity().multiply(stock.getCompany().getValue());
		Account account = stock.getAccount();
		account.setFund(value);
		transactionService.save(TransactionType.SALE, account, stock.getCompany(), stock.getCompany().getValue(), stock.getQuantity());
		this.delete(stock);
	}

}
