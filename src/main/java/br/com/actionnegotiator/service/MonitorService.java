package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Action;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;

@Service
public class MonitorService {
	
	@Autowired
	private InvestmentRuleService investmentRuleService;
	
	@Autowired
	private ActionService actionService;
	
	public void monitor(Company company) {
		Iterable<InvestmentRule> investmentRules = investmentRuleService.findAllByCompany(company);
		for (InvestmentRule investmentRule : investmentRules) {
			
			System.out.println(company.getValue());
			
			if (purchaseCheck(company.getValue(), investmentRule.getPurchasePrice())) {
				Account account = investmentRule.getAccount();
				if (!account.getFund().equals(BigDecimal.ZERO)) {
					System.out.println("time to buy - " + account.getFund());
					actionService.purchaseAction(account, company);
				} else {
					System.out.println("can't buy :( - " + account.getFund());
				}
			}
			
			if (saleCheck(company.getValue(), investmentRule.getSalePrice())) {
				System.out.println("time to sell ");
				Collection<Action> actions = investmentRule.getAccount().getAction();
				System.out.println("actions != null " + (actions != null));
				System.out.println("!actions.isEmpty() " + !actions.isEmpty());
				if(actions != null && !actions.isEmpty()) {
					for (Action action : investmentRule.getAccount().getAction()) {
						if (action.getCompany().getId().equals(company.getId())) {
							//realizar venda
						}
					}
				}				
			}
		}
	}
	
	public Boolean purchaseCheck(BigDecimal companyValue, BigDecimal purchaseValue) {
		return companyValue.compareTo(purchaseValue) < 1;
	}
	
	public Boolean saleCheck(BigDecimal companyValue, BigDecimal saleValue) {
		return companyValue.compareTo(saleValue) > -1;
	}
	
	
}
