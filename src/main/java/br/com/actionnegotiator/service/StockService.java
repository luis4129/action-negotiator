package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.actionnegotiator.enums.TransactionType;
import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.model.Transaction;
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
	
	@Autowired
	private EmailService emailService;
	
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public Stock save(Stock stock) throws DuplicateConstraintException {
		if (stock.getId() == null && stockAlreadyExists(stock.getAccount(), stock.getCompany())) {
			throw new DuplicateConstraintException("Já existe uma ação para a conta " + stock.getAccount().getEmail() + " e a empresa " + stock.getCompany().getName() + " cadastrada." );
		}
		
		return stockRepository.save(stock);
	}

	public void delete(Stock stock) {
		stockRepository.delete(stock);
	}
	
	public Stock findByAccountAndCompany(Account account, Company company) {
		return stockRepository.findByAccountAndCompany(account, company);
	}
	
	private boolean stockAlreadyExists(Account account, Company company) {
		return this.findByAccountAndCompany(account, company) != null;
	}
	
	@Transactional
	public void purchaseStock(Account account, Company company) throws DuplicateConstraintException {
		BigDecimal quantity = account.getFund().divide(company.getValue(), 2, RoundingMode.HALF_EVEN);
		account = updateAccountValue(account, BigDecimal.ZERO);
		registerAndNotify(TransactionType.PURCHASE, account, company, quantity);
		this.save(new Stock(account, company, quantity));
	}

	@Transactional
	public void sellStock(Stock stock) throws DuplicateConstraintException {
		BigDecimal value = stock.getQuantity().multiply(stock.getCompany().getValue());
		Account account = updateAccountValue(stock.getAccount(), value);
		registerAndNotify(TransactionType.SALE, account, stock.getCompany(), stock.getQuantity());		
		this.delete(stock);
	}

	private Account updateAccountValue(Account account, BigDecimal value) throws DuplicateConstraintException {
		account.setFund(value);
		return accountService.save(account);
	}
	private void registerAndNotify(TransactionType transactionType, Account account, Company company, BigDecimal quantity) {
		Transaction transaction = new Transaction(account, company, Calendar.getInstance(), company.getValue(), quantity, transactionType, true);				
		emailService.notify(transactionService.save(transaction));
	}

}
