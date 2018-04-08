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
import br.com.actionnegotiator.model.Transaction;
import br.com.actionnegotiator.model.TransactionType;
import br.com.actionnegotiator.repository.StockRepository;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public Stock save(Stock stock) throws DataIntegrityViolationException, DuplicateConstraintException {
		if (stock.getId() == null && stockAlreadyExists(stock.getAccount(), stock.getCompany())) {
			throw new DuplicateConstraintException("Já existe uma regra de investimento para a conta " + stock.getAccount().getEmail() + " e a empresa " + stock.getCompany().getName() + " cadastrada." );
		}
		
		return stockRepository.save(stock);
	}

	public void delete(Stock stock) {
		stockRepository.delete(stock);
	}
	
	public Iterable<Stock> findAllByAccount(Account account) {
		return stockRepository.findAllByAccount(account);
	}
	
	private boolean stockAlreadyExists(Account account, Company company) {
		return stockRepository.findByAccountAndCompany(account, company) != null;
	}
	
	@Transactional
	public void purchaseStock(Account account, Company company) throws DuplicateConstraintException {
		BigDecimal quantity = account.getFund().divide(company.getValue(), 2, RoundingMode.HALF_EVEN);
		account = accountService.updateFund(account, BigDecimal.ZERO);
		Transaction transaction = new Transaction(TransactionType.PURCHASE, account, company, company.getValue(), quantity);				
		transactionService.save(transaction);
		Stock stock = new Stock(account, company, quantity);
		this.save(stock);
	}

	@Transactional
	public void sellStock(Stock stock) {
		BigDecimal value = stock.getQuantity().multiply(stock.getCompany().getValue());
		Account account = accountService.updateFund(stock.getAccount(), value);
		Transaction transaction = new Transaction(TransactionType.SALE, account, stock.getCompany(), stock.getCompany().getValue(), stock.getQuantity());				
		transactionService.save(transaction);
		this.delete(stock);
	}

}
