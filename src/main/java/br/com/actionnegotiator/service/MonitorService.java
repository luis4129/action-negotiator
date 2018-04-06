package br.com.actionnegotiator.service;

import java.math.BigDecimal;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.model.Stock;

@Service
public class MonitorService {

	@Autowired
	private InvestmentRuleService investmentRuleService;

	@Autowired
	private StockService stockService;

	public void monitor(Company company) {
		Iterable<InvestmentRule> investmentRules = investmentRuleService.findAllByCompany(company);
		for (InvestmentRule investmentRule : investmentRules) {

			if (purchaseCheck(company.getValue(), investmentRule.getPurchasePrice())) {
				Account account = investmentRule.getAccount();
				if (!account.getFund().equals(BigDecimal.ZERO)) {
					stockService.purchaseStock(account, company);
				}
			}

			if (saleCheck(company.getValue(), investmentRule.getSalePrice())) {
				Hibernate.initialize(investmentRule.getAccount().getStocks());
				for (Stock stock : investmentRule.getAccount().getStocks()) {
					if (stock.getCompany().getId().equals(company.getId())) {
						stockService.sellStock(stock);
					}
				}				
			}
		}
	}

	public Boolean purchaseCheck(BigDecimal companyPrice, BigDecimal purchasePrice) {
		return companyPrice.compareTo(purchasePrice) < 1;
	}

	public Boolean saleCheck(BigDecimal companyPrice, BigDecimal salePrice) {
		return companyPrice.compareTo(salePrice) > -1;
	}

}
