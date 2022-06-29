package com.projekat.inzinjering.controller;

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
import com.projekat.inzinjering.dto.GraphicsCardDTO;
import com.projekat.inzinjering.dto.HardDiskDTO;
import com.projekat.inzinjering.dto.MouseDTO;
import com.projekat.inzinjering.dto.MouseKeybordSuggestionDTO;
import com.projekat.inzinjering.dto.ProcessorDTO;
import com.projekat.inzinjering.dto.RAMProcessorSuggestionDTO;
import com.projekat.inzinjering.service.ComponentSuggestionService;
import com.projekat.inzinjering.service.GraphicsCardSuggestionService;
import com.projekat.inzinjering.service.HardDiskSuggestionService;
import com.projekat.inzinjering.service.MouseSuggestionService;
import com.projekat.inzinjering.service.ProcessorSuggestionService;
import com.projekat.inzinjering.service.RamSuggestionService;

@RestController
@RequestMapping("api/suggestion")
@CrossOrigin(origins = "http://localhost:4200")
public class ComponentSuggestionController   
{  
	
	@Autowired
    private ComponentSuggestionService suggestionService;
	
	@Autowired
    private RamSuggestionService ramService;
	
	@Autowired
    private ProcessorSuggestionService processorService;
	
	@Autowired
    private HardDiskSuggestionService hddService;
	
	@Autowired
	private GraphicsCardSuggestionService gcService;
	
	@Autowired
	private MouseSuggestionService mouseService;
	
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
	public ResponseEntity<?> compatibleRams(@RequestBody RAMProcessorSuggestionDTO dto) {
		List<RAMDTO> result = ramService.getCompatibleRams(dto);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
	@GetMapping(value = "/motherboards")
	public ResponseEntity<?> motherboards() {
		List<String> result = suggestionService.getMotherboards();
		System.out.println(result);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/processorSuggestion")
	public ResponseEntity<?> compatibleProcessors(@RequestBody RAMProcessorSuggestionDTO dto) {
		List<ProcessorDTO> result = processorService.getCompatibleProcessors(dto.getMotherboard());
		return new ResponseEntity<>(result, HttpStatus.OK);  
	} 
	
	@PostMapping(value = "/hddSuggestion")
	public ResponseEntity<?> compatibleHardDisks(@RequestBody RAMProcessorSuggestionDTO dto) {
		List<HardDiskDTO> result = hddService.getCompatibleHDD(dto.getMotherboard(), dto.getComputer());
		return new ResponseEntity<>(result, HttpStatus.OK);  
	} 
	
	@PostMapping(value = "/gcSuggestion")
	public ResponseEntity<?> compatibleGCs(@RequestBody RAMProcessorSuggestionDTO dto) {
		List<GraphicsCardDTO> result = gcService.getCompatibleGC(dto.getMotherboard(), dto.getComputer());
		return new ResponseEntity<>(result, HttpStatus.OK);  
	} 
	
	@PostMapping(value = "/mouseSuggestion")
	public ResponseEntity<?> compatible(@RequestBody MouseKeybordSuggestionDTO dto) {
		List<MouseDTO> result = mouseService.getCompatibleMouses(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);  
	} 
}  
