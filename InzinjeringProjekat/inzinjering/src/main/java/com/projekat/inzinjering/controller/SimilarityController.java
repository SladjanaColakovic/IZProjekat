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

import com.projekat.inzinjering.dto.SimilarityDTO;
import com.projekat.inzinjering.service.SimilarityService;
@RequestMapping("api/similarity")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SimilarityController {

	@Autowired
    private SimilarityService similarityService;
	
	@GetMapping("/{processorBrand}/{processorName}/{cores}/{processorFrequency}/{ram}/{ramCapacity}/{hardDisc}/{hardDiscCapacity}/{graphicsType}/{graphicsCard}/{chipset}")
    public ResponseEntity<List<SimilarityDTO>> getSimilarity(@PathVariable String processorBrand ,
                                                             @PathVariable  String processorName,
                                                             @PathVariable int cores,
                                                             @PathVariable double processorFrequency,
                                                             @PathVariable String ram,
                                                             @PathVariable int ramCapacity,
                                                             @PathVariable String hardDisc,
                                                             @PathVariable int hardDiscCapacity ,
                                                             @PathVariable String graphicsType,
                                                             @PathVariable String graphicsCard,
                                                             @PathVariable String chipset) throws Exception {
		List<SimilarityDTO> result =  similarityService.similarity(processorBrand,processorName,cores,processorFrequency,ram,ramCapacity,hardDisc,hardDiscCapacity,graphicsType,graphicsCard,chipset);
        
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
