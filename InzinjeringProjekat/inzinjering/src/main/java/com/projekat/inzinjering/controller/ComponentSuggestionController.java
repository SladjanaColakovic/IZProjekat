package com.projekat.inzinjering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.inzinjering.dto.RAMSuggestionDTO;
import com.projekat.inzinjering.service.ComponentSuggestionService;

@RestController
@RequestMapping("api/suggestion")
public class ComponentSuggestionController   
{  
	
	@Autowired
    private ComponentSuggestionService suggestionService;
	
	@GetMapping(value = "/ram")
	public String hello() {
		suggestionService.ramSuggestion();
		return "Hello User";  
	}  
	/*public ResponseEntity<?> ramSuggestion(@RequestBody RAMSuggestionDTO dto) {
		suggestionService.ramSuggestion();
		//return "Hello User";
    	return new ResponseEntity<>(HttpStatus.OK);
    }*/
	 
}  