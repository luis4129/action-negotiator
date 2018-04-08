package br.com.actionnegotiator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.actionnegotiator.service.SimulationService;
import br.com.actionnegotiator.service.exception.DuplicateConstraintException;

@Controller
@RequestMapping("/simulation*")
public class SimulationController {

	@Autowired
	SimulationService simulationService;

	@RequestMapping("")
	public String index() {
		return "simulation";
	}

	@RequestMapping("/execute")
	public String execute() throws InterruptedException, DuplicateConstraintException {
		simulationService.executeSimulation();
		return "redirect:/reportRecent";
	}
	

}
