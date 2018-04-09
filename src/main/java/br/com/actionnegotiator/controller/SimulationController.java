package br.com.actionnegotiator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.actionnegotiator.exception.BigDecimalLengthException;
import br.com.actionnegotiator.exception.DuplicateConstraintException;
import br.com.actionnegotiator.exception.StringLengthException;
import br.com.actionnegotiator.service.SimulationService;

@Controller
@RequestMapping("/simulation")
public class SimulationController {

	@Autowired
	SimulationService simulationService;

	@GetMapping
	public String index() {
		return "simulation";
	}

	@RequestMapping("/execute")
	public String execute() throws InterruptedException, DuplicateConstraintException, StringLengthException, BigDecimalLengthException {
		simulationService.executeSimulation();
		return "redirect:/reportRecent";
	}
	

}
