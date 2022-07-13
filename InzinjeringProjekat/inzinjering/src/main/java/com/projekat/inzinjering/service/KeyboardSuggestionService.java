package com.projekat.inzinjering.service;

import java.io.InputStream;


import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Service;

import com.projekat.inzinjering.dto.KeyboardDTO;
import com.projekat.inzinjering.dto.MouseDTO;
import com.projekat.inzinjering.dto.MouseKeybordSuggestionDTO;



@Service
public class KeyboardSuggestionService {

	Model model = ModelFactory.createDefaultModel();
	
	public List<KeyboardDTO> getCompatibleKeyboards(MouseKeybordSuggestionDTO keyboardSuggestion) {
		List<KeyboardDTO> result = new ArrayList<>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE);  
	        List<String> keyboards = new ArrayList<String>();
	        keyboards = findKeyboards(keyboardSuggestion.getConnection(), keyboardSuggestion.getOperatingSystem());
	        result = suggestCompatibleKeyboards(keyboards);
	        
			is.close();						
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public List<String> findKeyboards(String keyboardConnection, String operatingSystem) {
		List<String> keyboards = new ArrayList<>();
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
				+ "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
				+ "SELECT ?keyboard "
				+ "WHERE { ?keyboard rdf:type base:Keyboard. "
				+ "?keyboard base:typeOfKeyboardConnection base:"
				+keyboardConnection
				+". "
				+ "?keyboard base:keyboardSupportOperatingSystem base:"
				+operatingSystem
				+ " }";
		Query query = QueryFactory.create(queryString);
		System.out.println(query);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model);
	    try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("keyboard");
                String keyboard = r.getLocalName();
                keyboards.add(keyboard);    
                
            }
            System.out.println(keyboards);
            return keyboards;  
            
            
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
	   
		return keyboards;
		
	}
	
	private String backlight (String keyboard) {
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				 + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				 + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
				 + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
				 + "SELECT ?backlight " 
				 + "WHERE "
				 + "{"
				 + "base:"
				 + keyboard
				 + " base:typeOfKeyboardBacklight ?backlight}";
		
		Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("backlight");
                String backlight;
                if(r==null) backlight=null;
                String[] array = r.toString().split("#");
                backlight = array[1];
                return backlight.replace("_", "");   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
        return "";
	}
	
	private List<KeyboardDTO> suggestCompatibleKeyboards(List<String> keyboards) {
		List<KeyboardDTO> compatibleKeyboards = new ArrayList<>();
		
		for(String k: keyboards) {
			KeyboardDTO keyboard = new KeyboardDTO();
			String backlight = backlight(k);
			keyboard.setName(k.replace('_', ' '));
			keyboard.setBacklight(backlight);
			compatibleKeyboards.add(keyboard);			
		}
		
        return compatibleKeyboards;	
	}
}
