package br.com.actionnegotiator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.actionnegotiator.service.SimulationService;

@Controller
@RequestMapping("/simulation*")
public class SimulationController {
	
	@Autowired
	SimulationService simulationService;
	
	@RequestMapping("")
	public String simulation() throws InterruptedException {
		simulationService.simulate();
		return "index";
	}

}
