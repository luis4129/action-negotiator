package br.com.actionnegotiator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.actionnegotiator.service.AccountService;
import br.com.actionnegotiator.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	TransactionService transactionService;

	@RequestMapping("/transaction")
	public String index(Model model) {
		model.addAttribute("transactions", transactionService.findAll());
		return "transaction";
	}

	@RequestMapping("/reportRecent")
	public String reportRecent(Model model) {
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("transactions", transactionService.findAllRecent());
		return "reportRecent";
	}

}
