package com.projekat.inzinjering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.inzinjering.dto.RAMDTO;
import com.projekat.inzinjering.dto.RAMProcessorSuggestionDTO;
import com.projekat.inzinjering.service.ComponentSuggestionService;
import com.projekat.inzinjering.service.FuzzyService;


@RestController
@RequestMapping("api/fuzzy")
@CrossOrigin(origins = "http://localhost:4200")

public class FuzzyController {

	@Autowired
	private FuzzyService fuzzyService;
	
	@GetMapping(value = "/processors")
	public ResponseEntity<?> processors() {
		List<String> result = fuzzyService.getProcessors();
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@GetMapping(value = "/gcs")
	public ResponseEntity<?> gcs() {
		List<String> result = fuzzyService.getGCs();
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
}
