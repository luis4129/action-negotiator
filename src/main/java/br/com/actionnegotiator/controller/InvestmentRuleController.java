package br.com.actionnegotiator.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.service.AccountService;
import br.com.actionnegotiator.service.CompanyService;
import br.com.actionnegotiator.service.InvestmentRuleService;

@Controller
@RequestMapping("/investmentRule")
public class InvestmentRuleController {

	@Autowired
	InvestmentRuleService investmentRuleService;

	@Autowired
	AccountService accountService;

	@Autowired
	CompanyService companyService;

	@GetMapping
	public String investmentRule(Model model) {
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("companys", companyService.findAll());
		model.addAttribute("investmentRules", investmentRuleService.findAll());
		return "investmentRule";
	}

	@RequestMapping("/save")
	public String save(@RequestParam("investment-rule-id") Long id,
			@RequestParam("investment-rule-account") Long accountId,
			@RequestParam("investment-rule-company") Long companyId,
			@RequestParam("investment-rule-purchase-price") BigDecimal purchasePrice,
			@RequestParam("investment-rule-sale-price") BigDecimal salePrice) throws DuplicateConstraintException, BigDecimalLengthException {
		investmentRuleService.save(new InvestmentRule(id, new Account(accountId), new Company(companyId), purchasePrice, salePrice));
		return "redirect:/investmentRule";
	}
	
	@RequestMapping("/delete")
	public String save(@RequestParam("investment-rule-id") Long id) throws DuplicateConstraintException, BigDecimalLengthException {
		investmentRuleService.delete(new InvestmentRule(id));
		return "redirect:/investmentRule";
	}

}
