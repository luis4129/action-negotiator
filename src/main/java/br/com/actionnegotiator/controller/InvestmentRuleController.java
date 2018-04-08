package br.com.actionnegotiator.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.actionnegotiator.service.AccountService;
import br.com.actionnegotiator.service.CompanyService;
import br.com.actionnegotiator.service.InvestmentRuleService;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@Controller
@RequestMapping("/investmentRule*")
public class InvestmentRuleController {

	@Autowired
	InvestmentRuleService investmentRuleService;

	@Autowired
	AccountService accountService;

	@Autowired
	CompanyService companyService;

	@RequestMapping("")
	public String investmentRule(Model model) {
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("companys", companyService.findAll());
		model.addAttribute("investmentRules", investmentRuleService.findAll());
		return "investmentRule";
	}

	@RequestMapping("/save")
	public String save(@RequestParam("investment-rule-account") Long accountId,
			@RequestParam("investment-rule-company") Long companyId,
			@RequestParam("investment-rule-purchase-price") BigDecimal purchasePrice,
			@RequestParam("investment-rule-sale-price") BigDecimal salePrice) throws DuplicateConstraintException {
		try {
			investmentRuleService.save(accountId, companyId, purchasePrice, salePrice);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
		}
		return "redirect:/investmentRule";
	}

}
