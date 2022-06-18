package com.projekat.inzinjering.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.inzinjering.dto.RAMDTO;
import com.projekat.inzinjering.dto.ProcessorDTO;
import com.projekat.inzinjering.dto.RAMSuggestionDTO;
import com.projekat.inzinjering.service.ComponentSuggestionService;

@RestController
@RequestMapping("api/suggestion")
@CrossOrigin(origins = "http://localhost:4200")
public class ComponentSuggestionController   
{  
	
	@Autowired
    private ComponentSuggestionService suggestionService;
	
	@PostMapping(value = "/ramSuggestion")
	/*public String hello() {
		RAMSuggestionDTO dto = new RAMSuggestionDTO();
		dto.setMotherboard("Gigabyte_H410M_S2H_V3_G10");
		List<BetterRAMDTO> result = suggestionService.ramSuggestion(dto);
		for(BetterRAMDTO br: result) {
    		System.out.println(br.getType());
    		System.out.println(br.getCapacity());
    		System.out.println(br.getLayout());
    		System.out.println(br.getSpeed());
    		System.out.println(br.getManufacturer());
    	}
		return "Hello User";  
	}  */
	public ResponseEntity<?> compatibleRams(@RequestBody RAMSuggestionDTO dto) {
		List<RAMDTO> result = suggestionService.getCompatibleRams(dto);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
	@GetMapping(value = "/motherboards")
	public ResponseEntity<?> motherboards() {
		List<String> result = suggestionService.getMotherboards();
		System.out.println(result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/processorSuggestion")
	public ResponseEntity<?> compatibleProcessors(@RequestBody RAMSuggestionDTO dto) {
		List<ProcessorDTO> result = suggestionService.getCompatibleProcessors(dto.getMotherboard());
		return new ResponseEntity<>(result, HttpStatus.OK);  
	}  
	
	
}  