package com.projekat.inzinjering.service;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projekat.inzinjering.dto.SimilarityDTO;

import connector.CsvConnector;
import model.CaseDescription;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CBRCaseBase;
import ucm.gaia.jcolibri.cbrcore.CBRQuery;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.EqualsStringIgnoreCase;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

@Service
public class SimilarityService implements StandardCBRApplication {
	
	Connector _connector;  /** Connector object */
	CBRCaseBase _caseBase;  /** CaseBase object */

	NNConfig simConfig;  /** KNN configuration */
	
	private static List<SimilarityDTO> similarityDTOS;
	
	public void configure() throws ExecutionException {
		similarityDTOS = new ArrayList<SimilarityDTO>();
		
		_connector =  new CsvConnector();
		
		_caseBase = new LinealCaseBase();  // Create a Lineal case base for in-memory organization
		
		simConfig = new NNConfig(); // KNN configuration
		simConfig.setDescriptionSimFunction(new Average());  // global similarity function = average
		
		// simConfig.addMapping(new Attribute("attribute", CaseDescription.class), new Interval(5));
		// TODO
		
		simConfig.addMapping(new Attribute("processorBrand", CaseDescription.class), new EqualsStringIgnoreCase());
		simConfig.addMapping(new Attribute("processorName", CaseDescription.class), new EqualsStringIgnoreCase());
		simConfig.addMapping(new Attribute("cores", CaseDescription.class), new Equal());
		simConfig.addMapping(new Attribute("processorFrequency", CaseDescription.class), new Interval(5));
		simConfig.addMapping(new Attribute("ram", CaseDescription.class), new EqualsStringIgnoreCase());
		simConfig.addMapping(new Attribute("hardDisc", CaseDescription.class), new EqualsStringIgnoreCase());
		simConfig.addMapping(new Attribute("ramCapacity", CaseDescription.class), new Interval(128));
		simConfig.addMapping(new Attribute("hardDiscCapacity", CaseDescription.class), new Interval(256));
		simConfig.addMapping(new Attribute("graphicsType", CaseDescription.class), new EqualsStringIgnoreCase());
		simConfig.addMapping(new Attribute("graphicsCard", CaseDescription.class), new EqualsStringIgnoreCase());
		simConfig.addMapping(new Attribute("chipset", CaseDescription.class), new EqualsStringIgnoreCase());
		
		// Equal - returns 1 if both individuals are equal, otherwise returns 0
		// Interval - returns the similarity of two number inside an interval: sim(x,y) = 1-(|x-y|/interval)
		// Threshold - returns 1 if the difference between two numbers is less than a threshold, 0 in the other case
		// EqualsStringIgnoreCase - returns 1 if both String are the same despite case letters, 0 in the other case
		// MaxString - returns a similarity value depending of the biggest substring that belong to both strings
		// EnumDistance - returns the similarity of two enum values as the their distance: sim(x,y) = |ord(x) - ord(y)|
		// EnumCyclicDistance - computes the similarity between two enum values as their cyclic distance
		// Table - uses a table to obtain the similarity between two values. Allowed values are Strings or Enums. The table is read from a text file.
		// TableSimilarity(List<String> values).setSimilarity(value1,value2,similarity) 
	}

	public void cycle(CBRQuery query) throws ExecutionException {
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		eval = SelectCases.selectTopKRR(eval, 5);
		String pom;
		System.out.println("Retrieved cases:");
		for (RetrievalResult nse : eval)
			System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
		for (RetrievalResult nse : eval) {
			pom = nse.get_case().getDescription().toString();
			System.out.println(pom);
			SimilarityDTO newDTO = processResult(pom);
			similarityDTOS.add(newDTO);
			
			
			
		}
		System.out.println(similarityDTOS.size());
		

	}
	
	private SimilarityDTO processResult(String result) {
        String[] parts = result.split(",");
        String processorBrand = parts[0].split("=")[1];
        String processorName = parts[1].split("=")[1];
        int cores = Integer.parseInt(parts[2].split("=")[1]);
        double processorFrequency = Double.parseDouble(parts[3].split("=")[1]);
        String ram = parts[4].split("=")[1];
        int ramCapacity = Integer.parseInt(parts[5].split("=")[1]);
        String hardDisc = parts[6].split("=")[1];
        int hardDiscCapacity = Integer.parseInt(parts[7].split("=")[1]);
        String graphicsType = parts[8].split("=")[1];
        String graphicsCard = parts[9].split("=")[1];
        String chipset = parts[10].split("=")[1];

        return new SimilarityDTO(processorBrand,processorName,cores,processorFrequency,ram,ramCapacity,hardDisc,hardDiscCapacity,graphicsType,graphicsCard,chipset);
    }

	public void postCycle() throws ExecutionException {
		
	}

	public CBRCaseBase preCycle() throws ExecutionException {
		_caseBase.init(_connector);
		java.util.Collection<CBRCase> cases = _caseBase.getCases();
		for (CBRCase c: cases)
			System.out.println(c.getDescription());
		return _caseBase;
	}
	
	
	 public List<SimilarityDTO> similarity(String processorBrand, String processorName, int cores, double frequency, String ram,int ramCapacity, String hardDisc, int hardDiscCapacity, String graphicsType, String graphicsCard, String chipset) {
	        
		    StandardCBRApplication recommender = new SimilarityService();
		    
	        try {
	            recommender.configure();

	            recommender.preCycle();

	            CBRQuery query = new CBRQuery();
	            CaseDescription caseDescription = new CaseDescription();

	            // TODO
	            caseDescription.setProcessorName(processorName);
	            caseDescription.setProcessorName(processorBrand);
	            caseDescription.setCores(cores);
	            caseDescription.setProcessorFrequency(frequency);
	            caseDescription.setRam(ram);
	            caseDescription.setRamCapacity(ramCapacity);
	            caseDescription.setHardDisc(hardDisc);
	            caseDescription.setHardDiscCapacity(hardDiscCapacity);
	            caseDescription.setGraphicsType(graphicsType);
	            caseDescription.setGraphicsCard(graphicsCard);
	            caseDescription.setChipset(chipset);

	            query.setDescription( caseDescription );

	            recommender.cycle(query);
	            
	            
	            
	            
	            
	            
	            return similarityDTOS;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return new ArrayList<>();
	    }
	

	
	

}