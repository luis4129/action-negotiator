package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.model.Transaction;
import br.com.actionnegotiator.repository.InvestmentRuleRepository;
import br.com.actionnegotiator.util.SystemUtil;

@Service
public class InvestmentRuleService {

	@Autowired
	private InvestmentRuleRepository investmentRuleRepository;
	
	@Autowired
	private StockService stockService;
	
	public InvestmentRuleService(InvestmentRuleRepository investmentRuleRepository) {
		this.investmentRuleRepository = investmentRuleRepository;
	}

	public Iterable<InvestmentRule> findAll() {
		return investmentRuleRepository.findAll();
	}
	
	private boolean ruleAlreadyExists(Account account, Company company) {
		return investmentRuleRepository.findByAccountAndCompany(account, company) != null;
	}
	
	public InvestmentRule save (InvestmentRule investmentRule) throws DuplicateConstraintException, BigDecimalLengthException {
		validateSave(investmentRule);
		return investmentRuleRepository.save(investmentRule);
	}
	
	public void delete (InvestmentRule investmentRule) {
		investmentRuleRepository.delete(investmentRule);
	}
	
	private void validateSave(InvestmentRule investmentRule) throws DuplicateConstraintException, BigDecimalLengthException {
		if (ruleAlreadyExists(investmentRule.getAccount(), investmentRule.getCompany())) {
			throw new DuplicateConstraintException("Já existe uma regra de investimento para a conta " + investmentRule.getAccount().getEmail() + " e a empresa " + investmentRule.getCompany().getName() + " cadastrada." );
		} else if (SystemUtil.invalidBigDecimalLength(investmentRule.getPurchasePrice())) {
			throw new BigDecimalLengthException("Preço de compra maior do que o permitido, favor preencher o mesmo com menos de 19 dígitos antes da vírgula.");
		} else if (SystemUtil.invalidBigDecimalLength(investmentRule.getSalePrice())) {
			throw new BigDecimalLengthException("Preço de venda maior do que o permitido, favor preencher o mesmo com menos de 19 dígitos antes da vírgula.");
		}
	}

	public Iterable<InvestmentRule> findByCompany(Company company) {
		return investmentRuleRepository.findByCompany(company);
	}

	@Transactional
	public void monitor(Company company) throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {		
		for (InvestmentRule investmentRule : this.findByCompany(company)) {
			if (canPurchase(company.getValue(), investmentRule.getPurchasePrice()) && accountHasFunds(investmentRule.getAccount())) {				
				stockService.purchaseStock(investmentRule.getAccount(), company);				
			} else if (canSell(company.getValue(), investmentRule.getSalePrice())) {
				Stock stock = stockService.findByAccountAndCompany(investmentRule.getAccount(), company);
				if (stock != null) {
					stockService.sellStock(stock);
				}																
			}
		}
	}

	private Boolean canPurchase(BigDecimal companyPrice, BigDecimal purchasePrice) {
		return companyPrice.compareTo(purchasePrice) < 1;
	}

	private Boolean canSell(BigDecimal companyPrice, BigDecimal salePrice) {
		return companyPrice.compareTo(salePrice) > -1;
	}
	
	private boolean accountHasFunds(Account account) {
		return account.getFund().compareTo(BigDecimal.ZERO) > 0;
	}
	
	public BigDecimal getRequestedValue(Transaction transaction) {
		for (InvestmentRule investmentRule : transaction.getAccount().getInvestmentRules()) {
			if (investmentRule.getCompany().getId().equals(transaction.getCompany().getId())) {
				return transaction.getType().getValue(investmentRule);
			}
		}
		return BigDecimal.ZERO;
	}
	
}
