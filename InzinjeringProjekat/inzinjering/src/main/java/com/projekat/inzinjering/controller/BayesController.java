package com.projekat.inzinjering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.inzinjering.dto.BayesDTO;
import com.projekat.inzinjering.service.BayesService;


@RestController
@RequestMapping("api/bayes")
@CrossOrigin(origins = "http://localhost:4200")
public class BayesController {

	@Autowired
	private BayesService bayesService;
	
	@GetMapping(value = "/bayes/{malfunction}")
	public ResponseEntity<?> bayes(@PathVariable String malfunction) {
		List<BayesDTO> result = bayesService.bayes(malfunction);
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	
}
