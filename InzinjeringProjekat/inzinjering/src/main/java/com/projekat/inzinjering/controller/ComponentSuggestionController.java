package com.projekat.inzinjering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.inzinjering.dto.BetterRAMDTO;
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
		List<BetterRAMDTO> result = suggestionService.ramSuggestion();
		for(BetterRAMDTO br: result) {
    		System.out.println(br.getType());
    		System.out.println(br.getCapacity());
    		System.out.println(br.getLayout());
    		System.out.println(br.getSpeed());
    		System.out.println(br.getManufacturer());
    	}
		return "Hello User";  
	}  
	/*public ResponseEntity<?> ramSuggestion(@RequestBody RAMSuggestionDTO dto) {
		List<BetterRAMDTO> result = suggestionService.ramSuggestion();
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }*/
	 
}  