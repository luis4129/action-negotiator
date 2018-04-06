package br.com.actionnegotiator.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.actionnegotiator.model.Account;
import br.com.actionnegotiator.service.AccountService;

@Controller
@RequestMapping("/account*")
public class AccountController {

	@Autowired
	AccountService accountService;

	@RequestMapping("")
	public String account(Model model) {
		Iterable<Account> accounts = accountService.findAll();
		model.addAttribute("accounts", accounts);
		return "account";
	}

	@RequestMapping("/save")
	public String save(@RequestParam("account-email") String email, @RequestParam("account-fund") BigDecimal fund) {
		accountService.save(email, fund);
		return "redirect:/account";
	}

}
