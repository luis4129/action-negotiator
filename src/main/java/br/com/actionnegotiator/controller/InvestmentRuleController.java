package br.com.actionnegotiator.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.service.AccountService;
import br.com.actionnegotiator.service.CompanyService;
import br.com.actionnegotiator.service.InvestmentRuleService;

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
	public String save(@RequestParam("investmentRule-email") String email, @RequestParam("investmentRule-fund") BigDecimal fund) {
		investmentRuleService.save(email, fund);
		return "redirect:/investmentRule";
	}
	
	

}
