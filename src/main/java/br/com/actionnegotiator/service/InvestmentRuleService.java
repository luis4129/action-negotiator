package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.model.Stock;
import br.com.actionnegotiator.repository.InvestmentRuleRepository;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

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
	
	public InvestmentRule save (InvestmentRule investmentRule) throws DuplicateConstraintException {
		if (investmentRule.getId() == null && ruleAlreadyExists(investmentRule.getAccount(), investmentRule.getCompany())) {
			throw new DuplicateConstraintException("Já existe uma regra de investimento para a conta " + investmentRule.getAccount().getEmail() + " e a empresa " + investmentRule.getCompany().getName() + " cadastrada." );
		}
		
		return investmentRuleRepository.save(investmentRule);
	}

	public InvestmentRule save(Long accountId, Long companyId, BigDecimal purchaseValue, BigDecimal saleValue) throws DuplicateConstraintException {
		Account account = new Account(accountId);
		Company company = new Company(companyId);
		InvestmentRule investmentRule = new InvestmentRule(account, company, purchaseValue, saleValue);
		return this.save(investmentRule);
	}

	public Iterable<InvestmentRule> findAllByCompany(Company company) {
		return investmentRuleRepository.findAllByCompany(company);
	}

	@Transactional
	public void monitor(Company company) throws DuplicateConstraintException {
		
		for (InvestmentRule investmentRule : this.findAllByCompany(company)) {

			System.out.println(company.getName() + " - " + company.getValue());
			
			if (purchaseCheck(company.getValue(), investmentRule.getPurchasePrice())) {
				Account account = investmentRule.getAccount();
				if (!account.getFund().equals(BigDecimal.ZERO)) {
					System.out.println("BOUGHT");
					stockService.purchaseStock(account, company);
				}
			}

			if (saleCheck(company.getValue(), investmentRule.getSalePrice())) {
				for (Stock stock : stockService.findAllByAccount(investmentRule.getAccount())) {
					if (stock.getCompany().getId().equals(company.getId())) {
						System.out.println("SOLD");
						stockService.sellStock(stock);
					}
				}				
			}
		}
	}

	private Boolean purchaseCheck(BigDecimal companyPrice, BigDecimal purchasePrice) {
		return companyPrice.compareTo(purchasePrice) < 1;
	}

	private Boolean saleCheck(BigDecimal companyPrice, BigDecimal salePrice) {
		return companyPrice.compareTo(salePrice) > -1;
	}
	
}
