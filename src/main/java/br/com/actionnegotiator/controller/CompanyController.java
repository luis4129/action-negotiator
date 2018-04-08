package br.com.actionnegotiator.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.actionnegotiator.model.Company;
import br.com.actionnegotiator.service.CompanyService;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@GetMapping
	public String Company(Model model) {
		Iterable<Company> companys = companyService.findAll();
		model.addAttribute("companys", companys);
		return "company";
	}

	@RequestMapping("/save")
	public String save(@RequestParam("company-name") String name, @RequestParam("company-value") BigDecimal value) throws DuplicateConstraintException {
		companyService.save(name, value);
		return "redirect:/company";
	}

}
