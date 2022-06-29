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

import com.projekat.inzinjering.dto.MouseDTO;
import com.projekat.inzinjering.dto.MouseKeybordSuggestionDTO;
import com.projekat.inzinjering.dto.RAMDTO;


@Service
public class MouseSuggestionService {
	
	Model model = ModelFactory.createDefaultModel();
	
	public List<MouseDTO> getCompatibleMouses(MouseKeybordSuggestionDTO mouseSuggestion) {
		List<MouseDTO> result = new ArrayList<>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE);  
	        List<String> mouses = new ArrayList<String>();
	        mouses = findMouses(mouseSuggestion.getConnection(), mouseSuggestion.getOperatingSystem());
	        result = suggestCompatibleMouses(mouses);
	        
			is.close();						
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public List<String> findMouses(String mouseConnection, String operatingSystem) {
		List<String> mouses = new ArrayList<>();
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
				+ "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
				+ "SELECT ?mouse "
				+ "WHERE { ?mouse rdf:type base:Mouse. "
				+ "?mouse base:typeOfMouseConnection base:"
				+mouseConnection
				+". "
				+ "?mouse base:mouseSupportOperatingSystem base:"
				+operatingSystem
				+ " }";
		Query query = QueryFactory.create(queryString);
		System.out.println(query);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model);
	    try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("mouse");
                String mouse = r.getLocalName();
                mouses.add(mouse);    
                
            }
            return mouses;  
            
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
	   
		return mouses;
		
	}
	
	
	private String response (String mouse) {
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				 + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				 + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
				 + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
				 + "SELECT ?mouseResponse " 
				 + "WHERE "
				 + "{"
				 + "base:"
				 + mouse
				 + " base:hasResponse ?mouseResponse}";
		
		Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
        	 ResultSet results = qexec.execSelect();
             while (results.hasNext()) {
                 QuerySolution solution = results.nextSolution();
                 Resource r = solution.getResource("mouseResponse");
                 String[] array = r.toString().split("#");
                 String response = array[1];
                 return response.replace("_", "");   
             }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
        return "";
	}
	
	private String acceleration (String mouse) {
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				 + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				 + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
				 + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
				 + "SELECT ?mouseAcceleration " 
				 + "WHERE "
				 + "{"
				 + "base:"
				 + mouse
				 + " base:hasAcceleration ?mouseAcceleration}";
		
		Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("mouseAcceleration");
                String[] array = r.toString().split("#");
                String acceleration = array[1];
                return acceleration.replace("_", "");   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
        return "";
	}
	
	private String minResolution (String mouse) {
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				 + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				 + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
				 + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
				 + "SELECT ?minMouseResolution " 
				 + "WHERE"
				 + "  { base:"+mouse+" base:hasMinMouseResolution  ?minMouseResolution"
				 + "  }";
		
		Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("minMouseResolution");
                String[] array = r.toString().split("#");
                String minResolution = array[1];
                return minResolution.replace("_", "");   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
       return "";
	}
	
	private String maxResolution (String mouse) {
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				 + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				 + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
				 + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
				 + "SELECT ?maxMouseResolution " 
				 + "WHERE "
				 + "{"
				 + "base:"
				 + mouse
				 + " base:hasMaxMouseResolution ?maxMouseResolution}";
		
		Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("maxMouseResolution");
                String[] array = r.toString().split("#");
                String maxResolution = array[1];
                return maxResolution.replace("_", "");   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
        return "";
	}
	
	private String backlight (String mouse) {
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
				 + mouse
				 + " base:typeOfMouseBacklight ?backlight}";
		
		Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("backlight");
                String[] array = r.toString().split("#");
                String backlight = array[1];
                return backlight.replace("_", "");   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
        return "";
	}
	
	private List<MouseDTO> suggestCompatibleMouses(List<String> mouses) {
		List<MouseDTO> compatibleMouses = new ArrayList<>();
		
		for(String m: mouses) {
			MouseDTO mouse = new MouseDTO();
			String response = response(m);
			String acceleration = acceleration(m);
			String minResolution = minResolution(m);
			String maxResolution = maxResolution(m);
			String backlight = backlight(m);
			mouse.setName(m.replace('_', ' '));
			mouse.setResponse(response);
			mouse.setAcceleration(acceleration);
			mouse.setMinResolution(minResolution);
			mouse.setMaxResolution(maxResolution);
			mouse.setBacklight(backlight);
			compatibleMouses.add(mouse);			
		}
		
        return compatibleMouses;	
	}
	
	
}