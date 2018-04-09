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
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping
	public String account(Model model) {
		Iterable<Account> accounts = accountService.findAll();
		model.addAttribute("accounts", accounts);
		return "account"; 
	}

	@RequestMapping("/save")
	public String save(@RequestParam("account-email") String email, @RequestParam("account-fund") BigDecimal fund) throws DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		accountService.save(new Account(email, fund));
		return "redirect:/account";
	}

}
